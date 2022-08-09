package com.tinqin.academy.processor;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.entity.Team;
import com.tinqin.academy.error.BudgetTooLowError;
import com.tinqin.academy.error.NoSuchDriverError;
import com.tinqin.academy.error.NoSuchTeamError;
import com.tinqin.academy.error.TransferFailedError;
import com.tinqin.academy.exception.DriverNotFoundException;
import com.tinqin.academy.exception.InsufficienFundsException;
import com.tinqin.academy.exception.TeamNotFoundException;
import com.tinqin.academy.exception.TransferNotPossibleException;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.model.transfer.driver.DriverRequest;
import com.tinqin.academy.model.transfer.driver.DriverResponse;
import com.tinqin.academy.model.transfer.team.TeamRequest;
import com.tinqin.academy.model.transfer.team.TeamResponse;
import com.tinqin.academy.operation.DriverProcessor;
import com.tinqin.academy.operation.TeamProcessor;
import com.tinqin.academy.operation.TransferProcessor;
import com.tinqin.academy.repository.DriverRepository;
import com.tinqin.academy.repository.TeamRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class TransferProcessorCore implements TransferProcessor {
    private final DriverProcessor driverProcessor;
    private final TeamProcessor teamProcessor;
    private final TeamRepository teamRepository;
    private final DriverRepository driverRepository;
    private final ConversionService conversionService;

    public TransferProcessorCore(DriverProcessor driverProcessor, TeamProcessor teamProcessor, TeamRepository teamRepository, DriverRepository driverRepository, ConversionService conversionService) {
        this.driverProcessor = driverProcessor;
        this.teamProcessor = teamProcessor;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, TransferResponse> process(TransferRequest input) {
        return Try.of(()->{
            final Either<Error,DriverResponse> driverResult=driverProcessor.process(new DriverRequest(input.getDriverId()));
            final Either<Error, TeamResponse> oldTeamResult=teamProcessor.process(new TeamRequest(input.getTeamId()));
            final Either<Error, TeamResponse> newTeamResult=teamProcessor.process(new TeamRequest(input.getNewTeamId()));

            if(driverResult.isLeft() || oldTeamResult.isLeft() || newTeamResult.isLeft())
                throw new TransferNotPossibleException();

            final DriverResponse driver=driverResult.get();
            final TeamResponse newTeam=newTeamResult.get();
            final TeamResponse oldTeam=oldTeamResult.get();

            final Double newTeamBudget=Double.parseDouble(newTeam.getBudget());
            final Double driverSalary=Double.parseDouble(driver.getSalary());
            final Double budgetAfterTransfer=newTeamBudget-driverSalary-5000000;

            if(budgetAfterTransfer<0){
                throw new InsufficienFundsException();
            }
           return Stream.of(driverRepository.findDriverByFirstNameAndLastName(driver.getFirstName(),driver.getLastName())
                    .orElseThrow(DriverNotFoundException::new))
                    .map(driver1 -> {
                        Team team=teamRepository.findTeamByTeamName(newTeam.getTeamName())
                                .orElseThrow(TeamNotFoundException::new);
                        driver1.setTeam(team);
                        driverRepository.save(driver1);
                        return team;
                    })
                    .peek(team -> {
                        team.setBudget(budgetAfterTransfer);
                        teamRepository.save(team);
                    })
                   .map(team -> TransferResponse.builder()
                           .driverFirstName(driver.getFirstName())
                           .driverLastName(driver.getLastName())
                           .oldTeamName(oldTeam.getTeamName())
                           .newTeamName(team.getTeamName())
                           .build())
                   .findFirst()
                   .orElseThrow(TransferNotPossibleException::new);

        }).toEither()
                .mapLeft(throwable -> {
                   if(throwable instanceof DriverNotFoundException)
                       return new NoSuchDriverError();
                   if(throwable instanceof TeamNotFoundException)
                       return new NoSuchTeamError();
                   if(throwable instanceof TransferNotPossibleException)
                       return new TransferFailedError();
                   if(throwable instanceof InsufficienFundsException)
                       return new BudgetTooLowError();
                   return new TransferFailedError();
                });
    }
}

package com.tinqin.academy.processor.transfer;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.error.transfer.BudgetTooLowError;
import com.tinqin.academy.error.driver.NoSuchDriverError;
import com.tinqin.academy.error.team.NoSuchTeamError;
import com.tinqin.academy.error.transfer.TransferFailedError;
import com.tinqin.academy.exception.driver.DriverNotFoundException;
import com.tinqin.academy.exception.transfer.InsufficienFundsException;
import com.tinqin.academy.exception.team.TeamNotFoundException;
import com.tinqin.academy.exception.transfer.TransferNotPossibleException;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.model.driver.DriverRequest;
import com.tinqin.academy.model.driver.DriverResponse;
import com.tinqin.academy.model.team.TeamRequest;
import com.tinqin.academy.model.team.TeamResponse;
import com.tinqin.academy.operation.DriverProcessor;
import com.tinqin.academy.operation.TeamProcessor;
import com.tinqin.academy.operation.TransferProcessor;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.data.repository.TeamRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class TransferProcessorCore implements TransferProcessor {
    private final DriverProcessor driverProcessor;
    private final TeamProcessor teamProcessor;
    private final TeamRepository teamRepository;
    private final DriverRepository driverRepository;
    private static final Double FIA_TRANSFER_FEE=5000000.0;

    public TransferProcessorCore(DriverProcessor driverProcessor, TeamProcessor teamProcessor, TeamRepository teamRepository, DriverRepository driverRepository) {
        this.driverProcessor = driverProcessor;
        this.teamProcessor = teamProcessor;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
    }
    private boolean fundsCheck(Double budget,Double salary){
        return budget - salary - FIA_TRANSFER_FEE > 0;
    }

    @Override
    public Either<Error, TransferResponse> process(final TransferRequest input) {
        return Try.of(()->{
            final DriverResponse driver=driverProcessor.process(new DriverRequest(input.getDriverId()))
                    .getOrElseThrow(TransferNotPossibleException::new);
            final TeamResponse oldTeam=teamProcessor.process(new TeamRequest(input.getTeamId()))
                    .getOrElseThrow(TransferNotPossibleException::new);
            final TeamResponse newTeam=teamProcessor.process(new TeamRequest(input.getNewTeamId()))
                    .getOrElseThrow(TransferNotPossibleException::new);

            final Double newTeamBudget=Double.parseDouble(newTeam.getBudget());
            final Double driverSalary=Double.parseDouble(driver.getSalary());
            final Double budgetAfterTransfer=newTeamBudget-driverSalary-FIA_TRANSFER_FEE;

            if(!fundsCheck(newTeamBudget,driverSalary)){
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

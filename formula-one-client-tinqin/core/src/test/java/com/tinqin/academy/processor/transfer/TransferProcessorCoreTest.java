package com.tinqin.academy.processor.transfer;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.data.repository.TeamRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.model.driver.DriverRequest;
import com.tinqin.academy.model.driver.DriverResponse;
import com.tinqin.academy.model.team.TeamRequest;
import com.tinqin.academy.model.team.TeamResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.processor.driver.DriverProcessorCore;
import com.tinqin.academy.processor.team.TeamProcessorCore;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransferProcessorCoreTest {
    @Mock
    private TeamProcessorCore teamProcessorCore;

    @Mock
    private DriverProcessorCore driverProcessorCore;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TransferProcessorCore transferProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
         Team team=new Team("Team1",50000000.0);
         Team team2=new Team("Team2",80000000.0);
        team.setId_team(1L);
        team2.setId_team(2L);
        Driver driver=new Driver("Alexander", "Zhivkov", 1000000.0, 8, team);
        driver.setId_driver(1L);

        Either<Error,DriverResponse> driverResponses= Try.of(()->DriverResponse.builder()
                .firstName(driver.getFirstName())
                .team(driver.getTeam().getTeamName())
                .salary(String.valueOf(driver.getSalary()))
                .lastName(driver.getLastName())
                .build()).toEither()
                        .mapLeft(throwable -> {
                            return new GeneralServerError();
                                }
                        );
        Either<Error,TeamResponse> teamResponses= Try.of(()->TeamResponse.builder()
                                .teamName(team.getTeamName())
                                .budget(String.valueOf(team.getBudget()))
                                .build()).toEither()
                .mapLeft(throwable -> {
                            return new GeneralServerError();
                        }
                );
        Either<Error,TeamResponse> teamResponses2= Try.of(()->TeamResponse.builder()
                        .teamName(team2.getTeamName())
                        .budget(String.valueOf(team2.getBudget()))
                        .build()).toEither()
                .mapLeft(throwable -> {
                            return new GeneralServerError();
                        }
                );

        DriverRequest driverRequest=new DriverRequest(driver.getId_driver());
        TeamRequest teamRequest=new TeamRequest(team.getId_team());
        TeamRequest teamRequest2=new TeamRequest(team2.getId_team());

        Mockito.when(driverProcessorCore.process(driverRequest))
                .thenReturn(driverResponses);

        when(teamProcessorCore.process(teamRequest))
                .thenReturn(teamResponses);

        when(teamProcessorCore.process(teamRequest2))
                .thenReturn(teamResponses2);

        when(teamRepository.findTeamByTeamName(team.getTeamName()))
                .thenReturn(Optional.of(team));
        when(teamRepository.findTeamByTeamName(team2.getTeamName()))
                .thenReturn(Optional.of(team2));
        when(driverRepository.findDriverByFirstNameAndLastName(driver.getFirstName(),driver.getLastName()))
                .thenReturn(Optional.of(driver));


        Mockito.when(driverRepository.save(driver)).thenReturn(null);

        Mockito.when(teamRepository.save(team2)).thenReturn(null);


        TransferRequest transferRequest= TransferRequest.builder()
                .driverId(driver.getId_driver())
                .newTeamId(team2.getId_team())
                .build();

        TransferResponse t= TransferResponse.builder()
                .driverFirstName(driver.getFirstName())
                .driverLastName(driver.getLastName())
                .oldTeamName(team.getTeamName())
                .newTeamName(team2.getTeamName())
                .build();
        Assertions.assertNotNull(transferProcessorCore.process(transferRequest).get());
        Assertions.assertEquals(t,transferProcessorCore.process(transferRequest).get());

    }

}
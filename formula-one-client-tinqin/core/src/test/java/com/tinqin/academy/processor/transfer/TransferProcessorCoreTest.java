package com.tinqin.academy.processor.transfer;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.data.repository.TeamRepository;
import com.tinqin.academy.model.driver.DriverRequest;
import com.tinqin.academy.model.driver.DriverResponse;
import com.tinqin.academy.model.team.TeamRequest;
import com.tinqin.academy.model.team.TeamResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.processor.driver.DriverProcessorCore;
import com.tinqin.academy.processor.team.TeamProcessorCore;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        final Team team=new Team("Team1",50000000.0);
        final Team team2=new Team("Team2",80000000.0);
        Team teamAfterTransfer=new Team("Team3",24000000.0);
        team.setId_team(1L);

        teamAfterTransfer.setId_team(3L);

        final Driver driver=new Driver("Alexander", "Zhivkov", 1000000.0, 8, team);
        driver.setId_driver(1L);

        Driver driverAfterSwitch=driver;
        driverAfterSwitch.setTeam(teamAfterTransfer);

        when(driverProcessorCore.process(new DriverRequest(1L)))
                .thenReturn(Either.right(DriverResponse.builder()
                        .firstName(driver.getFirstName())
                        .team(driver.getTeam().getTeamName())
                        .salary(String.valueOf(driver.getSalary()))
                        .lastName(driver.getLastName())
                        .build()));

        when(teamProcessorCore.process(new TeamRequest(1L)))
                .thenReturn(Either.right(TeamResponse.builder()
                                .teamName(team.getTeamName())
                                .budget(String.valueOf(team.getBudget()))
                        .build()));

        when(teamProcessorCore.process(new TeamRequest(2L)))
                .thenReturn(Either.right(TeamResponse.builder()
                        .teamName(team2.getTeamName())
                        .budget(String.valueOf(team2.getBudget()))
                        .build()));

        when(teamRepository.findTeamByTeamName(team.getTeamName()))
                .thenReturn(Optional.of(team));
        when(teamRepository.findTeamByTeamName(team2.getTeamName()))
                .thenReturn(Optional.of(team2));
        when(driverRepository.findDriverByFirstNameAndLastName(driver.getFirstName(),driver.getLastName()))
                .thenReturn(Optional.of(driver));

      /*  Answer<Driver> answer=new Answer<Driver>() {
            @Override
            public Driver answer(InvocationOnMock invocationOnMock) throws Throwable {
                driver.setTeam(team2);
                return driver;
            }
        };
        Answer<Team> teamAnswer=new Answer<Team>() {
            @Override
            public Team answer(InvocationOnMock invocationOnMock) throws Throwable {
                team2.setBudget(24000000.0);
                return team;
            }
        };*/
        /*when(driverRepository.save(driver)).then(answer);*/
/*
        when(teamRepository.save(team2)).then(teamAnswer);*/

        when(driverRepository.save(driver)).thenReturn(driverAfterSwitch);

        when(teamRepository.save(team2)).thenReturn(teamAfterTransfer);


        TransferRequest transferRequest= TransferRequest.builder()
                .driverId(1L)
                .newTeamId(2L)
                .build();

        TransferResponse t= TransferResponse.builder()
                .driverFirstName(driver.getFirstName())
                .driverLastName(driver.getLastName())
                .oldTeamName(team.getTeamName())
                .newTeamName(teamAfterTransfer.getTeamName())
                .build();
        Assertions.assertNotNull(transferProcessorCore.process(transferRequest).get());
        Assertions.assertEquals(t,transferProcessorCore.process(transferRequest).get());

    }
}
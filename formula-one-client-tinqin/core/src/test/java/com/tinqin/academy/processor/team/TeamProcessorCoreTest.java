package com.tinqin.academy.processor.team;

import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.TeamRepository;
import com.tinqin.academy.model.team.TeamRequest;
import com.tinqin.academy.model.team.TeamResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TeamProcessorCoreTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamProcessorCore teamProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        final Team team=new Team("Team1",8000.0);
        final Team team2=new Team("Team2",8000.0);
        team.setId_team(1L);
        team2.setId_team(2L);

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team2));

        when(conversionService.convert(team, TeamResponse.class))
                .thenReturn(TeamResponse.builder()
                        .teamName(team.getTeamName())
                        .budget(String.valueOf(team.getBudget()))
                        .build());

        when(conversionService.convert(team2, TeamResponse.class))
                .thenReturn(TeamResponse.builder()
                        .teamName(team2.getTeamName())
                        .budget(String.valueOf(team2.getBudget()))
                        .build());

        TeamRequest teamRequest=new TeamRequest(1L);
        TeamRequest teamRequest1=new TeamRequest(2L);

        TeamResponse teamResponse=TeamResponse.builder()
                .teamName(team.getTeamName())
                .budget(String.valueOf(team.getBudget()))
                .build();

        TeamResponse teamResponse2=TeamResponse.builder()
                .teamName(team2.getTeamName())
                .budget(String.valueOf(team2.getBudget()))
                .build();

        Assertions.assertNotNull(teamProcessorCore.process(teamRequest).get());
        Assertions.assertNotNull(teamProcessorCore.process(teamRequest1).get());

        Assertions.assertEquals(teamResponse,teamProcessorCore.process(teamRequest).get());
        Assertions.assertEquals(teamResponse2,teamProcessorCore.process(teamRequest1).get());
    }
}
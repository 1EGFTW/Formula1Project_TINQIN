package com.tinqin.academy.processor.mostsuccessfulteam;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.data.repository.TeamRepository;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverRequest;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamRequest;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamResponse;
import com.tinqin.academy.processor.mostsuccessfuldriver.MostSuccessfulDriverProcessorCore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class MostSuccessfulTeamProcessorCoreTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private MostSuccessfulTeamProcessorCore mostSuccessfulTeamProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        final Team team=new Team("Team1",8000.0);
        final Team team2=new Team("Team2",8000.0);
        final Team team3=new Team("Team3",8000.0);
        final Driver driver1 = new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        final Driver driver2 = new Driver("Vladi", "Andreev", 100.0, 7, team);
        final Driver driver3 = new Driver("Tedi", "Tedi", 100.0, 4, team3);

        when(teamRepository.findAll()).thenReturn(List.of(team,team2,team3));
        when(driverRepository.findDriversByTeam(team)).thenReturn(List.of(driver1,driver2));

        MostSuccessfulTeamRequest mostSuccessfulTeamRequest=new MostSuccessfulTeamRequest();
        MostSuccessfulTeamResponse m= MostSuccessfulTeamResponse.builder()
                .teamName(team.getTeamName())
                .numberOfTitles(String.valueOf(15))
                .build();

        Assertions.assertNotNull(mostSuccessfulTeamProcessorCore.process(mostSuccessfulTeamRequest).get());
        Assertions.assertEquals(m,mostSuccessfulTeamProcessorCore.process(mostSuccessfulTeamRequest).get());
    }
}
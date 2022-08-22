package com.tinqin.academy.processor.longestrace;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
import com.tinqin.academy.processor.driver.DriverProcessorCore;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class LongestRaceProcessorCoreTest {
    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private LongestRaceProcessorCore longestRaceProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {

        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        LocalDate localDate= LocalDate.now();

        final Race race1=new Race("C1", localDate,driver,10.0,10.0,100.0,100);
        final Race race2=new Race("C2",localDate,driver,10.0,10.0,100.0,1000);
        final Race race3=new Race("C3", localDate,driver,10.0,10.0,1.0,1);

        when(raceRepository.findAll()).thenReturn(List.of(race1,race2,race3));

        LongestRaceRequest longestRaceRequest=new LongestRaceRequest();

        LongestRaceResponse l= LongestRaceResponse.builder()
                .circuitName("C2")
                .totalDistance(String.valueOf(100000.0)+" km")
                .build();

        Assertions.assertNotNull(longestRaceProcessorCore.process(longestRaceRequest).get());
        Assertions.assertEquals(l,longestRaceProcessorCore.process(longestRaceRequest).get());
    }
}
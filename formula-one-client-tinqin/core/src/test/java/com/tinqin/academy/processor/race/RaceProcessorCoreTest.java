package com.tinqin.academy.processor.race;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RaceProcessorCoreTest {
    @Mock
    private ConversionService conversionService;
    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private RaceProcessorCore raceProcessorCore;

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

        race1.setId_race(1L);
        race2.setId_race(2L);
        race3.setId_race(3L);

        when(raceRepository.findById(1L)).thenReturn(Optional.of(race1));
        when(raceRepository.findById(2L)).thenReturn(Optional.of(race2));
        when(raceRepository.findById(3L)).thenReturn(Optional.of(race3));

        when(conversionService.convert(race1, RaceResponse.class))
                .thenReturn(RaceResponse.builder()
                        .circuitName(race1.getCircuitName())
                        .raceDate(race1.getRaceDate())
                        .longitude(race1.getLongitude())
                        .latitude(race1.getLatitude())
                        .winnerLastName(race1.getWinner().getLastName())
                        .winnerFirstName(race1.getWinner().getFirstName())
                        .build());

        when(conversionService.convert(race2, RaceResponse.class))
                .thenReturn(RaceResponse.builder()
                        .circuitName(race2.getCircuitName())
                        .raceDate(race2.getRaceDate())
                        .longitude(race2.getLongitude())
                        .latitude(race2.getLatitude())
                        .winnerLastName(race2.getWinner().getLastName())
                        .winnerFirstName(race2.getWinner().getFirstName())
                        .build());

        when(conversionService.convert(race3, RaceResponse.class))
                .thenReturn(RaceResponse.builder()
                        .circuitName(race3.getCircuitName())
                        .raceDate(race3.getRaceDate())
                        .longitude(race3.getLongitude())
                        .latitude(race3.getLatitude())
                        .winnerLastName(race3.getWinner().getLastName())
                        .winnerFirstName(race3.getWinner().getFirstName())
                        .build());

        RaceRequest raceRequest=new RaceRequest(1L);
        RaceRequest raceRequest1=new RaceRequest(2L);
        RaceRequest raceRequest2=new RaceRequest(3L);

        RaceResponse raceResponse=RaceResponse.builder()
                .circuitName(race1.getCircuitName())
                .raceDate(race1.getRaceDate())
                .longitude(race1.getLongitude())
                .latitude(race1.getLatitude())
                .winnerLastName(race1.getWinner().getLastName())
                .winnerFirstName(race1.getWinner().getFirstName())
                .build();
        RaceResponse raceResponse1=RaceResponse.builder()
                .circuitName(race2.getCircuitName())
                .raceDate(race2.getRaceDate())
                .longitude(race2.getLongitude())
                .latitude(race2.getLatitude())
                .winnerLastName(race2.getWinner().getLastName())
                .winnerFirstName(race2.getWinner().getFirstName())
                .build();
        RaceResponse raceResponse2=RaceResponse.builder()
                .circuitName(race3.getCircuitName())
                .raceDate(race3.getRaceDate())
                .longitude(race3.getLongitude())
                .latitude(race3.getLatitude())
                .winnerLastName(race3.getWinner().getLastName())
                .winnerFirstName(race3.getWinner().getFirstName())
                .build();

        Assertions.assertNotNull(raceProcessorCore.process(raceRequest).get());
        Assertions.assertNotNull(raceProcessorCore.process(raceRequest1).get());
        Assertions.assertNotNull(raceProcessorCore.process(raceRequest2).get());

        Assertions.assertEquals(raceResponse,raceProcessorCore.process(raceRequest).get());
        Assertions.assertEquals(raceResponse1,raceProcessorCore.process(raceRequest1).get());
        Assertions.assertEquals(raceResponse2,raceProcessorCore.process(raceRequest2).get());
    }
}
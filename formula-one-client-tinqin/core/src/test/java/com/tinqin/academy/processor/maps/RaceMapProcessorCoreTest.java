package com.tinqin.academy.processor.maps;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.feign.MapsClient;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.model.racemap.RaceMapRequest;
import com.tinqin.academy.model.racemap.RaceMapResponse;
import com.tinqin.academy.model.racemap.feign.FeignMapRequest;
import com.tinqin.academy.model.racemap.feign.FeignMapResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RaceMapProcessorCoreTest {
    @Mock
    private MapsClient mapsClient;

    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private RaceMapProcessorCore raceMapProcessorCore;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        team.setId_team(1L);
        driver.setId_driver(1L);
        LocalDate localDate= LocalDate.now();

        final Race race=new Race("C1", localDate,driver,10.0,10.0,100.0,100);
        race.setId_race(1L);

        byte[] imagedata=new byte[120];

        RaceMapRequest raceMapRequest=new RaceMapRequest(race.getCircuitName());

        when(raceRepository.getRaceByCircuitName(raceMapRequest.getCircuitName()))
                .thenReturn(Optional.of(race));


        Mockito.when(mapsClient.generateMaps(FeignMapRequest.builder()
                        .lat(race.getLatitude())
                        .lon(race.getLongitude())
                        .build()))
                .thenReturn(new FeignMapResponse(imagedata));


        RaceMapResponse r= new RaceMapResponse(imagedata);

        Assertions.assertNotNull(raceMapProcessorCore.process(raceMapRequest).get());
        Assertions.assertEquals(r,raceMapProcessorCore.process(raceMapRequest).get());
        System.out.println(raceMapProcessorCore.process(raceMapRequest));
    }

    @Test
    void testErrorRaceNotFound(){
        RaceMapRequest raceMapRequest=new RaceMapRequest("C1");
        when(raceRepository.getRaceByCircuitName(raceMapRequest.getCircuitName()))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,raceMapProcessorCore.process(raceMapRequest).getLeft().getCode());
        Assertions.assertEquals("No such race!",raceMapProcessorCore.process(raceMapRequest).getLeft().getMessage());
    }
    @Test
    void testErrorMapsServiceUnavailable(){
        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        team.setId_team(1L);
        driver.setId_driver(1L);
        LocalDate localDate= LocalDate.now();

        final Race race=new Race("C1", localDate,driver,10.0,10.0,100.0,100);
        race.setId_race(1L);

        RaceMapRequest raceMapRequest=new RaceMapRequest(race.getCircuitName());

        when(raceRepository.getRaceByCircuitName(raceMapRequest.getCircuitName()))
                .thenReturn(Optional.of(race));


        Mockito.when(mapsClient.generateMaps(FeignMapRequest.builder()
                        .lat(race.getLatitude())
                        .lon(race.getLongitude())
                        .build()))
                .thenReturn(null);


        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,raceMapProcessorCore.process(raceMapRequest).getLeft().getCode());
        Assertions.assertEquals("Unhandled exceptions!",raceMapProcessorCore.process(raceMapRequest).getLeft().getMessage());
    }
}
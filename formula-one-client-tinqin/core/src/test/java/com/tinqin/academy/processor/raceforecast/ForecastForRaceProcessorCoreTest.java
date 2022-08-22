package com.tinqin.academy.processor.raceforecast;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.feign.ForecastClient;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.processor.maps.RaceMapProcessorCore;
import com.tinqin.academy.processor.race.RaceProcessorCore;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ForecastForRaceProcessorCoreTest {
    @Mock
    private RaceProcessorCore raceProcessorCore;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ForecastClient forecastClient;

    @InjectMocks
    private ForecastForRaceProcessorCore forecastForRaceProcessorCore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);

        LocalDate localDate= LocalDate.now();

        final Race race=new Race("C1", localDate,driver,10.0,10.0,100.0,100);
        race.setId_race(1L);

        when(raceRepository.getRaceByCircuitName("C1")).thenReturn(Optional.of(race));
        when(raceProcessorCore.process(new RaceRequest(1L)))
                .thenReturn(Either.right(RaceResponse.builder()
                                .winnerFirstName(driver.getFirstName())
                                .winnerLastName(driver.getLastName())
                                .latitude(race.getLatitude())
                                .longitude(race.getLongitude())
                                .raceDate(localDate)
                                .circuitName(race.getCircuitName())
                        .build()));
        when(forecastClient.getForecast(FeignLocationRequest.builder()
                        .lon(race.getLongitude())
                        .lat(race.getLatitude())
                .build())).thenReturn(FeignLocationResponse.builder()
                        .condition("Sunny")
                        .humidity("100")
                        .temperature("10")
                .build());

        ForecastRequest forecastRequest=new ForecastRequest("C1");

        ForecastResponse f= ForecastResponse.builder()
                .circuitName("C1")
                .condition("Sunny")
                .humidity("100")
                .temperature("10")
                .build();

        Assertions.assertNotNull(forecastForRaceProcessorCore.process(forecastRequest).get());
        Assertions.assertEquals(f,forecastForRaceProcessorCore.process(forecastRequest).get());

    }
}
package com.tinqin.academy.processor.raceforecast;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.feign.ForecastClient;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.model.team.TeamResponse;
import com.tinqin.academy.processor.maps.RaceMapProcessorCore;
import com.tinqin.academy.processor.race.RaceProcessorCore;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ForecastForRaceProcessorCoreTest {

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

        team.setId_team(1L);
        driver.setId_driver(1L);
        LocalDate localDate= LocalDate.now();

        final Race race=new Race("C1", localDate,driver,10.0,10.0,100.0,100);
        race.setId_race(1L);

        when(raceRepository.getRaceByCircuitName(race.getCircuitName()))
                .thenReturn(Optional.of(race));

        FeignLocationRequest feignLocationRequest=new FeignLocationRequest(10.0,10.0);

        Mockito.when(forecastClient.getForecast(feignLocationRequest))
                .thenReturn(FeignLocationResponse.builder()
                        .condition("Sunny")
                        .humidity("100")
                        .temperature("10")
                        .build());

        FeignLocationResponse feignLocationResponse=FeignLocationResponse.builder()
                .condition("Sunny")
                .humidity("100")
                .temperature("10")
                .build();

        assertEquals(feignLocationResponse,forecastClient.getForecast(feignLocationRequest));

        ForecastResponse f= ForecastResponse.builder()
                .circuitName("C1")
                .condition("Sunny")
                .humidity("100")
                .temperature("10")
                .build();

        assertNotNull(forecastForRaceProcessorCore.process(new ForecastRequest("C1")).get());
        assertEquals(f,forecastForRaceProcessorCore.process(new ForecastRequest("C1")).get());
    }

    @Test
    void testNoSuchRaceError() {
        when(raceRepository.getRaceByCircuitName("C1"))
                .thenReturn(Optional.empty());

        Assertions.assertEquals("No such race!",forecastForRaceProcessorCore
                .process(new ForecastRequest("C1"))
                .getLeft()
                .getMessage());

        Assertions.assertEquals(HttpStatus.NOT_FOUND,forecastForRaceProcessorCore
                .process(new ForecastRequest("C1"))
                .getLeft()
                .getCode());
    }
    @Test
    void testGeneralServerError() {
        when(raceRepository.getRaceByCircuitName("C1"))
                .thenReturn(null);

        Assertions.assertEquals("Unhandled exceptions!", forecastForRaceProcessorCore
                .process(new ForecastRequest("C1"))
                .getLeft()
                .getMessage());

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, forecastForRaceProcessorCore
                .process(new ForecastRequest("C1"))
                .getLeft()
                .getCode());
    }
}
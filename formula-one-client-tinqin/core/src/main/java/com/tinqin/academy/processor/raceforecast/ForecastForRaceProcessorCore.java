package com.tinqin.academy.processor.raceforecast;

import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.error.forecast.ForecastServiceUnavailableError;
import com.tinqin.academy.feign.ForecastClient;
import com.tinqin.academy.base.Error;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.error.race.NoSuchRaceError;
import com.tinqin.academy.exception.race.RaceNotFoundException;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.operation.ForecastForRaceProcessor;
import com.tinqin.academy.operation.RaceProcessor;
import com.tinqin.academy.data.repository.RaceRepository;
import feign.FeignException;
import feign.RetryableException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class ForecastForRaceProcessorCore implements ForecastForRaceProcessor {
    private final RaceProcessor raceProcessor;
    private final ForecastClient forecastClient;
    private final RaceRepository raceRepository;

    public ForecastForRaceProcessorCore(RaceProcessor raceProcessor, ForecastClient forecastClient, RaceRepository raceRepository) {
        this.raceProcessor = raceProcessor;
        this.forecastClient = forecastClient;
        this.raceRepository = raceRepository;
    }

    @Override
    public Either<Error, ForecastResponse> process(final ForecastRequest input) {
        return Try.of(()->{
            final Race race=raceRepository.getRaceByCircuitName(input.getCircuitName())
                    .orElseThrow(RaceNotFoundException::new);
            final RaceResponse raceToGetForecast=raceProcessor.process(new RaceRequest(race.getId_race()))
                    .getOrElseThrow(RaceNotFoundException::new);


            final FeignLocationResponse forecast=forecastClient.getForecast(FeignLocationRequest.builder()
                            .lat(raceToGetForecast.getLatitude())
                            .lon(raceToGetForecast.getLongitude())
                    .build());

            return ForecastResponse.builder()
                    .circuitName(raceToGetForecast.getCircuitName())
                    .condition(forecast.getCondition())
                    .temperature(forecast.getTemperature())
                    .humidity(forecast.getHumidity())
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof RaceNotFoundException)
                        return new NoSuchRaceError();
                    if(throwable instanceof RetryableException)
                        return new ForecastServiceUnavailableError();
                    if(throwable instanceof FeignException)
                        return new ForecastServiceUnavailableError();
                    return new GeneralServerError();
                });
    }
}

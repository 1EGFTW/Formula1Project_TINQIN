package com.tinqin.academy.processor.maps;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.error.forecast.ForecastServiceUnavailableError;
import com.tinqin.academy.error.maps.MapsServiceUnavailableError;
import com.tinqin.academy.error.race.NoSuchRaceError;
import com.tinqin.academy.exception.race.RaceNotFoundException;
import com.tinqin.academy.feign.MapsClient;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.model.racemap.RaceMapRequest;
import com.tinqin.academy.model.racemap.RaceMapResponse;
import com.tinqin.academy.model.racemap.feign.FeignMapRequest;
import com.tinqin.academy.model.racemap.feign.FeignMapResponse;
import com.tinqin.academy.operation.RaceMapProcessor;
import feign.FeignException;
import feign.RetryableException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

@Service
public class RaceMapProcessorCore implements RaceMapProcessor {
    private final MapsClient mapsClient;
    private final RaceRepository raceRepository;

    public RaceMapProcessorCore(MapsClient mapsClient, RaceRepository raceRepository) {
        this.mapsClient = mapsClient;
        this.raceRepository = raceRepository;
    }

    @Override
    public Either<Error, RaceMapResponse> process(RaceMapRequest input) {
        return Try.of(()->{
            final Race race=raceRepository.getRaceByCircuitName(input.getCircuitName())
                    .orElseThrow(RaceNotFoundException::new);

            final FeignMapResponse feignMapResponse=mapsClient.generateMaps(FeignMapRequest.builder()
                            .lat(race.getLatitude())
                            .lon(race.getLongitude())
                    .build());

            return RaceMapResponse.builder()
                    .imageData(feignMapResponse.getImageData())
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof RaceNotFoundException)
                        return new NoSuchRaceError();
                    if(throwable instanceof RetryableException)
                        return new MapsServiceUnavailableError();
                    if(throwable instanceof FeignException)
                        return new MapsServiceUnavailableError();
                    return new GeneralServerError();
                });
    }
}

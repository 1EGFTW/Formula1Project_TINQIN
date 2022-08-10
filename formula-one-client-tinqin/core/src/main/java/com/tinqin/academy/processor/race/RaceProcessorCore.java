package com.tinqin.academy.processor.race;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.error.race.NoSuchRaceError;
import com.tinqin.academy.exception.race.RaceNotFoundException;
import com.tinqin.academy.model.race.RaceRequest;
import com.tinqin.academy.model.race.RaceResponse;
import com.tinqin.academy.operation.RaceProcessor;
import com.tinqin.academy.data.repository.RaceRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class RaceProcessorCore implements RaceProcessor {
    private final ConversionService conversionService;
    private final RaceRepository raceRepository;

    public RaceProcessorCore(ConversionService conversionService, RaceRepository raceRepository) {
        this.conversionService = conversionService;
        this.raceRepository = raceRepository;
    }

    @Override
    public Either<Error, RaceResponse> process(final RaceRequest input) {
        return Try.of(()->{
            Race race=raceRepository.findById(input.getRaceId())
                    .orElseThrow(RaceNotFoundException::new);
            return conversionService.convert(race,RaceResponse.class);
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof RaceNotFoundException)
                        return new NoSuchRaceError();
                    return new GeneralServerError();
                });
    }
}

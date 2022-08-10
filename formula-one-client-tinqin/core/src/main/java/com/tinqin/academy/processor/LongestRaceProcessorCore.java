package com.tinqin.academy.processor;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Race;
import com.tinqin.academy.data.repository.RaceRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
import com.tinqin.academy.operation.LongestRaceProcessor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LongestRaceProcessorCore implements LongestRaceProcessor {
    private final RaceRepository raceRepository;

    public LongestRaceProcessorCore(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Either<Error, LongestRaceResponse> process(LongestRaceRequest input) {
        return Try.of(()->{
            List<Race> races=raceRepository.findAll();
            return races.stream()
                    .map(race -> {
                        Double totalDistance=race.getDistancePerLap()*race.getLaps();
                        return LongestRaceResponse.builder()
                                .circuitName(race.getCircuitName())
                                .totalDistance(String.valueOf(totalDistance)+" km")
                                .build();
                    })
                    .sorted(Comparator.comparing(LongestRaceResponse::getTotalDistance)
                            .reversed())
                    .findFirst()
                    .orElseThrow();
        }).toEither()
                .mapLeft(throwable -> {
                    return new GeneralServerError();
                });
    }
}

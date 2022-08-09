package com.tinqin.academy.converter;

import com.tinqin.academy.entity.Race;
import com.tinqin.academy.model.race.RaceResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaceToRaceResponseConverter implements Converter<Race, RaceResponse> {
    @Override
    public RaceResponse convert(Race source) {
        return RaceResponse.builder()
                .winnerFirstName(source.getWinner().getFirstName())
                .winnerLastName(source.getWinner().getLastName())
                .circuitName(source.getCircuitName())
                .latitude(source.getLatitude())
                .longitude(source.getLongitude())
                .raceDate(source.getRaceDate())
                .build();
    }
}

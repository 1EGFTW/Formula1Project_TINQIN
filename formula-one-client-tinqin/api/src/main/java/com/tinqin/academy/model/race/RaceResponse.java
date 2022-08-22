package com.tinqin.academy.model.race;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class RaceResponse implements OperationResult {
    private String circuitName;

    private LocalDate raceDate;

    private String winnerFirstName;

    private String winnerLastName;

    private Double latitude;

    private Double longitude;
}

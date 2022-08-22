package com.tinqin.academy.model.raceforecast;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode
@ToString
public class ForecastResponse implements OperationResult {
    private String circuitName;
    private String temperature;
    private String condition;
    private String humidity;
}

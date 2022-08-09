package com.tinqin.academy.model.raceforecast;

import com.tinqin.academy.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class ForecastRequest implements OperationInput {
    private String circuitName;
}

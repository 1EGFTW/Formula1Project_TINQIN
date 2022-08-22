package com.tinqin.academy.model.longestrace;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class LongestRaceResponse implements OperationResult {
    private String circuitName;
    private String totalDistance;
}

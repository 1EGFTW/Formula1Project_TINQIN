package com.tinqin.academy.model.longestrace;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class LongestRaceResponse implements OperationResult {
    private String circuitName;
    private String totalDistance;
}

package com.tinqin.academy.model.racemap;

import com.google.maps.ImageResult;
import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class RaceMapResponse implements OperationResult {
    private byte[] imageData;
}

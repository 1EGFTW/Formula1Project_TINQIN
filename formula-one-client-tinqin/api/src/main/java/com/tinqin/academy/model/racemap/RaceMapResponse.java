package com.tinqin.academy.model.racemap;

import com.google.maps.ImageResult;
import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RaceMapResponse implements OperationResult {
    private byte[] imageData;
}

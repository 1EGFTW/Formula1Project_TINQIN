package com.tinqin.academy.model.racemap.feign;

import com.google.maps.ImageResult;
import com.tinqin.academy.base.OperationInput;
import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeignMapResponse{
    private byte[] imageData;
}

package com.tinqin.academy.model.racemap.feign;

import com.tinqin.academy.base.OperationInput;
import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class FeignMapRequest {
    private Double lat;
    private Double lon;
}

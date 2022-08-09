package com.tinqin.academy.model.raceforecast.feign;

import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeignLocationRequest {
    private Double lat;
    private Double lon;
}

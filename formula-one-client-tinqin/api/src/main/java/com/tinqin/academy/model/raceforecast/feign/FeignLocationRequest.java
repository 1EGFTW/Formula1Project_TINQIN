package com.tinqin.academy.model.raceforecast.feign;

import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class FeignLocationRequest {
    private Double lat;
    private Double lon;
}

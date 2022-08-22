package com.tinqin.academy.model.raceforecast.feign;

import lombok.*;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class FeignLocationResponse {
    private String temperature;
    private String humidity;
    private String condition;
}

package com.tinqin.academy.model.raceforecast.feign;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class FeignLocationResponse {
    private String temperature;
    private String humidity;
    private String condition;
}

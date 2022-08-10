package com.tinqin.academy.feign;

import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import io.vavr.control.Either;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "forecast",url = "http://localhost:8081/")
public interface ForecastClient {
    @PostMapping("/getForecast")
    FeignLocationResponse getForecast(@RequestBody FeignLocationRequest locationRequest);
}

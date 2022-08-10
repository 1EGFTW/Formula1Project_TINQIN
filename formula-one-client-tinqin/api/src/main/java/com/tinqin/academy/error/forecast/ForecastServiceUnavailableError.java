package com.tinqin.academy.error.forecast;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class ForecastServiceUnavailableError implements Error {
    @Override
    public String getMessage() {
        return "Forecast service unavailable";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}

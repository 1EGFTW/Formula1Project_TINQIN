package com.tinqin.academy.error.maps;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class MapsServiceUnavailableError implements Error {
    @Override
    public String getMessage() {
        return "Maps service is unavailable!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}

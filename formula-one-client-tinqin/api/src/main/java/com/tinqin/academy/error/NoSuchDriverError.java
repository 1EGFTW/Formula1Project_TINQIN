package com.tinqin.academy.error;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class NoSuchDriverError implements Error {
    @Override
    public String getMessage() {
        return "No such driver exists!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}

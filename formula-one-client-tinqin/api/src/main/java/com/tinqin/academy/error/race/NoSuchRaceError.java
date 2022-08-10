package com.tinqin.academy.error.race;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class NoSuchRaceError implements Error {
    @Override
    public String getMessage() {
        return "No such race!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}

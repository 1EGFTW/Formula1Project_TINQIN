package com.tinqin.academy.error;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class NoSuchTeamError implements Error {
    @Override
    public String getMessage() {
        return "No such team exists!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}

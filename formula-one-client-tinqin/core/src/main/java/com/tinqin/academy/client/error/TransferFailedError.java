package com.tinqin.academy.client.error;

import com.tinqin.academy.client.base.Error;
import org.springframework.http.HttpStatus;

public class TransferFailedError implements Error {
    @Override
    public String getMessage() {
        return "Transfer failed";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}

package com.tinqin.academy.error.transfer;

import com.tinqin.academy.base.Error;
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

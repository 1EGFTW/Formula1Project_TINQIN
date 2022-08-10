package com.tinqin.academy.error.transfer;

import com.tinqin.academy.base.Error;
import org.springframework.http.HttpStatus;

public class BudgetTooLowError implements Error {
    @Override
    public String getMessage() {
        return "New team doesn't have enough funds!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}

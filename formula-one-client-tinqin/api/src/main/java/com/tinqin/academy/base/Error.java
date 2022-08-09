package com.tinqin.academy.base;

import org.springframework.http.HttpStatus;

public interface Error {
    String getMessage();
    HttpStatus getCode();
}

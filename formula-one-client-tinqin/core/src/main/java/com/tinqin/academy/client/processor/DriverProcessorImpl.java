package com.tinqin.academy.client.processor;

import com.tinqin.academy.client.base.Error;
import com.tinqin.academy.client.model.transfer.driver.DriverRequest;
import com.tinqin.academy.client.model.transfer.driver.DriverResponse;
import com.tinqin.academy.client.operation.DriverProcessor;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class DriverProcessorImpl implements DriverProcessor {
    @Override
    public Either<Error, DriverResponse> process(DriverRequest input) {
        return null;
    }
}

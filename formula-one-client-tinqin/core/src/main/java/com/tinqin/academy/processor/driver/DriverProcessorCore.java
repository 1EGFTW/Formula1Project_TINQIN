package com.tinqin.academy.processor.driver;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.error.driver.NoSuchDriverError;
import com.tinqin.academy.error.transfer.TransferFailedError;
import com.tinqin.academy.exception.driver.DriverNotFoundException;
import com.tinqin.academy.model.driver.DriverRequest;
import com.tinqin.academy.model.driver.DriverResponse;
import com.tinqin.academy.operation.DriverProcessor;
import com.tinqin.academy.data.repository.DriverRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DriverProcessorCore implements DriverProcessor {
    private final ConversionService conversionService;
    private final DriverRepository driverRepository;

    public DriverProcessorCore(ConversionService conversionService, DriverRepository driverRepository) {
        this.conversionService = conversionService;
        this.driverRepository = driverRepository;
    }

    @Override
    public Either<Error, DriverResponse> process(final DriverRequest input) {
        return Try.of(()->{
            final Driver driver=driverRepository.findById(input.getDriverId())
                    .orElseThrow(DriverNotFoundException::new);
            return conversionService.convert(driver,DriverResponse.class);
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof DriverNotFoundException){
                        return new NoSuchDriverError();
                    }
                    return new TransferFailedError();
                });
    }
}

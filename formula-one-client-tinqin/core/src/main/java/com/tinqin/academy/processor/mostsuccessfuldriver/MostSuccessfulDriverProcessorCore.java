package com.tinqin.academy.processor.mostsuccessfuldriver;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.error.driver.NoSuchDriverError;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverRequest;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import com.tinqin.academy.operation.MostSuccessfulDriverProcessor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MostSuccessfulDriverProcessorCore implements MostSuccessfulDriverProcessor {
    private final DriverRepository driverRepository;
    private final ConversionService conversionService;

    public MostSuccessfulDriverProcessorCore(DriverRepository driverRepository, ConversionService conversionService) {
        this.driverRepository = driverRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, MostSuccessfulDriverResponse> process(final MostSuccessfulDriverRequest input) {
        return Try.of(()->{
            final List<Driver> drivers=driverRepository.findAll();
            final Driver bestDriver=drivers.stream()
                    .sorted(Comparator.comparingInt(Driver::getChampionships)
                            .reversed())
                    .findFirst()
                    .orElseThrow();
            return conversionService.convert(bestDriver,MostSuccessfulDriverResponse.class);
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof NoSuchElementException)
                        return new NoSuchDriverError();
                    return new GeneralServerError();
                });
    }
}

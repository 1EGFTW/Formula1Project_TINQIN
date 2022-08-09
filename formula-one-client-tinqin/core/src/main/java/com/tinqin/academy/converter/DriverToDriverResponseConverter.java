package com.tinqin.academy.converter;

import com.tinqin.academy.entity.Driver;
import com.tinqin.academy.model.transfer.driver.DriverResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DriverToDriverResponseConverter implements Converter<Driver, DriverResponse> {
    @Override
    public DriverResponse convert(Driver source) {
        return DriverResponse.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .salary(String.valueOf(source.getSalary()))
                .team(source.getTeam().getTeamName())
                .build();
    }
}

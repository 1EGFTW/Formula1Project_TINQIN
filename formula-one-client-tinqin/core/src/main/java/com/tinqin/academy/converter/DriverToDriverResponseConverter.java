package com.tinqin.academy.converter;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.model.driver.DriverResponse;
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

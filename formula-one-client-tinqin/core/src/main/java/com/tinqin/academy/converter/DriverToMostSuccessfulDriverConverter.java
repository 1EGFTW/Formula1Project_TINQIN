package com.tinqin.academy.converter;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DriverToMostSuccessfulDriverConverter implements Converter<Driver, MostSuccessfulDriverResponse> {
    @Override
    public MostSuccessfulDriverResponse convert(Driver source) {
        return MostSuccessfulDriverResponse.builder()
                .driverFirstName(source.getFirstName())
                .driverLastName(source.getLastName())
                .titles(String.valueOf(source.getChampionships()))
                .teamName(source.getTeam().getTeamName())
                .build();
    }
}

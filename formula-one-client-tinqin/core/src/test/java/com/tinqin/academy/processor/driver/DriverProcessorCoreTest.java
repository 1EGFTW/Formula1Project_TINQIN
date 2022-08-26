package com.tinqin.academy.processor.driver;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.exception.driver.DriverNotFoundException;
import com.tinqin.academy.model.driver.DriverRequest;
import com.tinqin.academy.model.driver.DriverResponse;
import com.tinqin.academy.operation.DriverProcessor;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

class DriverProcessorCoreTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private DriverProcessorCore driverProcessorCore;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        driver.setId_driver(1L);

        when(driverRepository.findById(1L))
                .thenReturn(Optional.of(driver));

        when(conversionService.convert(driver,DriverResponse.class))
                .thenReturn(DriverResponse.builder()
                        .firstName(driver.getFirstName())
                        .lastName(driver.getLastName())
                        .salary(driver.getSalary().toString())
                        .team(team.getTeamName())
                        .build());

        DriverRequest driverRequest=new DriverRequest(1L);

        DriverResponse d=DriverResponse.builder()
                .firstName("Alexander")
                .lastName("Zhivkov")
                .salary("1000.0")
                .team(team.getTeamName())
                .build();

        Assertions.assertNotNull(driverProcessorCore.process(driverRequest).get());
        Assertions.assertEquals(d,driverProcessorCore.process(driverRequest).get());
    }

    @Test
    void testErrorsProcess(){
        final Team team=new Team("Team1",8000.0);
        final Driver driver=new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        driver.setId_driver(1L);

        when(driverRepository.findById(1L))
                .thenReturn(Optional.empty());

        when(conversionService.convert(driver,DriverResponse.class))
                .thenReturn(DriverResponse.builder()
                        .firstName(driver.getFirstName())
                        .lastName(driver.getLastName())
                        .salary(driver.getSalary().toString())
                        .team(team.getTeamName())
                        .build());

        DriverRequest driverRequest=new DriverRequest(1L);

        Assertions.assertTrue(driverProcessorCore.process(driverRequest).isLeft());
        Assertions.assertEquals("No such driver exists!",driverProcessorCore.process(driverRequest).getLeft().getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,driverProcessorCore.process(driverRequest).getLeft().getCode());

    }
}
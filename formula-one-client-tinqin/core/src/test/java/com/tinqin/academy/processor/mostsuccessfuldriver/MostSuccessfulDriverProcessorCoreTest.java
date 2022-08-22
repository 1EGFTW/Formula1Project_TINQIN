package com.tinqin.academy.processor.mostsuccessfuldriver;

import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverRequest;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import com.tinqin.academy.processor.longestrace.LongestRaceProcessorCore;
import org.junit.Assert;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*@SpringBootTest(classes = MostSuccessfulDriverProcessorCore.class)
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)*/
class MostSuccessfulDriverProcessorCoreTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private DriverRepository driverRepository;
    @InjectMocks
    private MostSuccessfulDriverProcessorCore mostSuccessfulDriverProcessorCore;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {

        final Team team=new Team("Team1",8000.0);
        final Driver driver1 = new Driver("Alexander", "Zhivkov", 1000.0, 8, team);
        final Driver driver2 = new Driver("Vladi", "Andreev", 100.0, 7, team);
        final Driver driver3 = new Driver("Tedi", "Tedi", 100.0, 4, team);


        when(driverRepository.findAll()).thenReturn(List.of(driver1,driver2,driver3));

        when(conversionService.convert(driver1, MostSuccessfulDriverResponse.class))
                .thenReturn(MostSuccessfulDriverResponse.builder()
                        .driverFirstName(driver1.getFirstName())
                        .driverLastName(driver1.getLastName())
                        .titles(String.valueOf(driver1.getChampionships()))
                        .teamName(team.getTeamName())
                        .build());

        when(conversionService.convert(driver2, MostSuccessfulDriverResponse.class))
                .thenReturn(MostSuccessfulDriverResponse.builder()
                        .driverFirstName(driver2.getFirstName())
                        .driverLastName(driver2.getLastName())
                        .titles(String.valueOf(driver2.getChampionships()))
                        .teamName(team.getTeamName())
                        .build());

        when(conversionService.convert(driver3, MostSuccessfulDriverResponse.class))
                .thenReturn(MostSuccessfulDriverResponse.builder()
                        .driverFirstName(driver3.getFirstName())
                        .driverLastName(driver3.getLastName())
                        .titles(String.valueOf(driver3.getChampionships()))
                        .teamName(team.getTeamName())
                        .build());


        MostSuccessfulDriverResponse mr=MostSuccessfulDriverResponse.builder()
                .driverFirstName("Alexander")
                .driverLastName("Zhivkov")
                .titles("8")
                .teamName("Team1")
                .build();

        MostSuccessfulDriverRequest m=new MostSuccessfulDriverRequest();

        Assertions.assertNotNull(mostSuccessfulDriverProcessorCore.process(m).get());
        Assertions.assertEquals(mr,mostSuccessfulDriverProcessorCore.process(m).get());
       /* Assertions.assertNull(mostSuccessfulDriverProcessorCore.process(m).getLeft());*/

    }
}
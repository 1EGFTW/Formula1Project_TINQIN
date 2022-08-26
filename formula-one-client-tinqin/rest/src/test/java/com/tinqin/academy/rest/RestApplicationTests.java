package com.tinqin.academy.rest;

import com.tinqin.academy.feign.ForecastClient;
import com.tinqin.academy.feign.MapsClient;
import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverRequest;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamRequest;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamResponse;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationRequest;
import com.tinqin.academy.model.raceforecast.feign.FeignLocationResponse;
import com.tinqin.academy.model.racemap.RaceMapRequest;
import com.tinqin.academy.model.racemap.RaceMapResponse;
import com.tinqin.academy.model.racemap.feign.FeignMapRequest;
import com.tinqin.academy.model.racemap.feign.FeignMapResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.rest.controller.HomeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class RestApplicationTests {
    @Autowired
    private HomeController homeController;

    @MockBean
    private ForecastClient forecastClient;

    @MockBean
    private MapsClient mapsClient;

    @Test
    void contextLoads() {

    }

    @Test
    void testTransfer(){
        TransferRequest transferRequest= TransferRequest.builder()
                .driverId(2L)
                .newTeamId(2L)
                .build();

        TransferResponse transferResponse= TransferResponse.builder()
                .driverFirstName("Vladi")
                .driverLastName("Andreev")
                .oldTeamName("Mercedes")
                .newTeamName("Red Bull")
                .build();
        ResponseEntity<TransferResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(transferResponse);


        Assertions.assertEquals(response,homeController.transfer(transferRequest));

        TransferRequest fakeRequest=TransferRequest.builder()
                        .driverId(7L)
                                .newTeamId(8L)
                                        .build();

        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE,homeController.transfer(fakeRequest).getStatusCode());
        Assertions.assertEquals("Transfer failed",homeController.transfer(fakeRequest).getBody().toString());
    }

    @Test
    void testGetLongestRace(){
        LongestRaceRequest longestRaceRequest=new LongestRaceRequest();

        LongestRaceResponse longestRaceResponse= LongestRaceResponse.builder()
                .circuitName("Silverstone")
                .totalDistance("80.0 km")
                .build();

        ResponseEntity<LongestRaceResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(longestRaceResponse);

        Assertions.assertEquals(response,homeController.getLongestRace(longestRaceRequest));
    }

    @Test
    void testGetMostSuccessfulTeam(){

        MostSuccessfulTeamRequest mostSuccessfulTeamRequest=new MostSuccessfulTeamRequest();

        MostSuccessfulTeamResponse mostSuccessfulTeamResponse= MostSuccessfulTeamResponse.builder()
                .teamName("Mercedes")
                .numberOfTitles("13")
                .build();

        ResponseEntity<MostSuccessfulTeamResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(mostSuccessfulTeamResponse);

        Assertions.assertEquals(response,homeController.getMostSuccessfulTeam(mostSuccessfulTeamRequest));
    }

    @Test
    void testGetMostSuccessfulDriver(){
        MostSuccessfulDriverRequest mostSuccessfulDriverRequest=new MostSuccessfulDriverRequest();

        MostSuccessfulDriverResponse mostSuccessfulDriverResponse= MostSuccessfulDriverResponse.builder()
                .teamName("Mercedes")
                .titles("8")
                .driverLastName("Zhivkov")
                .driverFirstName("Alexander")
                .build();

        ResponseEntity<MostSuccessfulDriverResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(mostSuccessfulDriverResponse);

        Assertions.assertEquals(response,homeController.getMostSuccessfulDriver(mostSuccessfulDriverRequest));
    }

    @Test
    void testGetForecastForRace(){
        FeignLocationRequest feignLocationRequest= FeignLocationRequest.builder()
                .lat(10.0)
                .lon(12.0)
                .build();
        when(forecastClient.getForecast(feignLocationRequest))
                .thenReturn(FeignLocationResponse.builder()
                        .temperature("20.0")
                        .humidity("99")
                        .condition("Sunny")
                        .build());

        ForecastRequest forecastRequest= ForecastRequest.builder()
                .circuitName("Spa")
                .build();

        ForecastResponse forecastResponse= ForecastResponse.builder()
                .circuitName("Spa")
                .humidity("99")
                .temperature("20.0")
                .condition("Sunny")
                .build();

        ResponseEntity<ForecastResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(forecastResponse);

        Assertions.assertEquals(response,homeController.getForecast(forecastRequest));

        ForecastRequest fakeRequest= ForecastRequest.builder()
                .circuitName("dfasdas")
                .build();
        Assertions.assertEquals("No such race!",homeController.getForecast(fakeRequest).getBody().toString());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,homeController.getForecast(fakeRequest).getStatusCode());
    }

    @Test
    void testGetMapsForRace(){
        FeignMapRequest feignMapRequest= FeignMapRequest.builder()
                .lat(10.0)
                .lon(12.0)
                .build();
        final byte[] imageData=new byte[10];
        when(mapsClient.generateMaps(feignMapRequest))
                .thenReturn(FeignMapResponse.builder()
                        .imageData(imageData)
                        .build());

        RaceMapRequest raceMapRequest= RaceMapRequest.builder()
                .circuitName("Spa")
                .build();

        RaceMapResponse raceMapResponse= RaceMapResponse.builder()
                .imageData(imageData)
                .build();

        ResponseEntity<RaceMapResponse> response=ResponseEntity.status(HttpStatus.OK)
                .body(raceMapResponse);

        Assertions.assertEquals(response.getStatusCode(),homeController.getLocation(raceMapRequest).getStatusCode());
        RaceMapRequest fakeRequest= RaceMapRequest.builder()
                .circuitName("dfasdas")
                .build();
        Assertions.assertEquals("No such race!",homeController.getLocation(fakeRequest).getBody().toString());
        Assertions.assertEquals(HttpStatus.NOT_FOUND,homeController.getLocation(fakeRequest).getStatusCode());
    }

}

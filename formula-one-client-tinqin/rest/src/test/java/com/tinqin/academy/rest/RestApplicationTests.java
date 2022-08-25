package com.tinqin.academy.rest;

import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverRequest;
import com.tinqin.academy.model.mostsuccessfuldriver.MostSuccessfulDriverResponse;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamRequest;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.rest.controller.HomeController;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class RestApplicationTests {
    @Autowired
    private HomeController homeController;

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

}

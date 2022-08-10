package com.tinqin.academy.rest.controller;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.operation.ForecastForRaceProcessor;
import com.tinqin.academy.operation.LongestRaceProcessor;
import com.tinqin.academy.operation.TransferProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
   private final TransferProcessor transferProcessor;
   private final ForecastForRaceProcessor forecastForRaceProcessor;
   private final LongestRaceProcessor longestRaceProcessor;

   public HomeController(TransferProcessor transferProcessor, ForecastForRaceProcessor forecastForRaceProcessor, LongestRaceProcessor longestRaceProcessor) {
      this.transferProcessor = transferProcessor;
      this.forecastForRaceProcessor = forecastForRaceProcessor;
      this.longestRaceProcessor = longestRaceProcessor;
   }

   //transfer
   @PostMapping("/transfer")
   public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest){
      Either<Error, TransferResponse> response=transferProcessor.process(transferRequest); //da se refactorira
      if(response.isLeft()){
         return ResponseEntity.status(response.getLeft().getCode()).body(response.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(response.get());
   }
   @PostMapping("/forecastForRace")
   public ResponseEntity<?> getForecast(@RequestBody ForecastRequest forecastRequest){
      Either<Error, ForecastResponse> response=forecastForRaceProcessor.process(forecastRequest);
      if(response.isLeft()){
         return ResponseEntity.status(response.getLeft().getCode()).body(response.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(response.get());
   }
   @GetMapping("/longestRace")
   public ResponseEntity<?> getLongestRace(@RequestParam(required = false) LongestRaceRequest longestRaceRequest){
      Either<Error, LongestRaceResponse> response=longestRaceProcessor.process(longestRaceRequest);
      if(response.isLeft()){
         return ResponseEntity.status(response.getLeft().getCode()).body(response.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(response.get());
   }
}

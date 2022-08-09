package com.tinqin.academy.rest.controller;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
import com.tinqin.academy.operation.ForecastForRaceProcessor;
import com.tinqin.academy.operation.TransferProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
   private final TransferProcessor transferProcessor;
   private final ForecastForRaceProcessor forecastForRaceProcessor;

   public HomeController(TransferProcessor transferProcessor, ForecastForRaceProcessor forecastForRaceProcessor) {
      this.transferProcessor = transferProcessor;
      this.forecastForRaceProcessor = forecastForRaceProcessor;
   }

   //transfer
   @PostMapping("/transfer")
   public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest){
      Either<Error, TransferResponse> responses=transferProcessor.process(transferRequest);
      if(responses.isLeft()){
         return ResponseEntity.status(responses.getLeft().getCode()).body(responses.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(responses.get());
   }
   @PostMapping("/forecastForRace")
   public ResponseEntity<?> getForecast(@RequestBody ForecastRequest forecastRequest){
      Either<Error, ForecastResponse> responses=forecastForRaceProcessor.process(forecastRequest);
      if(responses.isLeft()){
         return ResponseEntity.status(responses.getLeft().getCode()).body(responses.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(responses.get());
   }
}

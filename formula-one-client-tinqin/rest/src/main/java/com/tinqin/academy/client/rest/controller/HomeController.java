package com.tinqin.academy.client.rest.controller;

import com.tinqin.academy.client.base.Error;
import com.tinqin.academy.client.model.transfer.TransferRequest;
import com.tinqin.academy.client.model.transfer.TransferResponse;
import com.tinqin.academy.client.operation.TransferProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
   private final TransferProcessor transferProcessor;

   public HomeController(TransferProcessor transferProcessor) {
      this.transferProcessor = transferProcessor;
   }

   //transfer
   @PostMapping("/transfer")
   public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest){
      Either<Error,TransferResponse> responses=transferProcessor.process(transferRequest);
      if(responses.isLeft()){
         return ResponseEntity.status(responses.getLeft().getCode()).body(responses.getLeft().getMessage());
      }
      return ResponseEntity.status(HttpStatus.OK).body(responses.get());
   }
}

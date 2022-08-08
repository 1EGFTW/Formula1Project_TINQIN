package com.tinqin.academy.client.processor;

import com.tinqin.academy.client.base.Error;
import com.tinqin.academy.client.model.transfer.TransferRequest;
import com.tinqin.academy.client.model.transfer.TransferResponse;
import com.tinqin.academy.client.model.transfer.driver.DriverRequest;
import com.tinqin.academy.client.model.transfer.driver.DriverResponse;
import com.tinqin.academy.client.model.transfer.team.TeamRequest;
import com.tinqin.academy.client.model.transfer.team.TeamResponse;
import com.tinqin.academy.client.operation.DriverProcessor;
import com.tinqin.academy.client.operation.TeamProcessor;
import com.tinqin.academy.client.operation.TransferProcessor;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class TransferProcessorImpl implements TransferProcessor {
    private final DriverProcessor driverProcessor;
    private final TeamProcessor teamProcessor;

    public TransferProcessorImpl(DriverProcessor driverProcessor, TeamProcessor teamProcessor) {
        this.driverProcessor = driverProcessor;
        this.teamProcessor = teamProcessor;
    }

    @Override
    public Either<Error, TransferResponse> process(TransferRequest input) {
        Either<Error, DriverResponse> driverResponses=driverProcessor.process(DriverRequest.builder()
                        .driverId(input.getDriverId())
                .build());
        Either<Error, TeamResponse> teamResponses=teamProcessor.process(TeamRequest.builder()
                        .teamId(input.getTeamId())
                .build());
    }
}

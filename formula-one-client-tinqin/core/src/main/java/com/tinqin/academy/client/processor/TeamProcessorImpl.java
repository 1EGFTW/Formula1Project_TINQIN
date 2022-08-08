package com.tinqin.academy.client.processor;

import com.tinqin.academy.client.base.Error;
import com.tinqin.academy.client.model.transfer.team.TeamRequest;
import com.tinqin.academy.client.model.transfer.team.TeamResponse;
import com.tinqin.academy.client.operation.TeamProcessor;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class TeamProcessorImpl implements TeamProcessor {
    @Override
    public Either<Error, TeamResponse> process(TeamRequest input) {
        return null;
    }
}

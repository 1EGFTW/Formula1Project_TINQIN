package com.tinqin.academy.processor.team;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.error.team.NoSuchTeamError;
import com.tinqin.academy.error.transfer.TransferFailedError;
import com.tinqin.academy.exception.team.TeamNotFoundException;
import com.tinqin.academy.model.team.TeamRequest;
import com.tinqin.academy.model.team.TeamResponse;
import com.tinqin.academy.operation.TeamProcessor;
import com.tinqin.academy.data.repository.TeamRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TeamProcessorCore implements TeamProcessor {
    private final ConversionService conversionService;
    private final TeamRepository teamRepository;

    public TeamProcessorCore(ConversionService conversionService, TeamRepository teamRepository) {
        this.conversionService = conversionService;
        this.teamRepository = teamRepository;
    }

    @Override
    public Either<Error, TeamResponse> process(final TeamRequest input) {
        return Try.of(()->{
            final Team team=teamRepository.findById(input.getTeamId())
                    .orElseThrow(TeamNotFoundException::new);
            return conversionService.convert(team,TeamResponse.class);
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof TeamNotFoundException)
                        return new NoSuchTeamError();
                    return new TransferFailedError();
                });
    }
}

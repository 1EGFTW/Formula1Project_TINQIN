package com.tinqin.academy.processor.mostsuccessfulteam;

import com.tinqin.academy.base.Error;
import com.tinqin.academy.data.entity.Driver;
import com.tinqin.academy.data.entity.Team;
import com.tinqin.academy.data.repository.DriverRepository;
import com.tinqin.academy.data.repository.TeamRepository;
import com.tinqin.academy.error.GeneralServerError;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamRequest;
import com.tinqin.academy.model.mostsuccessfulteam.MostSuccessfulTeamResponse;
import com.tinqin.academy.operation.MostSuccessfulTeamProcessor;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MostSuccessfulTeamProcessorCore implements MostSuccessfulTeamProcessor {
    private final TeamRepository teamRepository;
    private final DriverRepository driverRepository;


    public MostSuccessfulTeamProcessorCore(TeamRepository teamRepository, DriverRepository driverRepository) {
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Either<Error, MostSuccessfulTeamResponse> process(final MostSuccessfulTeamRequest input) {
        return Try.of(()->{
            final List<Team> teams=teamRepository.findAll();
            final Map<Integer,String> result=new HashMap<>();
            teams.stream()
                    .forEach(team -> {
                        final List<Integer> allTeamTitles= driverRepository.findDriversByTeam(team)
                                .stream()
                                .map(Driver::getChampionships)
                                .toList();
                        Integer titlesForTeam=0;
                        for(Integer title:allTeamTitles){
                            titlesForTeam+=title;
                        }
                        result.put(titlesForTeam,team.getTeamName());
                    });
            final Integer mostTitles=result.keySet().stream()
                    .max(Integer::compare)
                    .orElseThrow();

            return MostSuccessfulTeamResponse.builder()
                    .teamName(result.get(mostTitles))
                    .numberOfTitles(String.valueOf(mostTitles))
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    return new GeneralServerError();
                });
    }
}

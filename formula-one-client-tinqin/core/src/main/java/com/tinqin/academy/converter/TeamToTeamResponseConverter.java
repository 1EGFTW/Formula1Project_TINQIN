package com.tinqin.academy.converter;

import com.tinqin.academy.entity.Team;
import com.tinqin.academy.model.team.TeamResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamResponseConverter implements Converter<Team, TeamResponse> {
    @Override
    public TeamResponse convert(Team source) {
        return TeamResponse.builder()
                .teamName(source.getTeamName())
                .budget(String.valueOf(source.getBudget()))
                .build();
    }
}

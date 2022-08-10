package com.tinqin.academy.model.mostsuccessfulteam;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class MostSuccessfulTeamResponse implements OperationResult {
    private String teamName;
    private String numberOfTitles;
}

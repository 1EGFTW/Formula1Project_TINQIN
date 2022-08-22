package com.tinqin.academy.model.mostsuccessfulteam;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class MostSuccessfulTeamResponse implements OperationResult {
    private String teamName;
    private String numberOfTitles;
}

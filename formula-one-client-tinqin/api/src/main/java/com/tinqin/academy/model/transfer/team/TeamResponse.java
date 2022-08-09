package com.tinqin.academy.model.transfer.team;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class TeamResponse implements OperationResult {
    private String teamName;
    private String budget;
}

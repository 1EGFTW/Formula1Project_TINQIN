package com.tinqin.academy.client.model.transfer.team;

import com.tinqin.academy.client.base.OperationInput;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TeamRequest implements OperationInput {
    private Integer teamId;
}

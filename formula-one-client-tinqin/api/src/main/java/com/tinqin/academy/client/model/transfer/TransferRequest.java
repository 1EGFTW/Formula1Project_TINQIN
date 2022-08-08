package com.tinqin.academy.client.model.transfer;

import com.tinqin.academy.client.base.OperationInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class TransferRequest implements OperationInput {
    private Integer driverId;
    private Integer teamId;
    private Integer newTeamId;
}

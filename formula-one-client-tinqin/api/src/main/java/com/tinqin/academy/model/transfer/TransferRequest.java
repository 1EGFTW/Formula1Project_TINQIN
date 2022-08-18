package com.tinqin.academy.model.transfer;

import com.tinqin.academy.base.OperationInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class TransferRequest implements OperationInput {
    private Long driverId;
    private Long newTeamId;
}

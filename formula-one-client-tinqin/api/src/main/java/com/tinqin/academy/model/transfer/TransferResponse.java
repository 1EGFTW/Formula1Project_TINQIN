package com.tinqin.academy.model.transfer;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class TransferResponse implements OperationResult {
    private String driverFirstName;
    private String driverLastName;
    private String oldTeamName;
    private String newTeamName;
}

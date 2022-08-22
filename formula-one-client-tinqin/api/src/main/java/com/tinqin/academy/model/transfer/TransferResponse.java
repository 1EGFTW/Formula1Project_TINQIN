package com.tinqin.academy.model.transfer;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class TransferResponse implements OperationResult {
    private String driverFirstName;
    private String driverLastName;
    private String oldTeamName;
    private String newTeamName;
}

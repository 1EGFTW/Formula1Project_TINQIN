package com.tinqin.academy.client.model.transfer.driver;

import com.tinqin.academy.client.base.OperationInput;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class DriverRequest implements OperationInput {
    private Integer driverId;
}

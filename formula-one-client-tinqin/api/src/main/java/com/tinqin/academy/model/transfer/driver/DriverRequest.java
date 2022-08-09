package com.tinqin.academy.model.transfer.driver;

import com.tinqin.academy.base.OperationInput;
import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequest implements OperationInput {
    private Long driverId;
}

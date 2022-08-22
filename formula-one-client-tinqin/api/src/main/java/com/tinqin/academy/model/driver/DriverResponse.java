package com.tinqin.academy.model.driver;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class DriverResponse implements OperationResult {
    private String firstName;
    private String lastName;
    private String salary;
    private String team;
}

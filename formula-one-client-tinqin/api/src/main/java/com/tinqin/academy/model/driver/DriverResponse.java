package com.tinqin.academy.model.driver;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class DriverResponse implements OperationResult {
    private String firstName;
    private String lastName;
    private String salary;
    private String team;
}

package com.tinqin.academy.model.mostsuccessfuldriver;

import com.tinqin.academy.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
public class MostSuccessfulDriverResponse implements OperationResult {
    private String driverFirstName;
    private String driverLastName;
    private String titles;
    private String teamName;
}

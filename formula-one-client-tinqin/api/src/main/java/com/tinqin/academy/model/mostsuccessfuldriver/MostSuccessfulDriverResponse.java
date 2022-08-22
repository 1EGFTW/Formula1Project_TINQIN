package com.tinqin.academy.model.mostsuccessfuldriver;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

@Builder
@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class MostSuccessfulDriverResponse implements OperationResult {
    private String driverFirstName;
    private String driverLastName;
    private String titles;
    private String teamName;
}

package com.tinqin.academy.model.racemap;

import com.tinqin.academy.base.OperationInput;
import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RaceMapRequest implements OperationInput {
    private String circuitName;
}

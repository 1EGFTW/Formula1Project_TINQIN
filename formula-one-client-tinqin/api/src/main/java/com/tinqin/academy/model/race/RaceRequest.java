package com.tinqin.academy.model.race;

import com.tinqin.academy.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RaceRequest implements OperationInput {
    private Long raceId;
}

package com.tinqin.academy.model.team;

import com.tinqin.academy.base.OperationInput;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class TeamRequest implements OperationInput {
    private Long teamId;
}

package com.tinqin.academy.model.team;

import com.tinqin.academy.base.OperationResult;
import lombok.*;

import java.util.List;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse implements OperationResult {
    private String teamName;
    private String budget;
}

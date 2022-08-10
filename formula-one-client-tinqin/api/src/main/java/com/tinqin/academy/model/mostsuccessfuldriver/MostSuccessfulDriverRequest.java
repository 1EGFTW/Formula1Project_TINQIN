package com.tinqin.academy.model.mostsuccessfuldriver;

import com.tinqin.academy.base.OperationInput;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Getter
public class MostSuccessfulDriverRequest implements OperationInput {
}

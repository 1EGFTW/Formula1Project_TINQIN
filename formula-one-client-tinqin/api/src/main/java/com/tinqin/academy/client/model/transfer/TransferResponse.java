package com.tinqin.academy.client.model.transfer;

import com.tinqin.academy.client.base.OperationResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter(AccessLevel.PRIVATE)
@Getter
public class TransferResponse implements OperationResult {
}

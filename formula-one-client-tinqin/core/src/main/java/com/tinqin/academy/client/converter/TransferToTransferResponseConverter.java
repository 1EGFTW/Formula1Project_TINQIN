package com.tinqin.academy.client.converter;

import com.tinqin.academy.client.model.transfer.TransferRequest;
import com.tinqin.academy.client.model.transfer.TransferResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransferToTransferResponseConverter implements Converter<TransferRequest, TransferResponse> {
    @Override
    public TransferResponse convert(TransferRequest source) {
        return new TransferResponse();
    }
}

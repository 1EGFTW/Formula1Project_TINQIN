package com.tinqin.academy.operation;

import com.tinqin.academy.base.OperationProcessor;
import com.tinqin.academy.model.transfer.TransferRequest;
import com.tinqin.academy.model.transfer.TransferResponse;
//pone 5 operacii
/*Operation receives driverID, oldTeamId and newTeamID.
* If there is no driver with that id, an error is thrown and the transfer fails.
* If there are no teams with the given ids, errors are thrown and the transfer fails.
* For a successful transfer, the new team must be able to afford the driver's salary and FIA's transfer tax.
* If the team doesn't have enough funds, an error is thrown and the transfer fails.
* If the transfer is successful, a response is returned, containing the name of the driver,
* the name of their old team and the name of their new team.
* All associating costs regarding the transfer are deducted from the new team's budget.*/
public interface TransferProcessor extends OperationProcessor<TransferRequest, TransferResponse> {
}

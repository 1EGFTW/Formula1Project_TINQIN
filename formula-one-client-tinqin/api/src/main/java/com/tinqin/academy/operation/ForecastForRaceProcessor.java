package com.tinqin.academy.operation;

import com.tinqin.academy.base.OperationProcessor;
import com.tinqin.academy.model.raceforecast.ForecastRequest;
import com.tinqin.academy.model.raceforecast.ForecastResponse;
/*Returns a forecast based on the location of a race.
* Input is the race's circuit name.
* If there is no such race, an exception is thrown.
* The operation depends on an external API, which consumes latitude and longitude coordinates
* and returns a formatted response regarding the input data.
* If the forecast service is unavailable, an exception is thrown.*/
public interface ForecastForRaceProcessor extends OperationProcessor<ForecastRequest, ForecastResponse> {
}

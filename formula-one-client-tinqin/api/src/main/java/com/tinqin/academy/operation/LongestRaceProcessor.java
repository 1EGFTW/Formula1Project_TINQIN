package com.tinqin.academy.operation;

import com.tinqin.academy.base.OperationProcessor;
import com.tinqin.academy.model.longestrace.LongestRaceRequest;
import com.tinqin.academy.model.longestrace.LongestRaceResponse;
/*Returns a response containing the name and total distance of the longest race,
* based on the race's distance per lap and total number of laps*/
public interface LongestRaceProcessor extends OperationProcessor<LongestRaceRequest, LongestRaceResponse> {
}

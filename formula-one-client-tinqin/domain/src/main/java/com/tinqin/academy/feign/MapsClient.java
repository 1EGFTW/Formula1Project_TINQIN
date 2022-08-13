package com.tinqin.academy.feign;

import com.tinqin.academy.model.racemap.RaceMapRequest;
import com.tinqin.academy.model.racemap.RaceMapResponse;
import com.tinqin.academy.model.racemap.feign.FeignMapRequest;
import com.tinqin.academy.model.racemap.feign.FeignMapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "maps",url = "http://localhost:8082/")
public interface MapsClient {
    @PostMapping("/maps")
    FeignMapResponse generateMaps(@RequestBody FeignMapRequest raceMapRequest);
}

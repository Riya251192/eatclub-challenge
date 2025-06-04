package com.eatclub.controller;

import com.eatclub.model.DealDetails;
import com.eatclub.service.EatClubDealContext;
import com.eatclub.service.EatClubDealService;
import com.eatclub.service.impl.EatClubTimedDealStrategyImpl;
import com.eatclub.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/eatclub/deals")
public class EatClubDealsController {

    @Autowired
    EatClubDealService eatClubDealService;

    @GetMapping
    public ResponseEntity<List<DealDetails>> getDealsAtTime(@RequestParam(Constants.TIME_OF_DAY) String timeOfDay) {
        return new ResponseEntity(eatClubDealService.computeDeals(Constants.TIME_OF_DAY,timeOfDay),HttpStatus.OK);
    }

    @GetMapping("/peak-time")
    public Map<String, String> getPeakTime() {
        return eatClubDealService.computeDealPeakTime();
    }
}

package com.eatclub.controller;

import com.eatclub.model.DealDetails;
import com.eatclub.service.EatClubDealStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/eatclub/deals")
public class EatClubDealsController {

    @Autowired
    EatClubDealStrategy eatClubDealStrategy;

    @GetMapping
    public ResponseEntity<List<DealDetails>> getDealsAtTime(@RequestParam String timeOfDay) {
        return new ResponseEntity(eatClubDealStrategy.computeDeals(timeOfDay), HttpStatus.OK);

    }

//    @GetMapping("/peak-time")
//    public Map<String, String> getPeakTime() {
//        return eatClubDealStrategy.calculatePeakTimeWindow();
//    }
}

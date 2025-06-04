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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/v1/eatclub/deals")
public class EatClubDealsController {

    @Autowired
    EatClubDealService eatClubDealService;

    @GetMapping
    public ResponseEntity<Map<String , List<DealDetails>>> getDealsAtTime(@RequestParam String timeOfDay) {
        return new ResponseEntity(Map.of("deals",eatClubDealService.computeDeals(Constants.TIME_OF_DAY,timeOfDay)),HttpStatus.OK);
    }

    @GetMapping("/peak-time")
    public ResponseEntity<Map<String, String>> getPeakTime() {
        return new ResponseEntity(eatClubDealService.computeDealPeakTime(), HttpStatus.OK);
    }


    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getDealsAtTime(@RequestParam String timeOfDay ,@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "2") int size) {
        List<DealDetails> allDeals = eatClubDealService.computeDeals(Constants.TIME_OF_DAY,timeOfDay);
        int start = page * size;
        int end = Math.min(start + size, allDeals.size());

        if (start > allDeals.size()) {
            return ResponseEntity.ok(Map.of(
                    "deals", Collections.emptyList(),
                    "currentPage", page,
                    "totalItems", allDeals.size(),
                    "totalPages", (allDeals.size() + size - 1) / size
            ));
        }

        List<DealDetails> paginated = allDeals.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("deals", paginated);
        response.put("currentPage", page);
        response.put("totalItems", allDeals.size());
        response.put("totalPages", (allDeals.size() + size - 1) / size);

        return ResponseEntity.ok(response);
    }
}

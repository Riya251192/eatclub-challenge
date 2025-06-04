package com.eatclub.service.impl;

import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.service.EatClubDealService;
import com.eatclub.service.EatClubDealContext;
import com.eatclub.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class EatClubDealServiceImpl implements EatClubDealService {

    @Autowired
    EatClubDealContext eatClubDealContext;

    @Autowired
    RestaurantDealsRepository restaurantDealsRepository;
    @Autowired
    Helper helper;

    @Override
    public List<DealDetails> computeDeals(String filter , String value) {
        return eatClubDealContext.computeDeals(filter,value);
    }

    @Override
    public Map<String, String> computeDealPeakTime() {
        Map<LocalTime, Integer> timeCounts = new TreeMap<>();
        for (Restaurant restaurant : restaurantDealsRepository.retrieveRestaurantDeals()) {
            for (Deal deal : restaurant.getDeals()) {
                LocalDateTime dealStart = deal.getStart() != null ? helper.retrieveLocalDateTime(deal.getStart()) : helper.retrieveLocalDateTime(restaurant.getOpen());
                LocalDateTime dealEnd = deal.getEnd() != null ? helper.retrieveLocalDateTime(deal.getEnd()) : helper.retrieveLocalDateTime(restaurant.getClose());
                for (LocalDateTime time = dealStart.withMinute(0); !time.isAfter(dealEnd) ; time = time.plusHours(1)) {
                    timeCounts.put(time.toLocalTime(), timeCounts.getOrDefault(time.toLocalTime(), 0) + 1);
                }
            }
        }

        LocalTime peakTime = timeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(LocalTime.of(0, 0));

        return Map.of(
                "peakTimeStart", peakTime.toString(),
                "peakTimeEnd", peakTime.plusHours(1).toString()
        );
    }
}

package com.eatclub.service.impl;

import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.service.EatClubDealService;
import com.eatclub.service.EatClubDealContext;
import com.eatclub.utility.Constants;
import com.eatclub.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class EatClubDealServiceImpl implements EatClubDealService {

    @Autowired
    public
    EatClubDealContext eatClubDealContext;

    @Autowired
    public
    RestaurantDealsRepository restaurantDealsRepository;
    @Autowired
    public
    Helper helper;


    @Override
    public List<DealDetails> computeDeals(String filter, String value) {
        return eatClubDealContext.computeDeals(filter, value);
    }

    @Override
    public Map<String, String> computeDealPeakTime() {
        Map<LocalTime, Integer> timeCounts = new TreeMap<>();
        for (Restaurant restaurant : restaurantDealsRepository.retrieveRestaurantDeals()) {
            for (Deal deal : restaurant.getDeals()) {
                LocalDateTime dealStart = Objects.nonNull(deal.getStart()) ? helper.retrieveLocalDateTime(deal.getStart()) : helper.retrieveLocalDateTime(restaurant.getOpen());
                LocalDateTime dealEnd = Objects.nonNull(deal.getEnd()) ? helper.retrieveLocalDateTime(deal.getEnd()) : helper.retrieveLocalDateTime(restaurant.getClose());
                for (LocalDateTime time = dealStart.withMinute(0); !time.isAfter(dealEnd); time = time.plusHours(1)) {
                    timeCounts.put(time.toLocalTime(), timeCounts.getOrDefault(time.toLocalTime(), 0) + 1);
                }
            }
        }

        Optional<LocalTime> peakTime = timeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        if (peakTime.isPresent()) {
            return Map.of(
                    Constants.PEAK_START, peakTime.get().toString(),
                    Constants.PEAK_END, peakTime.get().plusHours(1).toString()
            );
        }
        return Map.of(
                Constants.PEAK_START, Constants.NO_DEALS,
                Constants.PEAK_END, Constants.NO_DEALS
        );

    }
}

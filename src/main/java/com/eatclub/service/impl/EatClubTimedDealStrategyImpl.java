package com.eatclub.service.impl;

import com.eatclub.mapper.DealDetailsMapper;
import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.service.EatClubDealStrategy;
import com.eatclub.utility.Constants;
import com.eatclub.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
public class EatClubTimedDealStrategyImpl implements EatClubDealStrategy {

    @Autowired
    RestaurantDealsRepository restaurantDealsRepository;

    @Autowired
    Helper helper;

    @Autowired
    DealDetailsMapper mapper;

    @Override
    public List<DealDetails> computeDeals(String timeOfDay) {

        LocalTime queryTime = helper.retrieveLocalTime(timeOfDay);
        List<DealDetails> deals = new ArrayList<>();

        for (Restaurant restaurant : restaurantDealsRepository.retrieveRestaurantDeals()) {
            for (Deal deal : restaurant.getDeals()) {
                LocalTime dealStart = deal.getStart() != null ? helper.retrieveLocalTime(deal.getStart()) : helper.retrieveLocalTime(restaurant.getOpen());
                LocalTime dealEnd = deal.getEnd() != null ? helper.retrieveLocalTime(deal.getEnd()) : helper.retrieveLocalTime(restaurant.getClose());
                if (!queryTime.isBefore(dealStart) && !queryTime.isAfter(dealEnd)) {
                    DealDetails dealDetails = mapper.mapToDealDetails(restaurant, deal);
                    deals.add(dealDetails);
                }
            }
        }
        return deals;
    }

    public String getType() {
        return Constants.TIME_OF_DAY;
    }

}

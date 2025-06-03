package com.eatclub.service.impl;

import com.eatclub.mapper.DealDetailsMapper;
import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.service.EatClubDealStrategy;
import com.eatclub.utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.*;

@Component
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
            if (restaurant.getOpen()!=null && restaurant.getOpen().length() < 7) restaurant.setOpen('0' + restaurant.getOpen());
            if (restaurant.getClose()!=null && restaurant.getClose().length() < 7) restaurant.setClose('0' + restaurant.getClose());
            LocalTime open = helper.retrieveLocalTime(restaurant.getOpen());
            LocalTime close = helper.retrieveLocalTime(restaurant.getClose());
            if (!queryTime.isBefore(open) && !queryTime.isAfter(close)) {
                for (Deal deal : restaurant.getDeals()) {
                    DealDetails dealDetails = mapper.mapToDealDetails(restaurant,deal);
                    deals.add(dealDetails);
                }
            }
        }
        return deals;
    }
}

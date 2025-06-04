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
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EatClubTimedDealStrategyImpl implements EatClubDealStrategy {

    @Autowired
    public RestaurantDealsRepository restaurantDealsRepository;

    @Autowired
    public Helper helper;

    @Autowired
    public DealDetailsMapper mapper;

    @Override
    public List<DealDetails> computeDeals(String timeOfDay) {

        LocalTime queryTime = helper.retrieveLocalTime(timeOfDay);
        List<DealDetails> deals = new ArrayList<>();

        for (Restaurant restaurant : restaurantDealsRepository.retrieveRestaurantDeals()) {
            for (Deal deal : restaurant.getDeals()) {
                LocalTime dealStart = deal.getStart() != null ? helper.retrieveLocalTime(deal.getStart()) : helper.retrieveLocalTime(restaurant.getOpen());
                LocalTime dealEnd = deal.getEnd() != null ? helper.retrieveLocalTime(deal.getEnd()) : helper.retrieveLocalTime(restaurant.getClose());
                if(dealStart!=null && dealEnd!=null) {
                    if (!queryTime.isBefore(dealStart) && !queryTime.isAfter(dealEnd)) {
                        DealDetails dealDetails = mapper.mapToDealDetails(restaurant, deal);
                        deals.add(dealDetails);
                    }
                }
            }
        }
        return deals;
    }

    public String getType() {
        return Constants.TIME_OF_DAY;
    }

}

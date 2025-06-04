package com.eatclub.mapper;

import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class DealDetailsMapper {
    public DealDetails mapToDealDetails(Restaurant restaurant, Deal deal){
            DealDetails dealDetails = new DealDetails();
            dealDetails.setRestaurantObjectId(restaurant.getObjectId());
            dealDetails.setRestaurantAddress1(restaurant.getAddress1());
            dealDetails.setRestarantSuburb(restaurant.getSuburb());
            dealDetails.setRestaurantOpen(restaurant.getOpen());
            dealDetails.setRestaurantClose(restaurant.getClose());
            dealDetails.setDealObjectId(deal.getObjectId());
            dealDetails.setDiscount(deal.getDiscount());
            dealDetails.setDineIn(deal.getDineIn());
            dealDetails.setLightning(deal.getLightning());
            dealDetails.setQtyLeft(deal.getQtyLeft());

        return dealDetails;
    }
}

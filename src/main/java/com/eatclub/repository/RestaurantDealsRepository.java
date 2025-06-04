package com.eatclub.repository;

import com.eatclub.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RestaurantDealsRepository {
    List<Restaurant> retrieveRestaurantDeals();
}

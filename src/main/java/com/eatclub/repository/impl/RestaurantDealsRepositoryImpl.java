package com.eatclub.repository.impl;


import com.eatclub.model.Restaurant;
import com.eatclub.repository.RestaurantDealsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.List;

@Component
public class RestaurantDealsRepositoryImpl implements RestaurantDealsRepository {

    private static final String DATA_URL = "https://eccdn.com.au/misc/challengedata.json";
    @Override
    public List<Restaurant> retrieveRestaurantDeals() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new URL(DATA_URL));
            JsonNode restaurantsNode = root.get("restaurants");
            List<Restaurant> restaurants = mapper.readValue(
                    restaurantsNode.toString(),
                    new TypeReference<List<Restaurant>>() {}
            );
            return restaurants;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

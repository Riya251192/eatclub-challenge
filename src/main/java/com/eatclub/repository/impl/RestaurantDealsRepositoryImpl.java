package com.eatclub.repository.impl;


import com.eatclub.exception.ApplicationException;
import com.eatclub.exception.ServiceException;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantCollection;
import com.eatclub.repository.RestaurantDealsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class RestaurantDealsRepositoryImpl implements RestaurantDealsRepository {

    private static final String DATA_URL = "https://eccdn.com.au/mic/challengedata.json";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Restaurant> retrieveRestaurantDeals() {
        try {
            ResponseEntity<RestaurantCollection> restaurants = restTemplate.getForEntity(
                    new URI(DATA_URL),
                    RestaurantCollection.class
            );
            if ((restaurants != null && restaurants.getBody() == null
                    || restaurants.getBody().getRestaurants() == null || restaurants.getBody().getRestaurants().isEmpty())
                    || restaurants == null) {
                return Collections.emptyList();
            }
            return restaurants.getBody().getRestaurants();
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
            throw new ServiceException("API Error while Retrieving Restaurant Deals Data.",e);
        }catch (Exception e ){
            log.error(e.getMessage());
            throw new ApplicationException("Application Error while reading Restaurant Deals Data.");

        }
    }
}

package com.eatclub.repository.impl;


import com.eatclub.exception.ApplicationException;
import com.eatclub.exception.ServiceException;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantCollection;
import com.eatclub.repository.RestaurantDealsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
public class RestaurantDealsRepositoryImpl implements RestaurantDealsRepository {


   @Value("${eatclub.data.url}")
   public String url ;

    @Autowired
    public
    RestTemplate restTemplate;

    @Override
    public List<Restaurant> retrieveRestaurantDeals() {
        try {
            ResponseEntity<RestaurantCollection> restaurants = restTemplate.getForEntity(
                    new URI(url),
                    RestaurantCollection.class
            );
            if ((Objects.nonNull(restaurants)  && Objects.isNull(restaurants.getBody())
                    || Objects.isNull(restaurants.getBody().getRestaurants()) || restaurants.getBody().getRestaurants().isEmpty())
                    || Objects.isNull(restaurants)) {
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

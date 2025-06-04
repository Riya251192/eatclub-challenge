package com.eatclub.repository;

import com.eatclub.exception.ApplicationException;
import com.eatclub.exception.ServiceException;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantCollection;
import com.eatclub.repository.impl.RestaurantDealsRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes=RestaurantDealsRepositoryImplTest.class)
class RestaurantDealsRepositoryImplTest {

    private RestaurantDealsRepositoryImpl repository;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        repository = new RestaurantDealsRepositoryImpl();
        repository.restTemplate = restTemplate;
        repository.url = "https://dummyurl.com/deals.json";
    }

    @Test
    void testRetrieveRestaurantDeals_successful() throws Exception {
        Restaurant restaurant = new Restaurant();
        RestaurantCollection collection = new RestaurantCollection();
        collection.setRestaurants(List.of(restaurant));

        when(restTemplate.getForEntity(new URI(repository.url), RestaurantCollection.class))
                .thenReturn(new ResponseEntity<>(collection, HttpStatus.OK));

        List<Restaurant> result = repository.retrieveRestaurantDeals();

        assertEquals(1, result.size());
        assertSame(restaurant, result.get(0));
    }

    @Test
    void testRetrieveRestaurantDeals_emptyResponseBody() throws Exception {
        RestaurantCollection emptyCollection = new RestaurantCollection();
        emptyCollection.setRestaurants(Collections.emptyList());

        when(restTemplate.getForEntity(new URI(repository.url), RestaurantCollection.class))
                .thenReturn(new ResponseEntity<>(emptyCollection, HttpStatus.OK));

        List<Restaurant> result = repository.retrieveRestaurantDeals();

        assertTrue(result.isEmpty());
    }

    @Test
    void testRetrieveRestaurantDeals_nullResponse() throws Exception {
        when(restTemplate.getForEntity(new URI(repository.url), RestaurantCollection.class))
                .thenReturn(null);


        ApplicationException ex = assertThrows(ApplicationException.class, () -> repository.retrieveRestaurantDeals());
        assertTrue(ex.getMessage().contains("Application Error"));
    }

    @Test
    void testRetrieveRestaurantDeals_httpClientErrorException() throws Exception {
        when(restTemplate.getForEntity(new URI(repository.url), RestaurantCollection.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        ServiceException ex = assertThrows(ServiceException.class, () -> repository.retrieveRestaurantDeals());
        assertTrue(ex.getMessage().contains("API Error"));
    }

    @Test
    void testRetrieveRestaurantDeals_genericException() throws Exception {
        when(restTemplate.getForEntity(new URI(repository.url), RestaurantCollection.class))
                .thenThrow(new RuntimeException("Unexpected"));

        ApplicationException ex = assertThrows(ApplicationException.class, () -> repository.retrieveRestaurantDeals());
        assertTrue(ex.getMessage().contains("Application Error"));
    }
}

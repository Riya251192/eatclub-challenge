package com.eatclub.service;

import com.eatclub.mapper.DealDetailsMapper;
import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantCollection;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.service.impl.EatClubTimedDealStrategyImpl;
import com.eatclub.utility.Constants;
import com.eatclub.utility.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EatClubTimedDealStrategyTest.class)
public class EatClubTimedDealStrategyTest {

    @InjectMocks
    private EatClubTimedDealStrategyImpl strategy;
    @Mock
    private RestaurantDealsRepository repository;

    @Mock
    private Helper helper;
    private DealDetailsMapper mapper;

    @BeforeEach
    void setUp() {
        repository = mock(RestaurantDealsRepository.class);
        helper = mock(Helper.class);
        mapper = mock(DealDetailsMapper.class);

        strategy = new EatClubTimedDealStrategyImpl();
        strategy.helper = helper;
        strategy.restaurantDealsRepository = repository;
        strategy.mapper = mapper;
    }

    @Test
    void testComputeDeals_returnsMatchingDeals() {

        String inputTime = "13:00";
        LocalTime queryTime = LocalTime.of(13, 0);
        when(helper.retrieveLocalTime(inputTime)).thenReturn(queryTime);


        Deal deal = mock(Deal.class);
        when(deal.getStart()).thenReturn("12:00");
        when(deal.getEnd()).thenReturn("14:00");
        when(helper.retrieveLocalTime("12:00")).thenReturn(LocalTime.of(12, 0));
        when(helper.retrieveLocalTime("14:00")).thenReturn(LocalTime.of(14, 0));

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getDeals()).thenReturn(List.of(deal));

        when(repository.retrieveRestaurantDeals()).thenReturn(List.of(restaurant));

        DealDetails mappedDeal = new DealDetails();
        when(mapper.mapToDealDetails(restaurant, deal)).thenReturn(mappedDeal);

        List<DealDetails> result = strategy.computeDeals(inputTime);

        assertEquals(1, result.size());
        assertEquals(mappedDeal, result.get(0));
    }

    @Test
    void testComputeDeals_returnsMatchingDeals1() throws IOException {

        String inputTime = "13:00";
        LocalTime queryTime = LocalTime.of(13, 0);
        when(helper.retrieveLocalTime(inputTime)).thenReturn(queryTime);


        Deal deal = mock(Deal.class);

        when(helper.retrieveLocalTime("12:00pm")).thenReturn(LocalTime.of(12, 0));
        when(helper.retrieveLocalTime("2:00pm")).thenReturn(LocalTime.of(14, 0));

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getDeals()).thenReturn(List.of(deal));

        ObjectMapper mapper = new ObjectMapper();
        RestaurantCollection restaurantCollection = mapper.readValue(new FileReader("src/test/resources/sample.json"), RestaurantCollection.class);
        when(repository.retrieveRestaurantDeals()).thenReturn(restaurantCollection.getRestaurants());

        List<DealDetails> result = strategy.computeDeals(inputTime);

        assertEquals(4, result.size());
    }


    @Test
    void testComputeDeals_ignoresOutOfTimeRangeDeals() {
        String inputTime = "09:00";
        LocalTime queryTime = LocalTime.of(9, 0);
        when(helper.retrieveLocalTime(inputTime)).thenReturn(queryTime);

        Deal deal = mock(Deal.class);
        when(deal.getStart()).thenReturn("10:00");
        when(deal.getEnd()).thenReturn("12:00");
        when(helper.retrieveLocalTime("10:00")).thenReturn(LocalTime.of(10, 0));
        when(helper.retrieveLocalTime("12:00")).thenReturn(LocalTime.of(12, 0));

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getDeals()).thenReturn(List.of(deal));

        when(repository.retrieveRestaurantDeals()).thenReturn(List.of(restaurant));

        List<DealDetails> result = strategy.computeDeals(inputTime);

        assertTrue(result.isEmpty());
        verify(mapper, never()).mapToDealDetails(any(), any());
    }

    @Test
    void testGetType_returnsConstant() {
        assertEquals(Constants.TIME_OF_DAY, strategy.getType()); // Adjust if Constants.TIME_OF_DAY = "TIME_OF_DAY"
    }

}

package com.eatclub.service;

import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import com.eatclub.model.RestaurantCollection;
import com.eatclub.repository.RestaurantDealsRepository;
import com.eatclub.repository.impl.RestaurantDealsRepositoryImpl;
import com.eatclub.service.impl.EatClubDealServiceImpl;
import com.eatclub.service.impl.EatClubTimedDealStrategyImpl;
import com.eatclub.utility.Constants;
import com.eatclub.utility.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EatClubDealServiceTest.class)
public class EatClubDealServiceTest {

    private EatClubDealServiceImpl service;
    private EatClubDealContext context;
    private RestaurantDealsRepository repository;
    private Helper helper;

    @BeforeEach
    void setUp() {
        context = mock(EatClubDealContext.class);
        repository = mock(RestaurantDealsRepository.class);
        helper = mock(Helper.class);

        service = new EatClubDealServiceImpl();
        service.eatClubDealContext = context;
        service.restaurantDealsRepository = repository;
        service.helper = helper;
    }

    @Test
    void testComputeDeals_delegatesToContext() {
        List<DealDetails> expected = List.of(new DealDetails());
        when(context.computeDeals("dineIn", "yes")).thenReturn(expected);

        List<DealDetails> actual = service.computeDeals("dineIn", "yes");

        assertEquals(expected, actual);
        verify(context).computeDeals("dineIn", "yes");
    }

    @Test
    void testComputeDealPeakTime_returnsPeakWindow() {
        Deal deal1 = mock(Deal.class);
        when(deal1.getStart()).thenReturn("12:00");
        when(deal1.getEnd()).thenReturn("14:00");

        Deal deal2 = mock(Deal.class);
        when(deal2.getStart()).thenReturn("13:00");
        when(deal2.getEnd()).thenReturn("15:00");

        Restaurant restaurant = mock(Restaurant.class);
        when(restaurant.getDeals()).thenReturn(List.of(deal1, deal2));
        when(repository.retrieveRestaurantDeals()).thenReturn(List.of(restaurant));

        when(helper.retrieveLocalDateTime("12:00")).thenReturn(LocalDateTime.of(2023, 1, 1, 12, 0));
        when(helper.retrieveLocalDateTime("14:00")).thenReturn(LocalDateTime.of(2023, 1, 1, 14, 0));
        when(helper.retrieveLocalDateTime("13:00")).thenReturn(LocalDateTime.of(2023, 1, 1, 13, 0));
        when(helper.retrieveLocalDateTime("15:00")).thenReturn(LocalDateTime.of(2023, 1, 1, 15, 0));

        Map<String, String> result = service.computeDealPeakTime();

        assertEquals("13:00", result.get("peakTimeStart"));
        assertEquals("14:00", result.get("peakTimeEnd"));
    }

    @Test
    void testComputeDealPeakTime_noDeals() {
        when(repository.retrieveRestaurantDeals()).thenReturn(List.of());

        Map<String, String> result = service.computeDealPeakTime();

        assertEquals(Constants.NO_DEALS, result.get("peakTimeStart"));
        assertEquals(Constants.NO_DEALS, result.get("peakTimeEnd"));
    }
}


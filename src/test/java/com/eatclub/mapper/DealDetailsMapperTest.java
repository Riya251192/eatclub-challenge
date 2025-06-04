package com.eatclub.mapper;


import com.eatclub.mapper.DealDetailsMapper;
import com.eatclub.model.Deal;
import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DealDetailsMapperTest.class)
class DealDetailsMapperTest {

    @Test
    void testMapToDealDetails() {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setObjectId("rest-001");
        restaurant.setAddress1("123 Main St");
        restaurant.setSuburb("Downtown");
        restaurant.setOpen("09:00");
        restaurant.setClose("22:00");

        Deal deal = new Deal();
        deal.setObjectId("deal-123");

        DealDetailsMapper mapper = new DealDetailsMapper();

        // Act
        DealDetails result = mapper.mapToDealDetails(restaurant, deal);

        // Assert
        assertEquals("rest-001", result.getRestaurantObjectId());
        assertEquals("123 Main St", result.getRestaurantAddress1());
        assertEquals("Downtown", result.getRestarantSuburb());
        assertEquals("09:00", result.getRestaurantOpen());
        assertEquals("22:00", result.getRestaurantClose());
        assertEquals("deal-123", result.getDealObjectId());
    }
}

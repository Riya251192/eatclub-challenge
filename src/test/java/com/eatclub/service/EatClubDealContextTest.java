package com.eatclub.service;

import com.eatclub.exception.BadRequestException;
import com.eatclub.model.DealDetails;
import com.eatclub.utility.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EatClubDealContextTest.class)
public class EatClubDealContextTest {
    private EatClubDealStrategy mockStrategy;
    private EatClubDealContext context;

    @BeforeEach
    void setUp() {
        mockStrategy = mock(EatClubDealStrategy.class);
        when(mockStrategy.getType()).thenReturn(Constants.TIME_OF_DAY);

        context = new EatClubDealContext(List.of(mockStrategy));
    }

    @Test
    void testComputeDeals_withValidType() {
        List<DealDetails> expectedDeals = List.of(new DealDetails());
        when(mockStrategy.computeDeals(Mockito.anyString())).thenReturn(expectedDeals);

        List<DealDetails> actualDeals = context.computeDeals(Constants.TIME_OF_DAY, "16:00pm");

        assertEquals(expectedDeals, actualDeals);

    }

    @Test
    void testComputeDeals_withInvalidType() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> context.computeDeals("invalidType", "value")
        );

        assertEquals("Unsupported Filter type: invalidType", exception.getMessage());
    }

}

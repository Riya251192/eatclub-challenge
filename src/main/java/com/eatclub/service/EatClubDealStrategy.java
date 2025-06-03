package com.eatclub.service;

import com.eatclub.model.DealDetails;
import com.eatclub.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EatClubDealStrategy {
    List<DealDetails> computeDeals(String timeOfDay);
}

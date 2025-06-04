package com.eatclub.service;

import com.eatclub.model.DealDetails;
import java.util.List;


public interface EatClubDealStrategy {
    List<DealDetails> computeDeals(String timeOfDay);
    String getType();

}

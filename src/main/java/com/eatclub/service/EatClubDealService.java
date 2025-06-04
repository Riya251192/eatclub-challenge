package com.eatclub.service;

import com.eatclub.model.DealDetails;
import java.util.List;
import java.util.Map;

public interface EatClubDealService {
    List<DealDetails> computeDeals(String filter , String value);
    Map<String, String> computeDealPeakTime();
}

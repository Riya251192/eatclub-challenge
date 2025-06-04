package com.eatclub.service;

import com.eatclub.model.DealDetails;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EatClubDealContext {
    private final Map<String, EatClubDealStrategy> strategies;

    public EatClubDealContext(List<EatClubDealStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(EatClubDealStrategy::getType, strategy -> strategy));
    }


    public List<DealDetails> computeDeals(String type, String value) {
        EatClubDealStrategy strategy = strategies.get(type);
        if (strategy != null) {
            return strategy.computeDeals(value);
        } else {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
    }


}


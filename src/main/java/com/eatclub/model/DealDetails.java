package com.eatclub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DealDetails {
    private String restaurantObjectId;
    private String restaurantName;
    private String restaurantAddress1;
    private String restarantSuburb;
    private String restaurantOpen;
    private String restaurantClose;
    private String dealObjectId;
    private String discount;
    private String dineIn;
    private String lightning;
    private String qtyLeft;

}

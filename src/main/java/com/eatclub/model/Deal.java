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
public class Deal {
    private String objectId;
    private String discount;
    private String dineIn;
    private String lightning;
    private String start;
    private String end;
    private String qtyLeft;
}
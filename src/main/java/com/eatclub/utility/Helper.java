package com.eatclub.utility;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
public class Helper {

    public LocalTime retrieveLocalTime(String timeOfDay){
        DateTimeFormatter TIME_FORMATTER =new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter(Locale.US);
        LocalTime queryTime = LocalTime.parse(timeOfDay, new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter(Locale.US));
        return queryTime;
    }

}

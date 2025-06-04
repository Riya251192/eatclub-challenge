package com.eatclub.utility;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
public class Helper {

    public LocalTime retrieveLocalTime(String timeOfDay) {
        if (timeOfDay != null && timeOfDay.length() < 7) timeOfDay = '0' + timeOfDay;
        LocalTime queryTime = LocalTime.parse(timeOfDay, new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter(Locale.US));
        return queryTime;
    }

    public LocalDateTime retrieveLocalDateTime(String timeOfDay) {
        if (timeOfDay != null && timeOfDay.length() < 7) timeOfDay = '0' + timeOfDay;
        LocalDate date = LocalDate.now();

        LocalTime queryTime = LocalTime.parse(timeOfDay, new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter(Locale.US));
        LocalDateTime localDateTime = LocalDateTime.of(date, queryTime);
        return localDateTime;
    }
}

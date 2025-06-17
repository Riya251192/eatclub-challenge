package com.eatclub.utility;

import com.eatclub.exception.BadRequestException;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Objects;

@Component
public class Helper {

    public LocalTime retrieveLocalTime(String timeOfDay) {
        try {
            if (Objects.nonNull(timeOfDay) && timeOfDay.length() < 7) timeOfDay = '0' + timeOfDay;
            LocalTime queryTime = LocalTime.parse(timeOfDay, new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter(Locale.US));
            return queryTime;
        }catch (Exception ex){
            throw new BadRequestException("Format incorrect for time :"+timeOfDay);
        }
    }

    public LocalDateTime retrieveLocalDateTime(String timeOfDay) {
        try {
        if (Objects.nonNull(timeOfDay) && timeOfDay.length() < 7) timeOfDay = '0' + timeOfDay;
        LocalDate date = LocalDate.now();

        LocalTime queryTime = LocalTime.parse(timeOfDay, new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mma").toFormatter());
        LocalDateTime localDateTime = LocalDateTime.of(date, queryTime);
        return localDateTime;
        }catch (Exception ex){
            throw new BadRequestException("Format incorrect for time :"+timeOfDay);
        }
    }
}

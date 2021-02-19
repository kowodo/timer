package com.kwd.timer;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeStringParser {
    public static Duration getDurationFromString(String string){

        Duration retval = Duration.ofDays(0);
        Pattern timeSpec = Pattern.compile("(\\d+)([h|H|m|M|s|S]{1})");
        Matcher matcher = timeSpec.matcher(string);
        while (matcher.find()) {
            int parsedNumber = Integer.parseInt(matcher.group(1));
            switch (matcher.group(2)) {
                case "s", "S" -> retval = retval.plus(Duration.ofSeconds(parsedNumber));
                case "m", "M" -> retval = retval.plus(Duration.ofMinutes(parsedNumber));
                case "h", "H" -> retval = retval.plus(Duration.ofHours(parsedNumber));
            }
        }
        return retval;
    }
}

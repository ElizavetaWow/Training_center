package sample.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtil {
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String format(LocalTime time){
        if (time == null){
            return null;
        }
        return DATE_TIME_FORMATTER.format(time).substring(0, 5);
    }

    public static LocalTime parse(String timeString){
        try {
            if (timeString.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$")){
                timeString = timeString + ":00";
            }
            return DATE_TIME_FORMATTER.parse(timeString, LocalTime::from);
        }
        catch (DateTimeParseException e){
            return null;
        }
    }

    public static boolean validTime(String timeString){
        return TimeUtil.parse(timeString) != null;
    }
}

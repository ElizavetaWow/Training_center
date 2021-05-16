package sample.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_PATTERN_SEC = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER_SEC = DateTimeFormatter.ofPattern(DATE_PATTERN_SEC);

    public static String format(LocalDate date){
        if (date == null){
            return null;
        }
        return DATE_TIME_FORMATTER.format(date);
    }
    public static String format(LocalDate date, boolean form) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER_SEC.format(date);
    }

    public static LocalDate parse(String dateString){
        try {
            return DATE_TIME_FORMATTER_SEC.parse(reformat(dateString), LocalDate::from);
        }
        catch (DateTimeParseException ignored){
        }
        try {
            return DATE_TIME_FORMATTER.parse(reformat(dateString), LocalDate::from);
        }
        catch (DateTimeParseException ignored){
        }
        return null;

    }


    public static String reformat(String dateString){
        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")){
            return dateString.substring(8, 10)+ "." + dateString.substring(5, 7)+"."+dateString.substring(0, 4);
        }
        return dateString;
    }


    public static boolean validDate(String dateString){
        return DateUtil.parse(dateString) != null;
    }
}

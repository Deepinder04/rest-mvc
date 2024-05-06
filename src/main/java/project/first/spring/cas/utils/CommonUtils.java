package project.first.spring.cas.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonUtils {

    public static LocalDate getFormattedLocalDate(String date, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        formatter = formatter.withLocale(Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }

    public static DecimalFormat getDecimalFormatOfPattern(String pattern){
        return new DecimalFormat(pattern);
    }
}

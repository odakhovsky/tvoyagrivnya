package com.tvoyagryvnia.commands.help;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CommandDateParser {

    private static final String today = "today";
    private static final String month = "month";
    private static final String year = "year";

    private static final SimpleDateFormat DF_POINT = new SimpleDateFormat("dd.MM.yyyy");

    public  DateRange parse(String date) {
            if (date.startsWith(today)) {
                return todayRange();
            }else if(date.startsWith(month)) {
                return forMonth();
            }else if(date.startsWith(year)) {
                return forYear();
            }else {
                if (date.contains("-")){
                    String from = date.split("-")[0];
                    String to = date.split("-")[1];
                    return new DateRange(parseDate(from), parseDate(to));
                }else {
                    return new DateRange(parseDate(date), parseDate(date));
                }

            }
    }

    private  DateRange todayRange() {
        return new DateRange();
    }

    private DateRange forMonth() {
        LocalDate ldStart = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).withDayOfMonth(1);
        Instant instant = ldStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date start = Date.from(instant);

        LocalDate ldTo = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).plusMonths(1).withDayOfMonth(1).minusDays(1);
        Instant instantTo = ldTo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date end = Date.from(instantTo);
        return new DateRange(start, end);
    }

    private DateRange forYear() {
        LocalDate ldStart = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).withDayOfYear(1);
        Instant instant = ldStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date start = Date.from(instant);

        LocalDate ldTo = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).plusYears(1).withDayOfYear(1).minusDays(1);
        Instant instantTo = ldTo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date end = Date.from(instantTo);
        return new DateRange(start, end);
    }

    private static Date parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DF_POINT.toPattern());
        DateTime dateTime = formatter.parseDateTime(date);
        return dateTime.toDate();
    }
}

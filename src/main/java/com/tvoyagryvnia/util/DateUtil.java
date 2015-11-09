package com.tvoyagryvnia.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat DF_HYPHEN = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DF_POINT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DF_POINT_REVERSE = new SimpleDateFormat("yyyy.MM.dd");
    public static final SimpleDateFormat DF_HYPHEN_REVERSE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DF_POINT_WITHOUT_DAY = new SimpleDateFormat("MM.yyyy");
    public static final SimpleDateFormat DF_HYPER_WITHOUT_DAY = new SimpleDateFormat("yyyy-MM");


    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static Date parseDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = formatter.parseDateTime(date);
        return dateTime.toDate();
    }

    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date convertToPatternDate(Date date, SimpleDateFormat to) {
        SimpleDateFormat sdf = new SimpleDateFormat(to.toPattern());
        return parseDate(sdf.format(date), to.toPattern());
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"січня", "лютого", "березня", "квітня", "травня", "червня",
                    "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"};
        }

    };

    public static String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", myDateFormatSymbols);
        return dateFormat.format(date);
    }

    public static String getFormattedDate(Date date, SimpleDateFormat pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.toPattern());
        return dateFormat.format(date);
    }

    public static String getDateRange(String date) {
        Date d = parseDate(date, DF_HYPER_WITHOUT_DAY.toPattern());
        Date from = getMonthFirstDate(d.getTime());
        Date to = getMonthLastDate(d.getTime());
        return getFormattedDate(from, DF_POINT) + " - " + getFormattedDate(to, DF_POINT);
    }

    private static Date getMonthFirstDate(Long time) {
        LocalDate ld = LocalDate.ofEpochDay(time / (24 * 60 * 60 * 1000)).withDayOfMonth(1).plusMonths(1);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date fd = Date.from(instant);
        return fd;
    }

    private static Date getMonthLastDate(Long time) {
        LocalDate ld = LocalDate.ofEpochDay(time / (24 * 60 * 60 * 1000)).plusMonths(2).withDayOfMonth(1).minusDays(1);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date fd = Date.from(instant);
        return fd;
    }
}

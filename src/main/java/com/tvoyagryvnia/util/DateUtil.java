package com.tvoyagryvnia.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat DF_HYPHEN = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DF_POINT = new SimpleDateFormat("dd.MM.yyyy");

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
}

package com.kou.prolab;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class UtilClass {
    private static final String APPRAISAL_DATE_FORMAT = "dd/MM/yyyy";
    private static final String UTC_TIME_ZONE_ID = "UTC";
    private static final FastDateFormat appraisalDateFormat = FastDateFormat.getInstance(APPRAISAL_DATE_FORMAT, TimeZone.getTimeZone(UTC_TIME_ZONE_ID));

    public static int yasHesapla(Date date) {
        LocalDate dogumTarihi = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Date currentDate = new Date();

        LocalDate simdikiZaman = Instant.ofEpochMilli(currentDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Period.between(dogumTarihi, simdikiZaman).getYears();
    }

    public static Date parseDateString(String dateStr) {
        try {
            return appraisalDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.invisee.finvest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MuhammadAbrar on 10/14/15.
 */
public class DateUtil {

    public static final String DDMMYYYY = "ddMMyyyy";
    public static final String MMDDYYYY = "MMddyyyy";
    public static final String DD_MMM_YYYY = "dd MMM yyyy";
    public static final String DD__MMM__YYYY = "dd-MMM-yyyy";
    public static final String DD__MMM__YYYY_HH_MM = "dd-MMM-yyyy HH:mm";
    public static final String DD_MM_YYYY = "dd/MM/yyyy";
    public static final String DD_MM_YYYY_ = "dd-MM-yyyy";
    public static final String DD_MM_YYYY_HH_MM = "dd-MM-yyyy'T'HH:mm";
    public static final String HH_MM = "HH:mm";
    public static final String HHMM = "HHmm";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MMMM = "MMMM";
    public static final String INVISEE_RETURN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String INVISEE_RETURN_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(date);
    }

    public static String format(long date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        return simpleDateFormat.format(new Date(date));
    }

    public static Date format(String dateInString, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    

}

package com.sparta.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import static com.sparta.util.Constants.LOGGER;

public class DateFormatter {

    private static final SimpleDateFormat inSDF = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(String inDate) {
        String outDate = "";
        if (inDate != null) {
            try {
                java.util.Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex){
                outDate=null;
                LOGGER.warn("Wrong date format");
            }
        }
        return outDate;
    }

}

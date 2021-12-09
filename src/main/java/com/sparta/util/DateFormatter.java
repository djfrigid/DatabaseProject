package com.sparta.util;

import com.sparta.validate.EmployeeValidate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import static com.sparta.util.Constants.LOGGER;

public class DateFormatter {

    private static final SimpleDateFormat inSDF = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");
    public static java.sql.Date formatDate(String inDate) {

        inDate = EmployeeValidate.validateDateString(inDate);

        String outDate = "";
        if (inDate != null) {
            try {
                java.util.Date date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex){
                return null;
            }
        }
        if(outDate.equals("")){
            return null;
        } else {
            return java.sql.Date.valueOf(outDate);
        }
    }

}

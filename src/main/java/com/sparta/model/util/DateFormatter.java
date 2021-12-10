package com.sparta.model.util;

import com.sparta.model.validate.EmployeeValidate;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatter {

    private static final SimpleDateFormat inSDF = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");
    public static java.sql.Date formatDate(String inDate, boolean dob) {

        // Evaluate input date format
        inDate = EmployeeValidate.validateDateString(inDate);

        java.util.Date date = null;
        String outDate = "";
        if (inDate != null) {
            try {
                // Create out date format
                date = inSDF.parse(inDate);
                outDate = outSDF.format(date);
            } catch (ParseException ex){
                return null;
            }
        }

        // Null if validation or parse failed
        if(outDate.equals("")){
            return null;
        } else {
            // Age and DOJ checks
            Date sqlDate = java.sql.Date.valueOf(outDate);
            if (dob && !(EmployeeValidate.validAge(sqlDate))) {
                return null;
            } else if (!dob && !(EmployeeValidate.validJoin(sqlDate))) {
                return null;
            }
            return java.sql.Date.valueOf(outDate);
        }
    }
}

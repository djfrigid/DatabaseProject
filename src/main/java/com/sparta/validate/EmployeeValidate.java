package com.sparta.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date;

public class EmployeeValidate {
    private static String emailRegex = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@" +
            "[a-zA-Z0-9]+[.][a-zA-Z0-9]{2,3}(?:[.][a-zA-Z0-9]{2,3})?$";
    private static String nameRegex = "^[A-Za-z]{2,}(?:-[A-Za-z]{2,})?$";
    private static String prefixRegex = "^[A-Za-z]{2,4}[.]?$";
    private static String idRegex = "^[0-9]{1,6}";
    private static String salaryRegex = "[0-9]{4,8}";
    private static String dateRegex = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/[0-9][4]$";
    private static String validDateRegex = "^(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9][4]$";
    private static Pattern emailPattern = Pattern.compile(emailRegex);
    private static Pattern namePattern = Pattern.compile(nameRegex);
    private static Pattern idPattern = Pattern.compile(idRegex);
    private static Pattern salaryPattern = Pattern.compile(salaryRegex);
    private static Pattern datePattern = Pattern.compile(dateRegex);
    private static Pattern validateDatePattern = Pattern.compile(validDateRegex);
    private static String[] namePrefixes = {"mr.", "mrs.", "miss.", "ms.", "dr.", "drs.", "hon.", "prof."};
    public static final int MILLISECONDSINDAY = 86400000;
    public static final double DAYSINYEAR = 365.2425;

    // Method to check string satisfies regular expression
    private static boolean matches(Pattern pattern, String employeeDetail) {
        Matcher matcher = pattern.matcher(employeeDetail);
        return matcher.matches();
    }

    public static String validateEmail(String email) {
        // Only valid emails are returned
        if (matches(emailPattern, email)) {
            return email;
        }
        return null;
    }

    public static char validateGender(char gender) {
        // Valid genders are returned
        if (gender == 'M' || gender == 'F') {
            return gender;
        } else if (gender == 'm') {
            return 'M';
        } else if (gender == 'f') {
            return 'F';
        }
        return '\0';
    }

    public static char validateInitial(char initial) {
        if (Character.isAlphabetic(initial)) {
            return Character.toUpperCase(initial);
        }

        return '\0';
    }

    public static String validateName(String name) {
        // Check name is in correct format
        if (matches(namePattern, name)) {
            // Ensure correct capitalisation is returned
            StringBuilder formattedName = new StringBuilder();
            if (name.contains("-")) {
                // Format hyphened name
                String[] names = name.split("-");
                for (int i = 0; i < names.length; i++) {
                    formattedName.append(names[i].substring(0,1).toUpperCase() + names[i].substring(1).toLowerCase());
                    if (i != names.length -1) {
                        formattedName.append("-");
                    }
                }
            } else {
                // Format single name
                formattedName.append(name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase());
            }
            return formattedName.toString();
        }
        return null;
    }

    public static String validateNamePrefix(String prefix) {
        String returnPrefix = null;
        StringBuilder prefixFormat = new StringBuilder(prefix.toLowerCase());
        if (prefix.charAt(prefix.length() - 1) != '.') {
            prefixFormat.append(".");
        }

        if (Arrays.asList(namePrefixes).contains(prefixFormat.toString())) {
            returnPrefix = prefixFormat.substring(0,1).toUpperCase() + prefixFormat.substring(1);
        }

        return returnPrefix;
    }

    public static String validateId(String idNumber) {
        if (matches(idPattern, idNumber)) {
            return idNumber;
        }
        return null;
    }

    public static String validateSalary(String salary) {
        if (matches(salaryPattern, salary)) {
            return salary;
        }
        return null;
    }

    /*
    TODO - Validate DOB and DOJ
    - Take sql.Date inputs, or strings
    - Make sure DOB is within 80/90/100 years
     */

    public static String validateDateString(String date) {
        if(matches(datePattern, date)) {
            return date;
        }

        String dummy = date.replaceAll("[^[-:;]]", "");
        if(matches(validateDatePattern, dummy)){
            StringBuilder returnDate = new StringBuilder(dummy);
            returnDate.insert(5, '/');
            returnDate.insert(8, '/');
            return returnDate.toString();
        }

        return null;
    }

    public static Date validateAge(Date dob) {
        Date currentDate = new Date(System.currentTimeMillis());
        double yearsDiff = (currentDate.getTime()/MILLISECONDSINDAY - dob.getTime()/MILLISECONDSINDAY)/DAYSINYEAR;

        if (yearsDiff >= 18) {
            return dob;
        }

        return null;
    }

}

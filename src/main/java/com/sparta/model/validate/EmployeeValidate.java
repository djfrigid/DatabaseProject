package com.sparta.model.validate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date;

import static com.sparta.model.util.Constants.*;

public class EmployeeValidate {

    // Method to check string satisfies regular expression, applied across methods
    private static boolean matches(Pattern pattern, String employeeDetail) {
        Matcher matcher = pattern.matcher(employeeDetail);
        return matcher.matches();
    }

    // Email validation
    public static String validateEmail(String email) {
        // Only valid emails are returned
        if (matches(EMAIL_PATTERN, email)) {
            return email;
        }
        return INVALID_STR;
    }

    // Gender validation
    public static char validateGender(char gender) {
        if (Character.toUpperCase(gender) == M ||
        Character.toUpperCase(gender) == F) {
            return Character.toUpperCase(gender);
        }

        return INVALID_CHAR;
    }

    // Initial validation
    public static char validateInitial(char initial) {
        // Check initial is alphabetical
        if (Character.isAlphabetic(initial)) {
            return Character.toUpperCase(initial);
        }

        return INVALID_CHAR;
    }

    // Name (first and last) validation
    public static String validateName(String name) {
        // Check name is in correct format
        if (matches(NAME_PATTERN, name)) {
            // Ensure correct capitalisation is returned
            StringBuilder formattedName = new StringBuilder();
            if (name.contains("-")) {
                // Format hyphened name
                String[] names = name.split("-");
                for (int i = 0; i < names.length; i++) {
                    formattedName.append(names[i].substring(0, 1).toUpperCase()).append(names[i].substring(1).toLowerCase());
                    if (i != names.length -1) {
                        formattedName.append("-");
                    }
                }
            } else {
                // Format single name
                formattedName.append(name.substring(0, 1).toUpperCase()).append(name.substring(1).toLowerCase());
            }
            return formattedName.toString();
        }
        return INVALID_STR;
    }

    // Prefix validation
    public static String validateNamePrefix(String prefix) {
        // Check prefix ends with '.'
        StringBuilder prefixFormat = new StringBuilder(prefix.toLowerCase());
        if (prefix.charAt(prefix.length() - 1) != '.') {
            prefixFormat.append(".");
        }

        // Check prefix exists in know values
        if (Arrays.asList(NAME_PREFIXES).contains(prefixFormat.toString())) {
            return prefixFormat.substring(0,1).toUpperCase() + prefixFormat.substring(1);
        }

        return INVALID_STR;
    }

    // ID validation
    public static String validateId(String idNumber) {
        // Check ID matches regex
        if (matches(ID_PATTERN, idNumber)) {
            return idNumber;
        }
        return INVALID_NUM;
    }

    // Salary validation
    public static String validateSalary(String salary) {
        // Check salary matches regex
        if (matches(SALARY_PATTERN, salary)) {
            return salary;
        }
        return INVALID_NUM;
    }

    // Date string validation (executed within ./util/DateFormatter)
    public static String validateDateString(String date) {
        // Check datStr matches regex
        if(matches(DATE_PATTERN, date)) {
            return date;
        }

        // Format dateStr if valid date, in incorrect format
        String dummy = date.replaceAll("[-:;]", "");
        if(matches(VALIDATE_DATE_PATTERN, dummy)){
            StringBuilder returnDate = new StringBuilder(dummy);
            returnDate.insert(2, '/');
            returnDate.insert(5, '/');
            return returnDate.toString();
        }
        LOGGER.info("Validation Failed. Failed date: " + date);
        // Handled by dateFormatter
        return null;
    }

    // Return years between two dates
    private static double yearsDiff(Date firstDate, Date secondDate) {
        return ((float) (secondDate.getTime()/MILLISECONDS_IN_DAY - firstDate.getTime()/MILLISECONDS_IN_DAY))/DAYS_IN_YEAR;
    }

    // Validate employee age (18 or over)
    public static boolean validAge(Date dob) {
        Date currentDate = new Date(System.currentTimeMillis());
        double age = yearsDiff(dob, currentDate);
        return age >= EIGHTEEN;
    }

    // Validate DOJ not in the future
    public static boolean validJoin(Date doj) {
        Date currentDate = new Date(System.currentTimeMillis());
        double dojCurrentDiff = yearsDiff(doj, currentDate);

        return dojCurrentDiff >= 0;
    }
}
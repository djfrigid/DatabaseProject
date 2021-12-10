package com.sparta.model.validate;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date;

//CONSTANTS
import static com.sparta.model.util.Constants.MILLISECONDS_IN_DAY;
import static com.sparta.model.util.Constants.DAYS_IN_YEAR;
import static com.sparta.model.util.Constants.EIGHTEEN;
import static com.sparta.model.util.Constants.M;
import static com.sparta.model.util.Constants.F;

import static com.sparta.model.util.Constants.INVALID_STR;
import static com.sparta.model.util.Constants.INVALID_CHAR;
import static com.sparta.model.util.Constants.INVALID_NUM;

import static com.sparta.model.util.Constants.NAME_PREFIXES;

import static com.sparta.model.util.Constants.EMAIL_PATTERN;
import static com.sparta.model.util.Constants.NAME_PATTERN;
import static com.sparta.model.util.Constants.ID_PATTERN;
import static com.sparta.model.util.Constants.SALARY_PATTERN;
import static com.sparta.model.util.Constants.DATE_PATTERN;
import static com.sparta.model.util.Constants.VALIDATE_DATE_PATTERN;

public class EmployeeValidate {
    // ----- CONSTANTS -----
    // Numbers
    public static final int MILLISECONDSINDAY = 86400000;
    public static final double DAYSINYEAR = 365.2425;
    // Invalid returns
    public static final String INVALIDSTR = "INVALID";
    public static final char INVALIDCHAR = '\0';
    public static final String INVALIDNUM = "-1";
    // Regular Expressions
    private static final String emailRegex = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@" +
            "[a-zA-Z0-9]+[.][a-zA-Z0-9]{2,3}(?:[.][a-zA-Z0-9]{2,3})?$";
    private static final String nameRegex = "^[A-Za-z]{2,}(?:-[A-Za-z]{2,})?$";
    private static final String idRegex = "^[0-9]{1,6}";
    private static final String salaryRegex = "[0-9]{4,8}";
    private static final String dateRegex = "^([0][1-9]|[1][0-2])[\\/]([0-2][0-9]|[3][0-1])[\\/]([1][8-9][0-9]{2}|[2-3][0-9]{3})$";
    private static final String validDateRegex = "^([0][1-9]|[1][0-2])([0-2][0-9]|[3][0-1])([1][8-9][0-9]{2}|2[0-9]{3})$";
    // Prefix library
    private static final  String[] namePrefixes = {"mr.", "mrs.", "miss.", "ms.", "dr.", "drs.", "hon.", "prof."};
    // Patterns
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    private static final Pattern namePattern = Pattern.compile(nameRegex);
    private static final Pattern idPattern = Pattern.compile(idRegex);
    private static final Pattern salaryPattern = Pattern.compile(salaryRegex);
    private static final Pattern datePattern = Pattern.compile(dateRegex);
    private static final Pattern validateDatePattern = Pattern.compile(validDateRegex);


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

        // Handled by dateFormatter
        return null;
    }

    // TODO - HAVE THIS IMPLEMENTED IN APP

    private static double yearsDiff(Date firstDate, Date secondDate) {
        double yearsDiff = (secondDate.getTime()/MILLISECONDSINDAY - firstDate.getTime()/MILLISECONDSINDAY)/DAYSINYEAR;
        return yearsDiff;
    }

    // Validate employee age (18 or over)
    public static boolean validAge(Date dob) {
        Date currentDate = new Date(System.currentTimeMillis());
        double yearsDiff = (currentDate.getTime()/MILLISECONDS_IN_DAY - dob.getTime()/MILLISECONDS_IN_DAY)/DAYS_IN_YEAR;

        if (yearsDiff >= EIGHTEEN) {
            return dob;
        double age = yearsDiff(dob, currentDate);

        if (age >= 18) {
            return true;
        }

        return false;
    }

    // Validate DOJ not in the future
    public static boolean validJoin(Date doj) {
        Date currentDate = new Date(System.currentTimeMillis());
        double dojCurrentDiff = yearsDiff(doj, currentDate);

        if (dojCurrentDiff >= 0) {
            return true;
        }
        return false;
    }
}
package com.sparta.model.validate;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Date;

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
    private static final String dateRegex = "^([0][1-9]|[1][0-2])[\\/]([0-2][0-9]|[3][0-1])[\\/]([1][8-9][0-9]{2}|2[0-9]{3})$";
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


    // Method to check string satisfies regular expression, applied across methods
    private static boolean matches(Pattern pattern, String employeeDetail) {
        Matcher matcher = pattern.matcher(employeeDetail);
        return matcher.matches();
    }

    // Email validation
    public static String validateEmail(String email) {
        // Only valid emails are returned
        if (matches(emailPattern, email)) {
            return email;
        }
        return INVALIDSTR;
    }

    // Gender validation
    public static char validateGender(char gender) {
        if (Character.toUpperCase(gender) == 'M' ||
        Character.toUpperCase(gender) == 'F') {
            return Character.toUpperCase(gender);
        }

        return INVALIDCHAR;
    }

    // Initial validation
    public static char validateInitial(char initial) {
        // Check initial is alphabetical
        if (Character.isAlphabetic(initial)) {
            return Character.toUpperCase(initial);
        }

        return INVALIDCHAR;
    }

    // Name (first and last) validation
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
        return INVALIDSTR;
    }

    // Prefix validation
    public static String validateNamePrefix(String prefix) {
        // Check prefix ends with '.'
        StringBuilder prefixFormat = new StringBuilder(prefix.toLowerCase());
        if (prefix.charAt(prefix.length() - 1) != '.') {
            prefixFormat.append(".");
        }

        // Check prefix exists in know values
        if (Arrays.asList(namePrefixes).contains(prefixFormat.toString())) {
            return prefixFormat.substring(0,1).toUpperCase() + prefixFormat.substring(1);
        }

        return INVALIDSTR;
    }

    // ID validation
    public static String validateId(String idNumber) {
        // Check ID matches regex
        if (matches(idPattern, idNumber)) {
            return idNumber;
        }
        return INVALIDNUM;
    }

    // Salary validation
    public static String validateSalary(String salary) {
        // Check salary matches regex
        if (matches(salaryPattern, salary)) {
            return salary;
        }
        return INVALIDNUM;
    }

    // Date string validation (executed within ./util/DateFormatter)
    public static String validateDateString(String date) {
        // Check datStr matches regex
        if(matches(datePattern, date)) {
            return date;
        }

        // Format dateStr if valid date, in incorrect format
        String dummy = date.replaceAll("[-:;]", "");
        if(matches(validateDatePattern, dummy)){
            StringBuilder returnDate = new StringBuilder(dummy);
            returnDate.insert(2, '/');
            returnDate.insert(5, '/');
            return returnDate.toString();
        }

        // Handled by dateFormatter
        return null;
    }

    // TODO - HAVE THIS IMPLEMENTED IN APP
    // Validate employee age (18 or over)
    public static Date validateAge(Date dob) {
        Date currentDate = new Date(System.currentTimeMillis());
        double yearsDiff = (currentDate.getTime()/MILLISECONDSINDAY - dob.getTime()/MILLISECONDSINDAY)/DAYSINYEAR;

        if (yearsDiff >= 18) {
            return dob;
        }

        return null;
    }
}
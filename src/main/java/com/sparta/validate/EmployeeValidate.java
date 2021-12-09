package com.sparta.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeValidate {
    private static String emailRegex = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@" +
            "[a-zA-Z0-9]+[.][a-zA-Z0-9]{2,3}(?:[.][a-zA-Z0-9]{2,3})?$";
    private static String nameRegex = "^[A-Za-z]{2,}(?:-[A-Za-z]{2,})?$";
    private static String prefixRegex = "^[A-Za-z]{2,4}[.]?$";
    private static String idRegex = "^[0-9]{1,6}";
    private static String salaryRegex = "[0-9]{4,8}";
    private static Pattern emailPattern = Pattern.compile(emailRegex);
    private static Pattern namePattern = Pattern.compile(nameRegex);
    private static Pattern prefixPattern = Pattern.compile(prefixRegex);
    private static Pattern idPattern = Pattern.compile(idRegex);
    private static Pattern salaryPattern = Pattern.compile(salaryRegex);

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

    public static String validateNamePrefix(String title) {
        if (matches(prefixPattern, title)) {
            StringBuilder formattedTitle = new StringBuilder();
            formattedTitle.append(title.substring(0,1).toUpperCase() + title.substring(1).toLowerCase());
            if (formattedTitle.charAt(formattedTitle.length() - 1) != '.') {
                formattedTitle.append(".");
            }
            return formattedTitle.toString();
        }
        return null;
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
}

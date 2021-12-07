package com.sparta.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeValidate {
    private static String emailRegex = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@[a-zA-Z0-9]+[.]{1}[a-zA-Z0-9]{2,3}(?:[.][a-zA-Z0-9]{2,3})?$";
    private static String nameRegex = "^[A-Za-z]{2,}(?:-[A-Za-z]{2,})?$";
    private static Pattern emailPattern = Pattern.compile(emailRegex);
    private static Pattern namePattern = Pattern.compile(nameRegex);

    public static String validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.matches()) {
            return email;
        }
        return null;
    }

    public static char validateGender(char gender) {
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
        Matcher matcher = namePattern.matcher(name);
        // Check name is in correct format
        if (matcher.matches()) {
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
}

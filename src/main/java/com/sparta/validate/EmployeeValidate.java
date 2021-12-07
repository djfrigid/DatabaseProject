package com.sparta.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeValidate {
    private static String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static String nameRegex = "^[A-Z][a-z]+(?:-[A-Z][a-z]+)?$";
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
        if (matcher.matches()) {
            return name;
        }
        return null;
    }
}

package com.sparta.model.util;

import com.sparta.model.dbaccess.EmployeeDao;
import com.sparta.model.dbaccess.EmployeeDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class Constants {
    public static final Logger LOGGER = LogManager.getLogger("Data handling app");
    public static final EmployeeDao EMPLOYEE_DAO = new EmployeeDaoImpl();
    // ----- CONSTANTS -----
    // Numbers
    public static final int MILLISECONDS_IN_DAY = 86400000;
    public static final double DAYS_IN_YEAR = 365.2425;
    public static final int EIGHTEEN = 18;
    public static final char M = 'M';
    public static final char F = 'F';
    // Invalid returns
    public static final String INVALID_STR = "INVALID";
    public static final char INVALID_CHAR = '\0';
    public static final String INVALID_NUM = "-1";
    // Regular Expressions
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@" +
            "[a-zA-Z0-9]+[.][a-zA-Z0-9]{2,3}(?:[.][a-zA-Z0-9]{2,3})?$";
    public static final String NAME_REGEX = "^[A-Za-z]{2,}(?:-[A-Za-z]{2,})?$";
    public static final String ID_REGEX = "^[0-9]{1,6}";
    public static final String SALARY_REGEX = "[0-9]{4,8}";
    public static final String DATE_REGEX = "^([0][1-9]|[1][0-2])[\\/]([0-2][0-9]|[3][0-1])[\\/]([1][8-9][0-9]{2}|[2-3][0-9]{3})$";
    public static final String VALID_DATE_REGEX = "^([0][1-9]|[1][0-2])([0-2][0-9]|[3][0-1])([1][8-9][0-9]{2}|2[0-9]{3})$";
    // Prefix library
    public static final  String[] NAME_PREFIXES = {"mr.", "mrs.", "miss.", "ms.", "dr.", "drs.", "hon.", "prof."};
    // Patterns
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
    public static final Pattern ID_PATTERN = Pattern.compile(ID_REGEX);
    public static final Pattern SALARY_PATTERN = Pattern.compile(SALARY_REGEX);
    public static final Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);
    public static final Pattern VALIDATE_DATE_PATTERN = Pattern.compile(VALID_DATE_REGEX);
}

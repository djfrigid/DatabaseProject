package com.sparta.example.test.validation;

import com.sparta.model.util.DateFormatter;
import com.sparta.model.validate.EmployeeValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTests {

    //Genders
    @ParameterizedTest
    @ValueSource(chars = {'M', 'F', 'f', 'm'})
    @DisplayName("returns the gender if the gender is valid.")
    public void validGenderTest(char input) {
        char expectedOutput = Character.toUpperCase(input);
        char result = EmployeeValidate.validateGender(input);
        assertEquals(expectedOutput, result);
    }

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'd'})
    @DisplayName("returns null terminator value if the gender is invalid.")
    public void invalidGenderTest(char input) {
        char expectedOutput = '\0';
        char result = EmployeeValidate.validateGender(input);
        assertEquals(expectedOutput, result);
    }

    //Initials
    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'D', 'f', 'g', 'h', 'e', 'f'})
    @DisplayName("returns the initial if the initial is valid.")
    public void validInitialTest(char input) {
        char expectedResult = Character.toUpperCase(input);
        char result = EmployeeValidate.validateInitial(input);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @ValueSource(chars = {'0', '-', '!', '?'})
    @DisplayName("returns null terminator value if the initial is invalid.")
    public void invalidInitialTest(char input) {
        char expectedOutput = '\0';
        char result = EmployeeValidate.validateInitial(input);
        assertEquals(expectedOutput, result);
    }

    //Emails
    @ParameterizedTest
    @ValueSource(strings = {"hellohello@gmail.com", "spartaglobal@spartaglobal.net", "george@facebook.co.in", "marklikestocode@canada.ca"})
    @DisplayName("returns the email if the email is valid.")
    public void validEmailTest(String input) {
        String result = EmployeeValidate.validateEmail(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hellohello", "spartaglobal@spartaglobal", "gmail.com"})
    @DisplayName("returns null if the email is invalid.")
    public void invalidEmailTest(String input) {
        String result = EmployeeValidate.validateEmail(input);
        assertEquals("INVALID", result);
    }

    //Names
    @ParameterizedTest
    @CsvSource({"Talal, Talal", "George-Jenkins, George-Jenkins", "tALAL, Talal", "gEoRgE, George", "kOnrAd-jEnkIns, Konrad-Jenkins"})
    @DisplayName("returns the name if the name is valid.")
    public void validNameTest(String input, String expectedOutput) {
        String result = EmployeeValidate.validateName(input);
        assertEquals(expectedOutput, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Talal34", "0George?!", "-Ria", "M4rk9881", "K0nr4d", "Mark-"})
    @DisplayName("returns null if the name is invalid. (checks for case sensitive)")
    public void invalidNameTest(String input) {
        String result = EmployeeValidate.validateName(input);
        assertEquals("INVALID", result);
    }

    //Prefix
    @ParameterizedTest
    @CsvSource({"Mrs. , Mrs.", "ms, Ms.", "Ms., Ms.", "dr, Dr.", "Dr., Dr.", "Prof., Prof.", "Hon., Hon.", "prof., Prof."})
    @DisplayName("returns the prefix if the prefix is valid.")
    public void validPrefixTest(String input, String expectedOutput) {
        String result = EmployeeValidate.validateNamePrefix(input);
        assertEquals(expectedOutput, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aklsjfdakl", "000", "???"})
    @DisplayName("returns null if the prefix is invalid.")
    public void invalidPrefixTest(String input) {
        String result = EmployeeValidate.validateNamePrefix(input);
        assertEquals("INVALID", result);
    }

    //Salary
    @ParameterizedTest
    @ValueSource(strings = {"2000", "20000", "200000", "9999999", "99999999"})
    @DisplayName("returns salary if the salary is valid.")
    public void validSalaryTest(String input) {
        String result = EmployeeValidate.validateSalary(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"SALARY", "?????", "293820938023", "1", "1w", "22", "323"})
    @DisplayName("returns null if the salary is invalid")
    public void invalidSalaryTest(String input) {
        String result = EmployeeValidate.validateSalary(input);
        assertEquals("-1", result);
    }

    //ID
    @ParameterizedTest
    @ValueSource(strings = {"000001", "999999", "1", "22", "333", "4444", "555555"})
    @DisplayName("returns id if the id is valid.")
    public void validIDTest(String input) {
        String result = EmployeeValidate.validateId(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"?????", "1000111", "1w", "99999w"})
    @DisplayName("returns null if the ID is invalid")
    public void invalidIDTest(String input) {
        String result = EmployeeValidate.validateId(input);
        assertEquals("-1", result);
    }

    //DATE FORMATTER
    // D.O.B
    @ParameterizedTest
    @CsvSource({"10/12/1999, 1999-10-12", "05/25/2000, 2000-05-25"})
    @DisplayName("returns the date in the correct SQL format if valid")
    public void dobFormatterTest(String input, String expectedOutput) {
        Date result = DateFormatter.formatDate(input, true);
        Date expectedDate = java.sql.Date.valueOf((expectedOutput));
        assertTrue(result.compareTo(expectedDate) == 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10/10/2015", "10/01/2025",  "01/01/3000"})
    @DisplayName("returns null if date format invalid")
    public void invalidDobFormatterTest(String input) {
        Date result = DateFormatter.formatDate(input, true);
        assertNull(result);
    }

    @DisplayName("Check 18 today is valid DOB")
    @Test
    public void eighteenTodayDobFormatterTest(){
        LocalDate today = LocalDate.now();
        String inputDob = today.getMonthValue() + "/" + today.getDayOfMonth() + "/" + (today.getYear() - 18);
        String outputDob = (today.getYear() - 18) + "-" + today.getMonthValue() + "-" + today.getDayOfMonth();
        Date expectedDate = java.sql.Date.valueOf(outputDob);
        Date result = DateFormatter.formatDate(inputDob, true);
        assertTrue(result.compareTo(expectedDate) == 0);
    }

    @DisplayName("Check 18 tomorrow is invalid DOB")
    @Test
    public void eighteenTomorrowDobFormatterTest(){
        LocalDate today = LocalDate.now();
        String inputDob = today.getMonthValue() + "/" + (today.getDayOfMonth() + 1) + "/" + (today.getYear() - 18);
        Date result = DateFormatter.formatDate(inputDob, true);
        assertNull(result);
    }

    //D.O.J
    @ParameterizedTest
    @CsvSource({"10/12/2015, 2015-10-12", "05/25/2020, 2020-05-25"})
    @DisplayName("returns the date in the correct SQL format if valid")
    public void dojFormatterTest(String input, String expectedOutput) {
        Date result = DateFormatter.formatDate(input, false);
        Date expectedDate = java.sql.Date.valueOf((expectedOutput));
        assertTrue(result.compareTo(expectedDate) == 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"13/10/1999", "10/01/2025",  "01/01/3000"})
    @DisplayName("returns null if date format invalid")
    public void invalidDojFormatterTest(String input) {
        Date result = DateFormatter.formatDate(input, false);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/12/1999, 10/12/1999", "10-12-1999, 10/12/1999", "10:12:1999, 10/12/1999"})
    @DisplayName("returns the correct format date if possible")
    public void dateStringFormatterTest(String input, String expectedOutput) {
        String result = EmployeeValidate.validateDateString(input);
        assertEquals(expectedOutput, result);
    }
}

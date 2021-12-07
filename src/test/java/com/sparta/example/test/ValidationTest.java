package com.sparta.example.test;

import com.sparta.validate.EmployeeValidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ValidationTest {

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
        assertNull(result);
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
        assertNull(result);
    }
}
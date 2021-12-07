package com.sparta.example.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationTest {

    @BeforeEach
    public void setUp() {
        EmployeeValidate eV = new EmployeeValidate();
    }

    //Genders
    @ParameterizedTest
    @ValueSource(chars = {'M', 'F', 'f', 'm'})
    @DisplayName("returns the gender if the gender is valid.")
    public void validGenderTest(char input) {
        char result = eV.validateGender(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'd'})
    @DisplayName("returns null terminator value if the gender is invalid.")
    public void invalidGenderTest(char input) {
        char expectedOutput = '\0';
        char result = eV.validateGender(input);
        assertEquals(expectedOutput, result);
    }
    //Initials
    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'D', 'f', 'g', 'h', 'e', 'f'})
    @DisplayName("returns the initial if the initial is valid.")
    public void validInitialTest(char input) {
        char result = eV.validateInitial(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(chars = {'0', '-', '!', '?'})
    @DisplayName("returns null terminator value if the initial is invalid.")
    public void invalidInitialTest(char input) {
        char expectedOutput = '\0';
        char result = eV.validateInitial(input);
        assertEquals(expectedOutput, result);
    }
    //Emails
    @ParameterizedTest
    @ValueSource(strings = {"hellohello@gmail.com", "spartaglobal@spartaglobal.com"})
    @DisplayName("returns the email if the email is valid.")
    public void validEmailTest(String input) {
        String result = eV.validateEmail(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"hellohello", "spartaglobal@spartaglobal"})
    @DisplayName("returns null if the email is invalid.")
    public void invalidEmailTest(String input) {
        String result = eV.validateEmail(input);
        assertEquals(null, result);
    }
    //Names
    @ParameterizedTest
    @ValueSource(strings = {"Talal", "George", "Ria", "Mark", "Konrad"})
    @DisplayName("returns the name if the name is valid.")
    public void validNameTest(String input) {
        String result = eV.validateName(input);
        assertEquals(input, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Talal34", "0George?!", "-Ria@", "M4rk9881", "K0nr4d"})
    @DisplayName("returns null if the name is invalid.")
    public void invalidNameTest(String input) {
        String result = eV.validateName(input);
        assertEquals(null, result);
    }
}
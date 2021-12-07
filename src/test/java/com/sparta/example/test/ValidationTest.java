package com.sparta.example.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

    @BeforeEach
    public void setUp() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"M", "F"})
    public void genderValidation(String gender) {
        assertTrue(validateGender(gender));
    }

    @Test
    public void validationTestTwo() {
    }

    @Test
    public void validationTestThree() {
    }
}
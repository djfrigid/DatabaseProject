package com.sparta.example.test.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;


public class DateFormatterTests {
    @DisplayName("Check valid date input")
    @Test
    @CsvSource({"10/12/1999, 1999-10-12", "05/25/2000, 2000-05-25"})
    public void checkValidDate(Date inputDate, Date outputDate) {

    }
}

package com.adebisi.student_management;


import com.adebisi.student_management.service.algorithm_database.SumStringUsingRecussion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumStringUsingRecussionTest {

    @Test
    void testValidNumericString() {
        String input = "123456";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(21, result, "The sum of digits in '123456' should be 21");
    }

    @Test
    void testStringWithNonNumericCharacters() {
        String input = "AB123ZZ45";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(15, result, "The sum of digits in 'AB123ZZ45' should be 15");
    }

    @Test
    void testStringWithSpecialCharacters() {
        String input = "@!#123$%";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(6, result, "The sum of digits in '@!#123$%' should be 6");
    }

    @Test
    void testEmptyString() {
        String input = "";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(0, result, "The sum of digits in an empty string should be 0");
    }

    @Test
    void testStringWithOnlyNonNumericCharacters() {
        String input = "ABCDE";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(0, result, "The sum of digits in 'ABCDE' should be 0");
    }

    @Test
    void testStringWithAllZeros() {
        String input = "000000";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(0, result, "The sum of digits in '000000' should be 0");
    }

    @Test
    void testStringWithMixedCharactersAndLargeDigits() {
        String input = "AB123ZZ45.,/123";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(21, result, "The sum of digits in 'AB123ZZ45.,/123' should be 21");
    }

    @Test
    void testLongString() {
        String input = "123456789012345678901234567890";
        int result = SumStringUsingRecussion.sumOfDigits(input, 0);
        assertEquals(135, result, "The sum of digits in '123456789012345678901234567890' should be 135");
    }
}

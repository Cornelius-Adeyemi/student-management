package com.adebisi.student_management;


import com.adebisi.student_management.service.algorithm_database.ConvertTime;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeConversionTest {

   @Test
    void testValidTimes() {
        assertEquals("one o' clock \n", captureOutput(() -> ConvertTime.convertTime(1, 0)));
        assertEquals("quarter past three\n", captureOutput(() -> ConvertTime.convertTime(3, 15)));
        assertEquals("half past two\n", captureOutput(() -> ConvertTime.convertTime(2, 30)));
        assertEquals("twenty nine minutes past four\n", captureOutput(() -> ConvertTime.convertTime(4, 29)));
        assertEquals("quarter to six\n", captureOutput(() -> ConvertTime.convertTime(5, 45)));
        assertEquals("ten minutes to seven\n", captureOutput(() -> ConvertTime.convertTime(6, 50)));
    }

    @Test
    void testEdgeCases() {
        assertEquals("twelve o' clock \n", captureOutput(() -> ConvertTime.convertTime(12, 0)));
        assertEquals("quarter to one\n", captureOutput(() -> ConvertTime.convertTime(12, 45)));
    }

    @Test
    void testInvalidTimes() {
        Exception exception1 = assertThrows(RuntimeException.class, () -> ConvertTime.convertTime(0, 10));
        assertEquals("Time values should be greater than 0", exception1.getMessage());

        Exception exception2 = assertThrows(RuntimeException.class, () -> ConvertTime.convertTime(5, -5));
        assertEquals("Time values should be greater than 0", exception2.getMessage());
    }

    // Helper method to capture output from System.out
    private String captureOutput(Runnable task) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        try {
            task.run();
        } finally {
            System.setOut(originalOut);
        }
        return outputStream.toString();
    }
}

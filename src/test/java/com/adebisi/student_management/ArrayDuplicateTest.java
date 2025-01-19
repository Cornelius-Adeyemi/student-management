package com.adebisi.student_management;



import com.adebisi.student_management.service.algorithm_database.ArrayDuplicate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ArrayDuplicateTest {

    @Test
    void testRegularCase() {
        int[][] input = {
                {1, 3, 1, 2, 3, 4, 4, 3, 5},
                {1, 1, 1, 1, 1, 1, 1}
        };
        int[][] expected = {
                {1, 3, 0, 2, 0, 4, 0, 0, 5},
                {1, 0, 0, 0, 0, 0, 0}
        };
        assertArrayEquals(expected, ArrayDuplicate.removeDuplicate(input));
    }

    @Test
    void testEmptyArray() {
        int[][] input = {};
        int[][] expected = {};
        assertArrayEquals(expected, ArrayDuplicate.removeDuplicate(input));
    }

    @Test
    void testSingleRow() {
        int[][] input = {{1, 2, 3, 4, 5}};
        int[][] expected = {{1, 2, 3, 4, 5}};
        assertArrayEquals(expected, ArrayDuplicate.removeDuplicate(input));
    }

    @Test
    void testSingleElementDuplicates() {
        int[][] input = {{1, 1, 1, 1, 1}};
        int[][] expected = {{1, 0, 0, 0, 0}};
        assertArrayEquals(expected, ArrayDuplicate.removeDuplicate(input));
    }

    @Test
    void testLargeValues() {
        int[][] input = {
                {500000, 500000, 400000, 500000},
                {300000, 300000, 300000}
        };
        int[][] expected = {
                {500000, 0, 400000, 0},
                {300000, 0, 0}
        };
        assertArrayEquals(expected, ArrayDuplicate.removeDuplicate(input));
    }
}

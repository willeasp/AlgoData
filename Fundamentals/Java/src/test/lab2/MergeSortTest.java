package test.lab2;

import main.lab2.MergeSort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    MergeSort mergeSort;

    @BeforeEach
    void setUp() {
        mergeSort = new MergeSort();
    }

    @AfterEach
    void tearDown() {
        mergeSort = null;
    }

    @Test
    void testSort() {
    }

    void testMerge() {

    }
}
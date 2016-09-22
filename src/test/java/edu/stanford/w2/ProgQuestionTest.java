package edu.stanford.w2;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static edu.stanford.common.CommonUtils.file2intArr;
import static junit.framework.Assert.assertEquals;

public class ProgQuestionTest {

    private static final Path ARRAY_FILE_PATH = Paths.get("src/test/resources/QuickSort.txt");

    private Integer[] arr;

    @Before
    public void setUp() throws IOException {
        arr = file2intArr(ARRAY_FILE_PATH);
    }

    @Test
    public void q1() {
        QuickSort<Integer> test = new QuickSort<>(arr -> 0);
        test.sort(arr);
        assertEquals(162085, test.getComparisons());
    }

    @Test
    public void q2() {
        QuickSort<Integer> test = new QuickSort<>(arr -> arr.length - 1);
        test.sort(arr);
        assertEquals(164123, test.getComparisons());
    }
}

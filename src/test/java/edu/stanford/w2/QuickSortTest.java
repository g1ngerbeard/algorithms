package edu.stanford.w2;

import org.junit.Test;

import java.util.stream.LongStream;

import static edu.stanford.common.Benchmark.runBenchmark;
import static java.util.stream.IntStream.generate;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertArrayEquals;

public class QuickSortTest {

    private Integer[] ARR = new Integer[]{1, 3, 2, 2, 10, 0, 6, 2};

    private Integer[] SORTED_ARR = new Integer[]{0, 1, 2, 2, 2, 3, 6, 10};

    @Test
    public void testCorrectness() {
        assertArrayEquals(QuickSort.quickSort(ARR), SORTED_ARR);
    }

    @Test
    public void testAverageSort() {
        double average = LongStream
                .generate(this::sortDuration)
                .limit(1000)
                .average()
                .getAsDouble();

        System.out.println("Average: " + average);
    }

    private long sortDuration() {
        Integer[] arr = generate(() -> nextInt(0, 10000))
                .limit(10000)
                .mapToObj(Integer::new)
                .toArray(Integer[]::new);

        return runBenchmark(() -> QuickSort.quickSort(arr)).getDuration();
    }
}

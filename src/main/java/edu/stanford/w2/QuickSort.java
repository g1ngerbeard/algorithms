package edu.stanford.w2;

import java.util.function.Function;

import static edu.stanford.common.CommonUtils.copyArray;
import static edu.stanford.w2.PartitionUtils.partition;
import static java.util.Arrays.copyOfRange;
import static org.apache.commons.lang3.ArrayUtils.add;
import static org.apache.commons.lang3.ArrayUtils.addAll;

public class QuickSort<T extends Comparable<T>> {

    private long comparisons;

    private final Function<T[], Integer> pivotFunc;

    public QuickSort() {
        this.pivotFunc = PartitionUtils::randomPivot;
    }

    public QuickSort(Function<T[], Integer> pivotFunc) {
        this.pivotFunc = pivotFunc;
    }

    public T[] sort(T[] arr) {
        if (arr.length < 2) return arr;
        comparisons += arr.length - 1;

        int pivot = pivotFunc.apply(arr);
        int pivotPos = partition(arr, pivot);

        T[] left = add(sort(copyOfRange(arr, 0, pivotPos)), arr[pivotPos]);
        T[] right = sort(copyOfRange(arr, pivotPos + 1, arr.length));
        return addAll(left, right);
    }

    public long getComparisons() {
        return comparisons;
    }

    public static <T extends Comparable<T>> T[] quickSort(T[] arr) {
        return new QuickSort<T>().sort(copyArray(arr));
    }
}


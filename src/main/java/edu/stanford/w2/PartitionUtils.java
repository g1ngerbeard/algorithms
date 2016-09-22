package edu.stanford.w2;

import org.apache.commons.lang3.RandomUtils;

import static edu.stanford.common.CommonUtils.swap;

public class PartitionUtils {

    public static int randomPivot(Object[] arr) {
        return RandomUtils.nextInt(0, arr.length);
    }

    public static <T extends Comparable<T>> int partition(T[] arr, int pivot) {
        swap(arr, 0, pivot);

        int i = 1;

        for (int j = 1; j < arr.length; j++) {
            if (arr[0].compareTo(arr[j]) > 0) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, 0, i - 1);
        return i - 1;
    }
}

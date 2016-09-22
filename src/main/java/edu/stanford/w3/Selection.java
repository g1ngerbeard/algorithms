package edu.stanford.w3;

import edu.stanford.w2.PartitionUtils;

import static edu.stanford.w2.PartitionUtils.partition;
import static java.util.Arrays.copyOfRange;

//TODO: handle duplicates
public class Selection<T extends Comparable<T>> {
    public T select(final T[] arr, final int orderStat) {
        if (arr.length == 1) return arr[0];

        final int pivot = PartitionUtils.randomPivot(arr);
        final int pivotPos = partition(arr, pivot);

        if (pivotPos == orderStat) {
            return arr[pivotPos];
        } else if (pivotPos > orderStat) {
            T[] left = copyOfRange(arr, 0, pivotPos);
            return select(left, orderStat);
        } else {
            T[] right = copyOfRange(arr, pivotPos, arr.length);
            return select(right, orderStat - pivotPos);
        }
    }
}

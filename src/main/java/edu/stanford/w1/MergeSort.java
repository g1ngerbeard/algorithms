package edu.stanford.w1;

import java.lang.reflect.Array;

import static edu.stanford.common.CommonUtils.addAll;
import static java.util.Arrays.copyOfRange;

public class MergeSort<T extends Comparable<T>> {

    private T[] sort(T[] array) {
        int len = array.length;

        if (len < 2) {
            return array;
        }
        final int mid = len / 2;

        return merge(
                sort(copyOfRange(array, 0, mid)),
                sort(copyOfRange(array, mid, len))
        );
    }

    private T[] merge(T[] left, T[] right) {
        int mLen = left.length + right.length;
        @SuppressWarnings("unchecked")
        T[] merged = (T[]) Array.newInstance(left.getClass().getComponentType(), mLen);

        for (int k = 0, i = 0, j = 0; k < mLen; k++) {
            if (i >= left.length) {
                addAll(merged, right, k, j);
                break;
            } else if (j >= right.length) {
                addAll(merged, left, k, i);
                break;
            }

            if (left[i].compareTo(right[j]) < 0) {
                merged[k] = left[i];
                i++;
            } else {
                merged[k] = right[j];
                j++;
            }
        }
        return merged;
    }

    public static <T extends Comparable<T>> T[] mergeSort(T[] array) {
        return new MergeSort<T>().sort(array);
    }

}

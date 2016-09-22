package edu.stanford.w1;

import java.lang.reflect.Array;

import static edu.stanford.common.CommonUtils.addAll;
import static edu.stanford.w1.InversionsCounter.ResultTuple.result;
import static java.util.Arrays.copyOfRange;

public class InversionsCounter<T extends Comparable<T>> {

    protected ResultTuple<T> countAndSort(T[] array) {
        int len = array.length;

        if (len < 2) {
            return result(0, array);
        }

        final int mid = len / 2;

        ResultTuple<T> lRes = countAndSort(copyOfRange(array, 0, mid));
        ResultTuple<T> rRes = countAndSort(copyOfRange(array, mid, len));
        ResultTuple<T> mRes = mergeAndCount(lRes.array, rRes.array);

        return result(lRes.count + rRes.count + mRes.count,
                mRes.array);
    }

    private ResultTuple<T> mergeAndCount(T[] left, T[] right) {
        long count = 0;

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
                count += left.length - i;
                j++;
            }
        }

        return result(count, merged);
    }

    public static <E extends Comparable<E>> long countInversions(E[] array) {
        return new InversionsCounter<E>().countAndSort(array).count;
    }

    static class ResultTuple<T> {
        private final long count;

        private final T[] array;

        public ResultTuple(long count, T[] array) {
            this.count = count;
            this.array = array;
        }

        public static <T> ResultTuple<T> result(long count, T[] array) {
            return new ResultTuple<>(count, array);
        }
    }
}

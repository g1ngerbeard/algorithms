package edu.stanford.w5;

import org.junit.Assert;
import org.junit.Test;

import static edu.stanford.common.CommonUtils.list;
import static edu.stanford.common.CommonUtils.pair;

public class HeapTest {

    @Test
    public void testHeap() {
        ArrayHeap<Integer, String> heap = new ArrayHeap<>();

        heap.addAll(list(
                pair(10, "ten"),
                pair(1, "one"),
                pair(1, "one"),
                pair(2, "two"),
                pair(4, "four")
        ));

        Assert.assertEquals(heap.extractMinValue(), "one");
        Assert.assertEquals(heap.extractMinValue(), "one");
        Assert.assertEquals(heap.extractMinValue(), "two");
        Assert.assertEquals(heap.extractMinValue(), "four");
        Assert.assertEquals(heap.extractMinValue(), "ten");
        Assert.assertEquals(heap.extractMinValue(), null);
    }
}

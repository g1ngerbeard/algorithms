package edu.stanford.w3;

import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

public class SelectionTest {
    private Integer[] ARR = new Integer[]{1, 3, 2, 10, 0, 6};

    @Test
    public void testSelection() {
        Integer select = new Selection<Integer>().select(ARR, 4);
        assertEquals(select, valueOf(6));
    }

}

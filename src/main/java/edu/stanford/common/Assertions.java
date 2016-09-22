package edu.stanford.common;

import java.util.Collection;

public class Assertions {

    public static void assertSize(int expectedSize, Collection collection) {
        assertSize(expectedSize, collection, "Collection's size doesn't match expected one");
    }

    public static void assertSizeGt(int expectedSize, Collection collection) {
        assertSizeGt(expectedSize, collection, "Collection size should be greater than " + expectedSize);
    }

    public static void assertSizeGt(int expectedSize, Collection collection, String msg) {
        assertTrue(collection.size() > expectedSize, msg);
    }

    public static void assertSizeLt(int expectedSize, Collection collection) {
        assertSizeLt(expectedSize, collection, "Collection size should be less than " + expectedSize);
    }

    public static void assertSizeLt(int expectedSize, Collection collection, String msg) {
        assertTrue(collection.size() < expectedSize, msg);
    }

    public static void assertSize(int expectedSize, Collection collection, String msg) {
        assertTrue(collection.size() == expectedSize, msg);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, "Expected true condition");
    }

    public static <T> T notNull(T obj) {
        return notNull(obj, "Passed obj is null");
    }

    public static <T> T notNull(T obj, String msg) {
        assertTrue(obj != null, msg);
        return obj;
    }

}

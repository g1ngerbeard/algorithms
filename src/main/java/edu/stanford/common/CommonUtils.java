package edu.stanford.common;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static edu.stanford.common.Assertions.assertTrue;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CommonUtils {

    public static <T> T[] fillArray(T[] array, Supplier<T> supplier) {
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    public static int[] fillArray(int[] array, Supplier<Integer> supplier) {
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
        return array;
    }

    public static <K, V> Pair<K, V> pair(K key, V value) {
        return Pair.of(key, value);
    }

    public static <K, V, R> R withPair(Pair<K, V> pair, BiFunction<K, V, R> producer) {
        return producer.apply(pair.getLeft(), pair.getRight());
    }

    public static <K, V> void withPairDo(Pair<K, V> pair, BiConsumer<K, V> supplier) {
        supplier.accept(pair.getLeft(), pair.getRight());
    }

    public static <T> void addAll(T[] to, T[] from, int iTo, int iFrom) {
        for (int i = iTo, j = iFrom; i < to.length && j < from.length; i++, j++) {
            to[i] = from[j];
        }
    }

    public static <T> T[] copyArray(T[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static float[] copyArray(float[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static double[] copyArray(double[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static char[] copyArray(char[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static long[] copyArray(long[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static short[] copyArray(short[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static byte[] copyArray(byte[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static <T> T[][] copyMatrix(T[][] matrix) {
        T[][] copy = copyArray(matrix);

        for (int i = 0; i < matrix.length; i++) {
            copy[i] = copyArray(matrix[i]);
        }

        return copy;
    }

    public static <T> T[] swap(T[] arr, int leftIndex, int rightIndex) {
        T buf = arr[leftIndex];
        arr[leftIndex] = arr[rightIndex];
        arr[rightIndex] = buf;
        return arr;
    }

    public static int[] swap(int[] arr, int leftIndex, int rightIndex) {
        int buf = arr[leftIndex];
        arr[leftIndex] = arr[rightIndex];
        arr[rightIndex] = buf;
        return arr;
    }

    public static Stream<String> resourse2stream(String resourseName) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourseName).toURI());
            return Files.lines(path);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Unable to parse matrix", e);
        }
    }

    public static Integer[] file2intArr(Path path2file) throws IOException {
        return Files.lines(path2file)
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }

    @SafeVarargs
    public static <T> List<T> list(T... elems) {
        return Arrays.stream(elems).filter(Objects::nonNull).collect(toList());
    }

    public static <T> List<T> list(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    @SafeVarargs
    public static <T> Set<T> set(T... elems) {
        return Arrays.stream(elems).collect(toSet());
    }

    public static <T> Set<T> set(Set<T> set) {
        return new HashSet<>(set);
    }

    public static boolean isSingleton(Collection collection) {
        return collection.size() == 1;
    }

    @SafeVarargs
    public static <T> List<T> concat(T elem, List<T>... lists) {
        return flatten(list(elem), flatten(lists));
    }

    @SafeVarargs
    public static <T> List<T> flatten(List<T>... lists) {
        return stream(lists).flatMap(Collection::stream).collect(toList());
    }

    public static <T> int checkMatrixRowLength(T[][] matrix) {
        int len = matrix[0].length;

        for (T[] row : matrix) {
            assertTrue(row.length == len, "Matrix can't have rows of different length");
        }

        return len;
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(list(list));
    }

    public static <T> Set<T> immutableSet(Set<T> set) {
        return Collections.unmodifiableSet(set(set));
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new HashMap<>(map));
    }

}

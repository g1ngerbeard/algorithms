package edu.stanford.w5;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.function.Function;

public interface Heap<K extends Comparable<K>, V> {

    void add(K key, V value);

    void addAll(Collection<Pair<K, V>> pairs);

    void addAll(Function<V, K> keySupplier, Collection<V> values);

    V delete(K key);

    V extractMin();

}

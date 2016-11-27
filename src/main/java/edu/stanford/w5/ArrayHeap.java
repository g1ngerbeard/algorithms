package edu.stanford.w5;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static edu.stanford.common.CommonUtils.list;
import static edu.stanford.common.CommonUtils.pair;

public class ArrayHeap<K extends Comparable<K>, V> implements Heap<K, V> {

    private final LinkedList<Pair<K, V>> nodes = new LinkedList<>();

    @Override
    public void add(K key, V value) {
        int last = nodes.size();
        nodes.addLast(pair(key, value));
        bubbleUp(last);
    }

    private void bubbleUp(int childIdx) {
        Pair<K, V> child = nodes.get(childIdx);
        int parentIdx = parent(childIdx);
        Pair<K, V> parent = nodes.get(parentIdx);

        if (child.getKey().compareTo(parent.getKey()) < 0) {
            swap(childIdx, parentIdx);
            bubbleUp(parentIdx);
        }
    }

    @Override
    public Pair<K, V> extractMin() {
        Pair<K, V> node = nodes.pollFirst();
        if (node != null) {
            nodes.addFirst(nodes.pollLast());
            bubbleDown(0);
            return node;
        } else {
            return null;
        }

    }

    @Override
    public V extractMinValue() {
        return extractMin().getValue();
    }

    private void bubbleDown(int i) {
        for (Integer child : orderedChildren(i)) {
            if (isFirstKeyLess(child, i)) {
                swap(child, i);
                bubbleDown(child);
                return;
            }
        }
    }

    private List<Integer> orderedChildren(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left > nodes.size() - 1) {
            return list();
        } else if (right > nodes.size() - 1) {
            return list(left);
        }

        return isFirstKeyLess(left, right) ? list(left, right) : list(right, left);
    }

    @Override
    public void addAll(Collection<Pair<K, V>> pairs) {
        pairs.forEach(pair -> add(pair.getKey(), pair.getValue()));
    }

    @Override
    public void addAll(Function<V, K> keySupplier, Collection<V> values) {
        values.forEach(v -> add(keySupplier.apply(v), v));
    }

    @Override
    public V delete(K key) {
        int delIndex = -1;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getKey().equals(key)) {
                delIndex = i;
                break;
            }
        }

        if (delIndex >= 0) {
            Pair<K, V> node = nodes.get(delIndex);
            swap(delIndex, nodes.size() - 1);
            bubbleDown(delIndex);
            return node.getValue();
        } else {
            return null;
        }
    }

    private int parent(int i) {
        return (int) Math.floor(i / 2);
    }

    private void swap(int left, int right) {
        Collections.swap(nodes, left, right);
    }

    private boolean isFirstKeyLess(int first, int second) {
        return nodes.get(first).getKey().compareTo(nodes.get(second).getKey()) < 0;
    }

    @Override
    public String toString() {
        return "ArrayHeap{" +
                "nodes=" + nodes +
                '}';
    }
}

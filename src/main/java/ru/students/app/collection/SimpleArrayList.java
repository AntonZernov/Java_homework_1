package ru.students.app.collection;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Упрощенная реализация динамического массива.
 */
public class SimpleArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data = new Object[DEFAULT_CAPACITY];
    private int size;

    public void add(T element) {
        ensureCapacity(size + 1);
        data[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) data[index];
    }

    public T remove(int index) {
        T old = get(index);
        int movedCount = size - index - 1;
        if (movedCount > 0) {
            System.arraycopy(data, index + 1, data, index, movedCount);
        }
        data[--size] = null;
        return old;
    }

    public int size() {
        return size;
    }

    public Stream<T> stream() {
        Stream.Builder<T> builder = Stream.builder();
        for (int i = 0; i < size; i++) {
            builder.add(get(i));
        }
        return builder.build();
    }

    private void ensureCapacity(int capacity) {
        if (capacity > data.length) {
            int newCapacity = Math.max(data.length * 2, capacity);
            data = Arrays.copyOf(data, newCapacity);
        }
    }
}

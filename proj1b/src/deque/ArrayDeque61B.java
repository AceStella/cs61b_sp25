package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    // INVARIANTS:
    // 1. The 'size' of the deque is the number of items it contains.
    // 2. The 'capacity' is the length of the 'items' array.
    // 3. 'nextFirst' is the index where the next item to be added at the front will go.
    // 4. 'nextLast' is the index where the next item to be added at the back will go.
    // 5. The pointers 'nextFirst' and 'nextLast' always stay within the range [0, capacity - 1].
    // 6. The first logical element of the deque is at index (nextFirst + 1) % capacity.
    private int nextFirst;
    private int nextLast;
    private int capacity;

    private static final int INITIAL_CAPACITY = 8;
    private static final double RESIZE_FACTOR = 2.0;
    private static final double USAGE_RATIO_THRESHOLD = 0.25;

    public ArrayDeque61B() {
        capacity = INITIAL_CAPACITY;
        items = (T[]) new Object[capacity];
        size = 0;
        // A common starting state for an empty circular array.
        // nextFirst points to the "end" and nextLast points to the "start".
        nextFirst = capacity - 1;
        nextLast = 0;
    }

    /** Helper to calculate the new index with wraparound. */
    private int minusOne(int index) {
        return Math.floorMod(index - 1, capacity);
    }

    private int plusOne(int index) {
        return Math.floorMod(index + 1, capacity);
    }

    /** Resizes the underlying array to the given new capacity. */
    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        int current = plusOne(nextFirst); // The physical index of the first logical element

        // Copy elements from the old array to the new array in order.
        for (int i = 0; i < size; i++) {
            newItems[i] = items[current];
            current = plusOne(current);
        }

        items = newItems;
        capacity = newCapacity;

        // Reset pointers for the new, non-wrapped array configuration.
        // Elements are now at [0, 1, ..., size - 1].
        nextFirst = capacity - 1; // Next addFirst will go at the end of the new array.
        nextLast = size;          // Next addLast will go right after the last element.
    }

    @Override
    public void addFirst(T x) {
        // Resize if the array is full.
        if (size == capacity) {
            resize((int) (capacity * RESIZE_FACTOR));
        }

        // The 'nextFirst' pointer is pre-calculated to be the correct empty slot.
        items[nextFirst] = x;
        // Move the pointer for the *next* addFirst operation.
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T x) {
        // Resize if the array is full.
        if (size == capacity) {
            resize((int) (capacity * RESIZE_FACTOR));
        }

        // The 'nextLast' pointer is pre-calculated to be the correct empty slot.
        items[nextLast] = x;
        // Move the pointer for the *next* addLast operation.
        nextLast = plusOne(nextLast);
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        // Check for downsizing *before* removal.
        if (capacity >= INITIAL_CAPACITY * 2 && (double) size / capacity < USAGE_RATIO_THRESHOLD) {
            resize(capacity / 2);
        }

        // The first logical item is one position after 'nextFirst'.
        int firstItemIndex = plusOne(nextFirst);
        T itemToRemove = items[firstItemIndex];
        items[firstItemIndex] = null; // Help with garbage collection.
        // The now-empty slot becomes the new 'nextFirst'.
        nextFirst = firstItemIndex;
        size -= 1;

        return itemToRemove;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        // Check for downsizing *before* removal.
        if (capacity >= INITIAL_CAPACITY * 2 && (double) size / capacity < USAGE_RATIO_THRESHOLD) {
            resize(capacity / 2);
        }

        // The last logical item is one position before 'nextLast'.
        int lastItemIndex = minusOne(nextLast);
        T itemToRemove = items[lastItemIndex];
        items[lastItemIndex] = null; // Help with garbage collection.
        // The now-empty slot becomes the new 'nextLast'.
        nextLast = lastItemIndex;
        size -= 1;

        return itemToRemove;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int current = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            returnList.add(items[current]);
            current = plusOne(current);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        // Calculate the physical index: start from the first element and offset by 'index'.
        int physicalIndex = Math.floorMod(plusOne(nextFirst) + index, capacity);
        return items[physicalIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int logicalIndex;

        public ArrayDequeIterator() {
            logicalIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return logicalIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element.");
            }
            T returnItem = get(logicalIndex);
            logicalIndex += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // Use instanceof for better type checking, it handles null implicitly.
        if (!(obj instanceof Deque61B<?> o)) {
            return false;
        }
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(o.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        // Reusing toList is a clean and simple way to implement toString.
        // It's less error-prone than manual string building.
        return toList().toString().replace('[', '{').replace(']', '}');
    }
}

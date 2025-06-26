package deque;

import net.sf.saxon.om.Item;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;

    private static final int INITIAL_CAPACITY = 8;

    public ArrayDeque61B() {
        capacity = INITIAL_CAPACITY;
        items = (T[]) new Object[capacity];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        addHelper(x, true);
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        addHelper(x, false);
    }

    public void addHelper(T x, boolean isAddFirst) {
        if(size == capacity) {
            items = resizingUp();
        }
        size += 1;
        if(isAddFirst){
            items[nextFirst] = x;
            nextFirst = Math.floorMod(nextFirst - 1, capacity);
        } else {
            items[nextLast] = x;
            nextLast = Math.floorMod(nextLast + 1, capacity);
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            returnList.addLast(items[Math.floorMod(nextFirst + i, capacity)]);
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        return removeHelper(true);
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        return removeHelper(false);
    }

    public T removeHelper(boolean isRemoveFirst) {
        if (capacity > 15 && size <= capacity * 0.25){
            items = resizingDown();
        }
        int index;
        if (isRemoveFirst) {
            index = Math.floorMod(nextFirst + 1, capacity);
            nextFirst += 1;
        } else {
            index = Math.floorMod(nextLast - 1, capacity);
            nextLast -= 1;
        }
        T item = items[index];
        items[index] = null;
        size -= 1;
        return item;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[Math.floorMod(nextFirst + index + 1, capacity)];
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public T[] resizingUp() {
        return resizingHelper(2);
    }

    public T[] resizingDown() {
        return resizingHelper(0.5);
    }

    public T[] resizingHelper(double x) {
        int newCapacity = (int) (capacity * x);
        T[] newItems = (T[]) new Object[newCapacity];
        int index;
        for(int i = 1; i <= size; i++) {
            index = Math.floorMod(nextFirst + i, capacity);
            newItems[i] = items[index];
        }
        nextFirst = 0;
        nextLast = size + 1;
        capacity = newCapacity;
        return newItems;
    }
}

package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        T maxItem = iterator.next();
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (current.compareTo(maxItem) > 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        T maxItem = iterator.next();
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (comp.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }
}

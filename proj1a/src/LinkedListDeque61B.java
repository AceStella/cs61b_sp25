import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node n = sentinel.next;
        while (!n.equals(sentinel)) {
            returnList.addLast(n.item);
            n = n.next;
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
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node firstNode = sentinel.next;

        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;
        firstNode.prev = null;
        firstNode.next = null;

        size -= 1;
        return firstNode.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        Node lastNode = sentinel.prev;
        lastNode.prev.next = sentinel;
        sentinel.prev = lastNode.prev;
        lastNode.prev = null;
        lastNode.next = null;

        size -= 1;
        return lastNode.item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node n = sentinel;
        for (int i = 0; i <= index; i++) {
            n = n.next;
        }
        return n.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getRecursiveHelper(n.next, index - 1);
    }
}

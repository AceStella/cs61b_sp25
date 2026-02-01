package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Ace
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int size;

    /* Constants */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.size = 0;
        this.buckets = new Collection[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private int hashIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private Node findNode(K key, int index) {
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private void initializeBuckets(int capacity) {
        this.buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int index = hashIndex(key);
        Node node = findNode(key, index);

        if (node != null) {
            node.value = value;
        } else {
            buckets[index].add(new Node(key, value));
            size += 1;
            if (size > loadFactor * buckets.length) {
                resize(buckets.length * 2);
            }
        }
    }

    private void resize(int newCapacity) {
        Collection<Node>[] oldBuckets = this.buckets;

        initializeBuckets(newCapacity);

        this.size = 0;
        for (Collection<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node node = findNode(key, hashIndex(key));
        return (node == null) ? null : node.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return findNode(key, hashIndex(key)) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        initializeBuckets(DEFAULT_INITIAL_CAPACITY);
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                set.add(node.key);
            }
        }
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        int index = hashIndex(key);
        Collection<Node> bucket = buckets[index];

        Iterator<Node> iter = bucket.iterator();

        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key)) {
                V value = node.value;
                iter.remove();
                size -= 1;
                return value;
            }
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}

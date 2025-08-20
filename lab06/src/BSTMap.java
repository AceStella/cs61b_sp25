import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BST root;
    private int size;

    private class BST {
        private K key;
        private V value;
        private BST left;
        private BST right;

        public BST(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BSTMap() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     */
    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            size++;
        }
        root = put(root, key, value);
    }

    /**
     * Private helper method to recursively insert/update a key-value pair.
     */
    private BST put(BST T, K ik, V iv) {
        if (T == null) {
            return new BST(ik, iv);
        }

        int cmp = ik.compareTo(T.key);
        if (cmp < 0) {
            T.left = put(T.left, ik, iv);
        } else if (cmp > 0) {
            T.right = put(T.right, ik, iv);
        } else {
            // Key already exists, so we update the value.
            T.value = iv;
        }
        return T;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        BST node = find(root, key);
        return (node == null) ? null : node.value;
    }

    /**
     * Private helper method to find the node associated with a given key.
     */
    private BST find(BST T, K sk) {
        if (T == null) {
            return null;
        }
        int cmp = sk.compareTo(T.key);
        if (cmp < 0) {
            return find(T.left, sk);
        } else if (cmp > 0) {
            return find(T.right, sk);
        } else {
            return T;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(K key) {
        return find(root, key) != null;
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
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        keySet(root, keys);
        return keys;
    }

    /**
     * Private helper to perform in-order traversal to collect keys.
     */
    private void keySet(BST T, Set<K> keys) {
        if (T != null) {
            keySet(T.left, keys);
            keys.add(T.key);
            keySet(T.right, keys);
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     */
    @Override
    public V remove(K key) {
        V valueToRemove = get(key);
        if (valueToRemove != null) {
            root = remove(root, key);
            size--;
        }
        return valueToRemove;
    }

    /**
     * Private helper method to recursively remove a node.
     */
    private BST remove(BST T, K rk) {
        if (T == null) {
            return null;
        }
        int cmp = rk.compareTo(T.key);
        if (cmp < 0) {
            T.left = remove(T.left, rk);
        } else if (cmp > 0) {
            T.right = remove(T.right, rk);
        } else {
            // Node to remove is T.
            if (T.left == null) {
                return T.right;
            }
            if (T.right == null) {
                return T.left;
            }

            // Node T has two children.
            BST successor = min(T.right);
            T.key = successor.key;
            T.value = successor.value;
            T.right = remove(T.right, successor.key);
        }
        return T;
    }

    /**
     * Private helper to find the minimum node in a subtree.
     */
    private BST min(BST T) {
        if (T.left == null) {
            return T;
        }
        return min(T.left);
    }


    /**
     * Returns an iterator over the keys in this map in ascending order.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public void printInOrder(BST T) {
        if (T != null) {
            printInOrder(T.left);
            System.out.print(T.key + " ");
            printInOrder(T.right);
        }
    }
}

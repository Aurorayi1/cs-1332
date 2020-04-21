import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Your implementation of a LinearProbingHashMap.
 *
 * @author Wenye Yi
 * @version 1.0
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = new LinearProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException(
                    "Key or value is null.");
        } else {
            if (((double) (size + 1)) / (double) table.length
                    > MAX_LOAD_FACTOR) {
                resizeBackingTable((2 * table.length) + 1);
            }
            int hash = Math.abs(key.hashCode() % table.length);
            LinearProbingMapEntry<K, V> entry =
                    new LinearProbingMapEntry<K, V>(key, value);
            int count = 0;
            int del = -1;
            while (hash >= table.length) {
                hash -= table.length;
            }
            while (table[hash] != null && count <= table.length) {
                if (table[hash].isRemoved()) {
                    if (del < 0) {
                        del = hash;
                    }
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                    continue;
                } else if (table[hash].getKey().equals(key)) {
                    V temp = table[hash].getValue();
                    table[hash].setValue(value);
                    return temp;
                } else {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                }
            }
            if (del >= 0) {
                table[del] = entry;
                table[del].setRemoved(false);
                size++;
                return null;
            } else {
                table[hash] = entry;
                size++;
                return null;
            }
        }
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("The key is null.");
        } else {
            int count = 0;
            int hash = Math.abs(key.hashCode() % table.length);
            while (table[hash] != null && count <= table.length) {
                if (!(table[hash].isRemoved())
                        && table[hash].getKey().equals(key)) {
                    table[hash].setRemoved(true);
                    size--;
                    return table[hash].getValue();
                } else {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                }
            }
            throw new java.util.NoSuchElementException(
                    "Cannot find the key in the map.");
        }
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("The key is null.");
        } else {
            int count = 0;
            int hash = Math.abs(key.hashCode()) % table.length;
            while (table[hash] != null && count <= table.length) {
                if (table[hash].isRemoved()) {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                } else if (table[hash].getKey().equals(key)) {
                    return table[hash].getValue();
                } else {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                }
            }
            throw new java.util.NoSuchElementException(
                    "Cannot find the key in the map.");
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("The key is null.");
        } else {
            int count = 0;
            int hash = Math.abs(key.hashCode()) % table.length;
            while (table[hash] != null && count <= table.length) {
                if ((table[hash].isRemoved())) {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                    continue;
                } else if (table[hash].getKey().equals(key)) {
                    return true;
                } else {
                    hash++;
                    count++;
                    if (hash == table.length) {
                        hash -= table.length;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (LinearProbingMapEntry<K, V> entry : table) {
            if (entry != null) {
                if ((!entry.isRemoved())) {
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> set = new ArrayList<V>();
        for (LinearProbingMapEntry<K, V> entry : table) {
            if (entry != null) {
                if ((!entry.isRemoved())) {
                    set.add(entry.getValue());
                }
            }
        }
        return set;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        LinearProbingMapEntry<K, V>[] temp = new LinearProbingMapEntry[length];
        if (length < size) {
            throw new java.lang.IllegalArgumentException(
                    "The length is not valid.");
        }
        for (LinearProbingMapEntry<K, V> entry : table) {
            if (entry != null && !(entry.isRemoved())) {
                int hash = Math.abs(entry.getKey().hashCode() % temp.length);
                if (hash < 0) {
                    hash *= -1;
                }
                while (temp[hash] != null) {
                    hash++;
                    if (hash == temp.length) {
                        hash -= temp.length;
                    }
                }
                temp[hash] = entry;
            }
        }
        table = temp;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        table = new LinearProbingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

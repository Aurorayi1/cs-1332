import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LinearProbingHashMapImanuelTest {
    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        map.put(null, "A");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT)
    public void testPutDuplicateKey() {
        // [(0, A), (1, B), (2, D), (3, E), _, _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertEquals("C", map.put(2, "D"));
        assertNull(map.put(3, "E"));

        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "A");
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        expected[2] = new LinearProbingMapEntry<>(2, "D");
        expected[3] = new LinearProbingMapEntry<>(3, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutResizeAndRehashArray() {
        map = new LinearProbingHashMap<>(5);
        map.put(5, "A"); // Load factor of 0.2
        map.put(6, "B"); // Load factor of 0.4

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[5];
        expected[0] = new LinearProbingMapEntry<>(5, "A");
        expected[1] = new LinearProbingMapEntry<>(6, "B");

        assertEquals(5, map.getTable().length);
        assertArrayEquals(expected, map.getTable());

        map.put(7, "C"); // Load factor of 0.6
        map.put(8, "D"); // Load factor of 0.8, resize and rehash array

        assertEquals(11, map.getTable().length);

        expected = new LinearProbingMapEntry[11];
        expected[5] = new LinearProbingMapEntry<>(5, "A");
        expected[6] = new LinearProbingMapEntry<>(6, "B");
        expected[7] = new LinearProbingMapEntry<>(7, "C");
        expected[8] = new LinearProbingMapEntry<>(8, "D");

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutWithProbing() {
        // [(0, A), (13, B), (26, C), (39, D), _, _, _, _, _, _, _, _, _]
        map.put(0, "A");
        map.put(13, "B");
        map.put(26, "C");
        map.put(39, "D");

        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "A");
        expected[1] = new LinearProbingMapEntry<>(13, "B");
        expected[2] = new LinearProbingMapEntry<>(26, "C");
        expected[3] = new LinearProbingMapEntry<>(39, "D");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutWithDeletedKeys() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(0, A), (1, B)X, (2, C), (3, D)X, (4, E), _, _, _, _, _, _, _, _]
        assertSame("B", map.remove(1));
        assertSame("D", map.remove(3));
        assertEquals(3, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "A");
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        expected[1].setRemoved(true);
        expected[2] = new LinearProbingMapEntry<>(2, "C");
        expected[3] = new LinearProbingMapEntry<>(3, "D");
        expected[3].setRemoved(true);
        expected[4] = new LinearProbingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());

        // [(0, A), (3, G), (2, C), (3, D)X, (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(1, "G"));
        assertEquals(4, map.size());

        expected[1] = new LinearProbingMapEntry<>(1, "G");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPutWithArrayFullOfDeletedKeys() {
        map = new LinearProbingHashMap<>(4);
        // [(0, A), (1, B), _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));

        // [(0, A)X, (1, B)X, _, _]
        assertEquals("A", map.remove(0));
        assertEquals("B", map.remove(1));

        // [(0, A)X, (1, B)X, (2, C), (3, D)]
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));

        // [(0, A)X, (1, B)X, (2, C)X, (3, D)X]
        assertEquals("C", map.remove(2));
        assertEquals("D", map.remove(3));

        assertEquals(0, map.size());
        assertEquals(4, map.getTable().length); // Making sure the array was not resized

        // [(4, E), (1, B)X, (2, C)X, (3, D)X]
        assertNull(map.put(4, "E"));
        assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[4];
        expected[0] = new LinearProbingMapEntry<>(4, "E");
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        expected[1].setRemoved(true);
        expected[2] = new LinearProbingMapEntry<>(2, "C");
        expected[2].setRemoved(true);
        expected[3] = new LinearProbingMapEntry<>(3, "D");
        expected[3].setRemoved(true);

        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveKeyNotInMap() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));

        map.remove(3);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveDeletedKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(0, A), (1, B), (2, C), (3, D)X, (4, E), _, _, _, _, _, _, _, _]
        assertSame("D", map.remove(3));
        assertEquals(4, map.size());

        map.remove(3);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetKeyNotInMap() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));

        map.get(3);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsKeyNullKey() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void testKeySetWithDeletedKeys() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(0, A), (1, B)X, (2, C), (3, D)X, (4, E), _, _, _, _, _, _, _, _]
        assertSame("B", map.remove(1));
        assertSame("D", map.remove(3));
        assertEquals(3, map.size());

        Set<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(2);
        expected.add(4);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValuesListWithDeletedKeys() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        // [(0, A), (1, B)X, (2, C), (3, D)X, (4, E), _, _, _, _, _, _, _, _]
        assertSame("B", map.remove(1));
        assertSame("D", map.remove(3));
        assertEquals(3, map.size());

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("C");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeBackingArrayInvalidSize() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        map.resizeBackingTable(4);
    }
}
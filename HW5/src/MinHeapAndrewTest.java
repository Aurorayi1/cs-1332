import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;

public class MinHeapAndrewTest {
    private static final int TIMEOUT = 200;
    private MinHeap<Integer> minHeap;

    @Before
    public void setup() {
        minHeap = new MinHeap<>();
    }

    //test thrown exception from building with null ArrayList
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArrayList() {
        minHeap = new MinHeap<>(null);
    }

    //test thrown exception from building with ArrayList with null element
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArrayElement() {
        ArrayList<Integer> aList = new ArrayList<Integer>(3);
        aList.add(1);
        aList.add(null);
        aList.add(2);
        minHeap = new MinHeap<Integer>(aList);
    }

    //test thrown exception from adding null data
    @Test(expected = IllegalArgumentException.class)
    public void testAddIllegalElement() {
        minHeap.add(null);
    }

    //test thrown exception from removing from empty heap
    @Test(expected = NoSuchElementException.class)
    public void testIllegalRemoveFromEmpty() {
        ArrayList<Integer> aList = new ArrayList<Integer>(2);
        aList.add(1);
        aList.add(2);
        minHeap = new MinHeap<Integer>(aList);
        minHeap.remove();
        minHeap.remove(); //after this the heap is empty
        minHeap.remove();
    }


    //test thrown exception from getting min of empty heap
    @Test(expected = NoSuchElementException.class)
    public void testIllegalGetMin() {
        ArrayList<Integer> aList = new ArrayList<Integer>(0);
        minHeap = new MinHeap<Integer>(aList);
        minHeap.getMin();
    }

    //test adding the min
    @Test (timeout = TIMEOUT)
    public void testAddMin() {
        ArrayList<Integer> aList = new ArrayList<Integer>(5);
        aList.add(12);
        aList.add(11);
        aList.add(10);
        aList.add(9);
        aList.add(8);
        minHeap = new MinHeap<Integer>(aList);
        minHeap.add(1);

        Integer[] expected = new Integer[11];
        expected[1] = 1;
        expected[2] = 9;
        expected[3] = 8;
        expected[4] = 12;
        expected[5] = 11;
        expected[6] = 10;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    //test adding with resize
    @Test (timeout = TIMEOUT)
    public void testAddWithResize() {
        minHeap = new MinHeap<Integer>(); // minHeap capacity is INITIAL_CAP
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY * 2];
        for (int i = 1; i <= MinHeap.INITIAL_CAPACITY; i++) {
            expected[i] = i;
            minHeap.add(i);
        }
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    //test adding min with resize
    @Test (timeout = TIMEOUT)
    public void testAddMinWithResize() {
        minHeap = new MinHeap<Integer>(); //minHeap capacity is INITIAL_CAP
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY * 2];
        for (int i = 1; i < MinHeap.INITIAL_CAPACITY; i++) {
            expected[i] = i;
            minHeap.add(i);
        }
        minHeap.add(0); //adding the min while causing resize;
        expected[1] = 0;
        expected[3] = 1;
        expected[6] = 3;
        expected[13] = 6;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }


    //test adding to empty array
    @Test (timeout = TIMEOUT)
    public void testAddToEmpty() {
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[1] = 1;
        minHeap.add(1);
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    //test adding to heap with 1 space in backing array
    @Test (timeout = TIMEOUT)
    public void testAddToAlmostFull() {
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        for (int i = 1; i < MinHeap.INITIAL_CAPACITY - 1; i++) {
            expected[i] = i;
            minHeap.add(i);
        } //filling all but last entry checking for improper resize call
        assertArrayEquals(expected, minHeap.getBackingArray());

        ArrayList<Integer> aList = new ArrayList<Integer>(3);
        expected = new Integer[7];
        for (int i = 0; i < 3; i++) {
            aList.add(i);
            expected[i + 1] = i;
        }
        minHeap = new MinHeap<>(aList);
        for (int i = 3; i < 6; i++) {
            minHeap.add(i);
            expected[i + 1] = i;
        }
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    //test removing from heap with one element
    @Test (timeout = TIMEOUT)
    public void testRemoveFromAlmostEmpty() {
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        minHeap.add(1);
        minHeap.remove();
        assertArrayEquals(expected, minHeap.getBackingArray());
    }

    //test removing from full heap
    @Test (timeout = TIMEOUT)
    public void testRemoveFromFull() {
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        for (int i = 1; i < MinHeap.INITIAL_CAPACITY; i++) {
            expected[i] = i;
            minHeap.add(i);
        }
        minHeap.remove();
        expected[1] = 2;
        expected[2] = 4;
        expected[4] = 8;
        expected[8] = 12;
        expected[12] = null;
        assertArrayEquals(expected, minHeap.getBackingArray());
    }
}
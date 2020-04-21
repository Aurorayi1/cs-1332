import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
/*
      NN      NN  OOOOOOOOOO  TTTTTTTTTT  EEEEEEEEEE
      NNNN    NN  OO      OO      TT      EE
      NN  NN  NN  OO      OO      TT      EEEEEE
      NN    NNNN  OO      OO      TT      EE
      NN      NN  OOOOOOOOOO      TT      EEEEEEEEEE

      WE RELY ON YOUR BUILDHEAP FUNCTION TO WORK FOR THE ADD AND REMOVE TESTS.
      WE ALSO HAVE A TESTER FOR BUILDHEAP, SO IF THAT ISNT PASSING AND UR
      GETTING WEIRD ANSWERS IN OTHER METHODS, THAT IS WHY. FIX BUILDHEAP.
 */

public class ApeUnit5 {
    private static int  failures= 0;
    private static int succeded = 0;
    public static final int TIMEOUT = 200;
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }
        @Override
        protected void succeeded(Description description) {
            succeded++;
        }
    };

    //@Test(timeout =TIMEOUT)
    //tests go here
    //
    //@Test(expected = NullPointerException.class)

    private MinHeap<Integer> minHeap;
    private MinHeap<Integer> populatedMin;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> dataList = new ArrayList<>();
        Integer[] dataArr = {0, 3, 1, 5, 4, 2, 6, 8, 9, 15, 11, 7, 10, 21};
        dataList.addAll(Arrays.asList(dataArr));
        minHeap = new MinHeap<>();

        /*
                    0
              3/         \1
           5/   \4     2/    \6
         8/ \9 15/\11 7/\10 21/
         */
        populatedMin = new MinHeap<>(dataList);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void minHeap() {
        minHeap = new MinHeap<Integer>();
        Integer[] arr = {null, null, null, null, null, null, null,
                null, null, null, null, null, null};
        assertArrayEquals(arr, minHeap.getBackingArray());
        assertEquals(0, minHeap.size());
    }

    @Test
    public void testGetMinEmpty() throws Exception {
        try {
            minHeap.getMin();
        } catch (NoSuchElementException e) {
            System.out.println("Yes!! I caught the banana \uD83C\uDF4C");
        } catch (Exception e) {
            System.out.println("AWWW MAN, getMin() is not throwing a NoSuchElementException!!! Instead I got " + e.getMessage());
            throw new Exception();
        }
    }

    @Test
    public void testGetMin() {
        minHeap.add(10);
        minHeap.add(7);
        minHeap.add(8);
        minHeap.add(9);
        minHeap.add(1);
        minHeap.add(3);
        minHeap.add(4);

        assertEquals("Dang it \uD83D\uDE14, it looks like your getMin is not returning the correct value. Maybe you're " +
                        "remove or add is broken, or perhaps you are returning the wrong index",
                new Integer(1),
                minHeap.getMin());
        // new Integer because otherwise it complains about an ambiguous method call
    }

    @Test
    public void testBuildHeapNullList() throws Exception
    {
        try {
            minHeap = new MinHeap<>(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Yes!! I caught the correct banana \uD83C\uDF4C");
        } catch (Exception e) {
            System.out.println("AWWW MAN, your constructor is not throwing an IllegalArgumentException" + " when null is passed in!!! Instead I got " + e.getMessage());
            throw new Exception();
        }
    }

    @Test
    public void testBuildHeapNullData() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(5);
        list.add(10);
        list.add(2);
        list.add(0);
        list.add(null);
        list.add(13);
        list.add(14);
        list.add(null);
        try {
            minHeap = new MinHeap<>(list);
        } catch (IllegalArgumentException e) {
            System.out.println("Yes!! I caught the correct banana \uD83C\uDF4C");
        } catch (Exception e) {
            System.out.println("AWWW MAN, your constructor is not throwing an IllegalArgumentException" +
                    " when null is added to the heap!!! Instead I got " + e.getMessage());
            throw new Exception();
        }
    }

    @Test
    public void testBuildHeapSingleElement() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(13);
        minHeap = new MinHeap<>(list);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(13);
        assertTrue("BUILD \uD83D\uDC4F HEAP \uD83D\uDC4F DID \uD83D\uDC4F NOT \uD83D\uDC4F WORKKKK", verifyHeapArray(expected, minHeap.getBackingArray()));
    }

    @Test
    public void testBuldHeapTwoElements() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(13);
        list.add(87);
        try {
            minHeap = new MinHeap<>(list);
            ArrayList<Integer> expected = new ArrayList<>();
            expected.add(13);
            expected.add(87);
            assertTrue("BUILD \uD83D\uDC4F HEAP \uD83D\uDC4F DID \uD83D\uDC4F NOT \uD83D\uDC4F WORKKKK", verifyHeapArray(expected, minHeap.getBackingArray()));
        } catch (Exception e) {
            System.out.println("Alright, I didn't expect to catch a \uD83C\uDF4C here. " +
                    "I caught this not so pretty looking message \n" + e.getMessage() + "\n" +
                    "You should check out your buildHeap and downHeap methods to fix this \uD83D\uDEE0️");
        }
    }

    @Test
    public void testBuildHeapAlreadyAHeap() {
        Integer[] arr = new Integer[]{1, 4, 17, 5, 7, 20, 21, 6, 9, 10, 12};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));

        try {
            minHeap = new MinHeap<>(list);
            assertTrue("YOUr BUILD HEAP DOESN'T WORK \uD83D\uDE3E", verifyHeapArray(list, minHeap.getBackingArray()));
        } catch (Exception e) {
            System.out.println("Alright, I didn't expect to catch a \uD83C\uDF4C here. " +
                    "I caught this not so pretty looking message \n" + e.getMessage() + "\n" +
                    "You should check out your buildHeap and downHeap methods to fix this \uD83D\uDEE0️");
        }
    }

    @Test
    public void testBuildHeapSortedListAscending() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));

        try {
            minHeap = new MinHeap<>(list);
            assertTrue("YOUr BUILD HEAP DOESN'T WORK \uD83D\uDE3E",
                    verifyHeapArray(list, minHeap.getBackingArray()));
        } catch (Exception e) {
            System.out.println("Alright, I didn't expect to catch a \uD83C\uDF4C here. " +
                    "I caught this not so pretty looking message \n" + e.getMessage() + "\n" +
                    "You should check out your buildHeap and downHeap methods to fix this \uD83D\uDEE0️");
        }
    }

    @Test
    public void testBuildHeapSortedListDescending() {
        Integer[] arr = new Integer[]{8, 7, 6, 5, 4, 3, 2, 1};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));

        try {
            minHeap = new MinHeap<>(list);
            Integer[] earr = new Integer[]{1, 4, 2, 5, 8, 3, 6, 7};
            ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(earr));
            assertTrue("YOUr BUILD HEAP DOESN'T WORK \uD83D\uDE3E",verifyHeapArray(expected, minHeap.getBackingArray()));
        } catch (Exception e) {
            System.out.println("Alright, I didn't expect to catch a \uD83C\uDF4C here. " +
                    "I caught this not so pretty looking message \n" + e.getMessage() + "\n" +
                    "You should check out your buildHeap and downHeap methods to fix this \uD83D\uDEE0️");
        }
    }

    @Test
    public void testBuildHeap() {
        Integer[] arr = new Integer[]{1, 23, 13, 14, 5, 66, 7};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));

        try {
            Integer[] earr = {1,5,7,14,23,66,13};
            ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(earr));
            minHeap = new MinHeap<>(list);
            assertTrue("YOUr BUILD HEAP DOESN'T WORK \uD83D\uDE3E",verifyHeapArray(expected, minHeap.getBackingArray()));
        } catch (Exception e) {
            System.out.println("Alright, I didn't expect to catch a \uD83C\uDF4C here. " +
                    "I caught this not so pretty looking message \n" + e.getMessage() + "\n" +
                    "You should check out your buildHeap and downHeap methods to fix this \uD83D\uDEE0️");
        }
    }

    public boolean verifyHeapArray(ArrayList<Integer> list, Comparable[] minHeapArray) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(minHeapArray[i+1])) {
                return false;
            }
        }
        return true;
    }
    @Test (expected = IllegalArgumentException.class)
    public void addFail() {
        minHeap.add(null);
    }

    @Test
    public void add() {
        minHeap.add(1);
        minHeap.add(3);
        minHeap.add(5);
        Integer[] expected = {null, 1, 3, 5, null, null,
                null, null, null, null, null, null, null};
        assertEquals(3, minHeap.size());
        assertArrayEquals(expected, minHeap.getBackingArray());

        /*
            1
          3   5
         */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        minHeap = new MinHeap<>(list);
        Integer[] expected2 = {null, 1, 3, 5, null, null, null};
        assertEquals(3, minHeap.size());
        assertArrayEquals(expected2, minHeap.getBackingArray());

        /*
                      0
                1/          \5
             3/
         */
        minHeap.add(0);
        Integer[] expected3 = {null, 0, 1, 5, 3, null, null};
        assertEquals(4, minHeap.size());
        assertArrayEquals(expected3, minHeap.getBackingArray());

        /*
                      -1
                0/          \5
             3/   \1
         */
        minHeap.add(-1);
        Integer[] expected4 = {null, -1, 0, 5, 3, 1, null};
        assertEquals(5, minHeap.size());
        assertArrayEquals(expected4, minHeap.getBackingArray());

        /*
                      -2
                0/          \-1
             3/   \1     5/
         */
        minHeap.add(-2);
        Integer[] expected5 = {null, -2, 0, -1, 3, 1, 5};
        assertEquals(6, minHeap.size());
        assertArrayEquals(expected5, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
         */
        minHeap.add(-3);
        Integer[] expected6 = {null, -3, 0, -2, 3, 1, 5, -1, null, null, null,
                null, null, null};
        assertEquals(7, minHeap.size());
        assertArrayEquals(expected6, minHeap.getBackingArray());

        //ADDED A BUNCH OF ITEMS THAT NEEDED REHEAPING. ADDING NON REHEAPING

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/
         */
        minHeap.add(4);
        Integer[] expected7 = {null, -3, 0, -2, 3, 1, 5, -1, 4, null, null,
                null, null, null};
        assertEquals(8, minHeap.size());
        assertArrayEquals(expected7, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/ \6
         */
        minHeap.add(6);
        Integer[] expected8 = {null, -3, 0, -2, 3, 1, 5, -1, 4, 6, null,
                null, null, null};
        assertEquals(9, minHeap.size());
        assertArrayEquals(expected8, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/ \6 7/
         */
        minHeap.add(7);
        Integer[] expected9 = {null, -3, 0, -2, 3, 1, 5, -1, 4, 6, 7,
                null, null, null};
        assertEquals(10, minHeap.size());
        assertArrayEquals(expected9, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/ \6 7/\8
         */
        minHeap.add(8);
        Integer[] expected10 = {null, -3, 0, -2, 3, 1, 5, -1, 4, 6, 7,
                8, null, null};
        assertEquals(11, minHeap.size());
        assertArrayEquals(expected10, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/ \6 7/\8  9/
         */
        minHeap.add(9);
        Integer[] expected11 = {null, -3, 0, -2, 3, 1, 5, -1, 4, 6, 7,
                8, 9, null};
        assertEquals(12, minHeap.size());
        assertArrayEquals(expected11, minHeap.getBackingArray());

        /*
                      -3
                0/          \-2
             3/   \1     5/    \-1
           4/ \6 7/\8  9/ \10
         */
        minHeap.add(10);
        Integer[] expected12 = {null, -3, 0, -2, 3, 1, 5, -1, 4, 6, 7,
                8, 9, 10};
        assertEquals(13, minHeap.size());
        assertArrayEquals(expected12, minHeap.getBackingArray());
    }

    @Test
    public void remove() {
        /*
                    0
              3/         \1
           5/   \4     2/    \6
         8/ \9 15/\11 7/\10 21/
         */
        System.out.println(populatedMin.size());
        assertEquals(14, populatedMin.size());

        assertEquals(0, (long) populatedMin.remove());
        /*
                    1
              3/         \2
           5/   \4      7/    \6
         8/ \9 15/\11 21/\10
         */
        Integer[] arr1 = {null, 1, 3, 2, 5, 4, 7, 6, 8, 9, 15, 11, 21, 10,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        System.out.println(arr1.length);
        assertArrayEquals(arr1, populatedMin.getBackingArray());
        assertEquals(13, populatedMin.size());

        assertEquals(1, (long) populatedMin.remove());
        /*
                    2
              3/         \6
           5/   \4      7/  \10
         8/ \9 15/\11 21/
         */
        Integer[] arr2 = {null, 2, 3, 6, 5, 4, 7, 10, 8, 9, 15, 11, 21, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr2, populatedMin.getBackingArray());
        assertEquals(12, populatedMin.size());

        assertEquals(2, (long) populatedMin.remove());
        /*
                    3
              4/         \6
           5/   \11     7/  \10
         8/ \9 15/\21
         */
        Integer[] arr3 = {null, 3, 4, 6, 5, 11, 7, 10, 8, 9, 15, 21, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr3, populatedMin.getBackingArray());
        assertEquals(11, populatedMin.size());

        assertEquals(3, (long) populatedMin.remove());
        /*
                    4
              5/         \6
           8/   \11     7/  \10
         21/ \9 15/
         */
        Integer[] arr4 = {null, 4, 5, 6, 8, 11, 7, 10, 21, 9, 15, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr4, populatedMin.getBackingArray());
        assertEquals(10, populatedMin.size());

        assertEquals(4, (long) populatedMin.remove());
        /*
                     5
               8/         \6
           9/   \11     7/  \10
         21/ \15
         */
        Integer[] arr5 = {null, 5, 8, 6, 9, 11, 7, 10, 21, 15, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr5, populatedMin.getBackingArray());
        assertEquals(9, populatedMin.size());

        assertEquals(5, (long) populatedMin.remove());
        /*
                     6
               8/          \7
           9/   \11     15/  \10
         21/
         */
        Integer[] arr6 = {null, 6, 8, 7, 9, 11, 15, 10, 21, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr6, populatedMin.getBackingArray());
        assertEquals(8, populatedMin.size());

        assertEquals(6, (long) populatedMin.remove());
        /*
                     7
               8/          \10
           9/   \11     15/  \21
         */
        Integer[] arr7 = {null, 7, 8, 10, 9, 11, 15, 21, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr7, populatedMin.getBackingArray());
        assertEquals(7, populatedMin.size());

        assertEquals(7, (long) populatedMin.remove());
        /*
                     8
              9/          \10
           21/   \11   15/
         */
        Integer[] arr8 = {null, 8, 9, 10, 21, 11, 15, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr8, populatedMin.getBackingArray());
        assertEquals(6, populatedMin.size());

        assertEquals(8, (long) populatedMin.remove());
        /*
                     9
              11/          \10
           21/   \15
         */
        Integer[] arr9 = {null, 9, 11, 10, 21, 15, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr9, populatedMin.getBackingArray());
        assertEquals(5, populatedMin.size());

        assertEquals(9, (long) populatedMin.remove());
        /*
                     10
              11/          \15
           21/
         */
        Integer[] arr10 = {null, 10, 11, 15, 21, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr10, populatedMin.getBackingArray());
        assertEquals(4, populatedMin.size());

        assertEquals(10, (long) populatedMin.remove());
        /*
                     11
              21/          \15
         */
        Integer[] arr11 = {null, 11, 21, 15, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr11, populatedMin.getBackingArray());
        assertEquals(3, populatedMin.size());

        assertEquals(11, (long) populatedMin.remove());
        /*
                     15
              21/
         */
        Integer[] arr12 = {null, 15, 21, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr12, populatedMin.getBackingArray());
        assertEquals(2, populatedMin.size());

        assertEquals(15, (long) populatedMin.remove());
        /*
                     21
         */
        Integer[] arr13 = {null, 21, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr13, populatedMin.getBackingArray());
        assertEquals(1, populatedMin.size());

        assertEquals(21, (long) populatedMin.remove());
        /* null */
        Integer[] arr14 = {null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null};
        assertArrayEquals(arr14, populatedMin.getBackingArray());
        assertEquals(0, populatedMin.size());
        //Billy
    }

    @Test (expected = NoSuchElementException.class)
    public void removeFail() {
        minHeap.remove();
    }

    @Test
    public void getMin() {
        assertNotNull(populatedMin.getMin());
        assertEquals(0, (long) populatedMin.getMin());
    }

    @Test (expected = NoSuchElementException.class)
    public void getMinFail() {
        minHeap = new MinHeap<>();
        Integer a = minHeap.getMin();
    }

    @Test
    public void isEmpty() {
        minHeap = new MinHeap<>();
        assertTrue(minHeap.isEmpty());

        ArrayList<Integer> empty = new ArrayList<>();
        minHeap = new MinHeap<>(empty);
        assertTrue(minHeap.isEmpty());

        minHeap.add(1);
        minHeap.remove();
        assertTrue(minHeap.isEmpty());
    }

    @Test
    public void clear() {
        populatedMin.clear();
        Integer[] fugg = {null, null, null, null, null, null, null, null, null, null, null, null,
                null};
        assertArrayEquals(fugg, populatedMin.getBackingArray());
        ;
    }

    @AfterClass
    public static void testCompleted() throws Exception {

        if (failures <= 0 && succeded >= 19 /*test count*/) {
            try {
                Desktop.getDesktop().browse(
                        new URL("https://ape-unit.github.io/"
                                + "RamRanchGBA/").toURI());
            } catch (Exception e) { }
        } else {
            System.out.println("Ape game not unlocked."
                    + "Please continue to work on your tests.");
        }
    }
}

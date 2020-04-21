import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

public class RandomTest {
    private static final int TIMEOUT = 20000;
    private static final long SEED = 42;
    private static final int TEST_CASES = 100;
    private static final int ARRAY_LENGTH = 9001;
    private Random rand;
    private long currSeed = 42;
    private Thing[] arr;
    private int[] ints;
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private static Thing[] copy(Thing[] other) {
        Thing[] out = new Thing[other.length];
        for (int i = 0; i < other.length; i++) {
            out[i] = new Thing(other[i].num, other[i].first);
        }
        return out;
    }
    private void shuffle(int[] a, Random random) {
        for (int i = 0; i < a.length - 1; i++) {
            swap(a, i, i + random.nextInt(a.length - i));
        }
    }
    private void shuffle(Thing[] a, Random random) {
        for (int i = 0; i < a.length - 1; i++) {
            swap(a, i, i + random.nextInt(a.length - i));
        }
        HashSet<Integer> list = new HashSet<>();
        for (Thing thing : a) {
            thing.first = list.add(thing.num);
        }
    }
    @Before
    public void setUp() {
        rand = new Random(SEED);
        arr = new Thing[ARRAY_LENGTH];
        ints = new int[ARRAY_LENGTH];
        boolean first = true;
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            arr[i] = new Thing(i / 2, first);
            ints[i] = i / 2;
            first = !first;
        }
    }
    @Test(timeout = TIMEOUT)
    public void selection() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            Thing[] temp = copy(arr);
            shuffle(temp, rand);
            Sorting.selectionSort(temp, Comparator.naturalOrder());
            checkUnstable(temp);
        }
    }
    @Test(timeout = TIMEOUT)
    public void merge() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            Thing[] temp = copy(arr);
            shuffle(temp, rand);
            Sorting.mergeSort(temp, Comparator.naturalOrder());
            checkStable(temp);
        }
    }
    @Test(timeout = TIMEOUT)
    public void cocktail() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            Thing[] temp = copy(arr);
            shuffle(temp, rand);
            Sorting.cocktailSort(temp, Comparator.naturalOrder());
            checkStable(temp);
        }
    }
    @Test(timeout = TIMEOUT)
    public void insert() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            Thing[] temp = copy(arr);
            shuffle(temp, rand);
            Sorting.insertionSort(temp, Comparator.naturalOrder());
            checkStable(temp);
        }
    }
    @Test(timeout = TIMEOUT)
    public void quick() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            Thing[] temp = copy(arr);
            shuffle(temp, rand);
            Sorting.quickSort(temp, Comparator.naturalOrder(), rand);
            checkUnstable(temp);
        }
    }
    @Test(timeout = TIMEOUT)
    public void radix() {
        for (int i = 0; i < TEST_CASES; i++) {
            currSeed = rand.nextLong();
            rand.setSeed(currSeed);
            int[] temp = ints.clone();
            shuffle(temp, rand);
            Sorting.lsdRadixSort(temp);
            checkRadix(temp);
        }
    }
    private void checkStable(Thing[] temp) {
        for (int i = 0; i < arr.length; i++) {
            assertEquals("Random Seed: " + currSeed + " excpected " + arr[i] + " but found " + temp[i] + " at index " + i, arr[i], temp[i]);
        }
    }
    private void checkUnstable(Thing[] temp) {
        for (int i = 0; i < arr.length; i++) {
            assertEquals("Random Seed: " + currSeed + " excpected " + arr[i].num + " but found " + temp[i].num + " at index " + i, arr[i].num, temp[i].num);
        }
    }
    private void checkRadix(int[] temp) {
        for (int i = 0; i < ints.length; i++) {
            assertEquals("Random Seed: " + currSeed + " excpected " + ints[i] + " but found " + temp[i] + " at index " + i, ints[i], temp[i]);
        }
    }
    private static class Thing implements Comparable<Thing> {
        private int num;
        private boolean first;
        private Thing(int n, boolean f) {
            num = n;
            first = f;
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Thing)) {
                return false;
            }
            return num == ((Thing) other).num && first == ((Thing) other).first;
        }
        @Override
        public int compareTo(Thing other) {
            return Integer.compare(num, other.num);
        }
        @Override
        public String toString() {
            return "(" + num + ", " + first + ")";
        }
    }
}
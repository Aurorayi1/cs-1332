import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

public class RandomTestEff {
    private static final int TIMEOUT = 20000;
    private static final long SEED = 42;
    private static final int TEST_CASES = 100;
    private static final double LOG_BASE = 1.2;
    private static final int MAX_LENGTH = (int) (Math.log(9001) / Math.log(LOG_BASE));
    private static int length = 9001;
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
    public void setUp() {
        rand = new Random(SEED);
        arr = new Thing[length];
        boolean first = true;
        for (int i = 0; i < length; i++) {
            arr[i] = new Thing(i / 2, first);
            first = !first;
        }
    }
    @Test(timeout = Integer.MAX_VALUE)
    public void selection() {
        File output = new File("selection.csv");
        PrintStream write = null;
        try {
            write = new PrintStream(output);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        write.println("length,sorted,reversed,random");
        for (int j = 0; j < MAX_LENGTH; j++) {
            length = (int) Math.pow(LOG_BASE, j);
            setUp();
            long[] times = new long[3];
            for (int i = 0; i < TEST_CASES; i++) {
                currSeed = rand.nextLong();
                rand.setSeed(currSeed);

                Thing[] temp = copy(arr);
                long t = 0;

                t = System.nanoTime();
                Sorting.selectionSort(temp, Comparator.naturalOrder());
                times[0] += System.nanoTime() - t;

                t = System.nanoTime();
                Sorting.selectionSort(temp, Comparator.reverseOrder());
                times[1] += System.nanoTime() - t;

                shuffle(temp, rand);

                t = System.nanoTime();
                Sorting.selectionSort(temp, Comparator.naturalOrder());
                times[2] += System.nanoTime() - t;

            }
            write.print(length + ",");
            write.print(times[0] + ",");
            write.print(times[1] + ",");
            write.println(times[2]);
        }
    }
    @Test(timeout = Integer.MAX_VALUE)
    public void merge() {
        //The first time is inconsistant for complex JVM reasons I dont understand
        for (int z = 0; z < 2; z++) {
            File output = new File("merge.csv");
            PrintStream write = null;
            try {
                write = new PrintStream(output);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            write.println("length,sorted,reversed,random");
            for (int j = 0; j < MAX_LENGTH; j++) {
                length = (int) Math.pow(LOG_BASE, j);
                setUp();
                long[] times = new long[3];
                for (int i = 0; i < TEST_CASES; i++) {
                    currSeed = rand.nextLong();
                    rand.setSeed(currSeed);

                    Thing[] temp = copy(arr);
                    long t = 0;

                    t = System.nanoTime();
                    Sorting.mergeSort(temp, Comparator.naturalOrder());
                    times[0] += System.nanoTime() - t;

                    t = System.nanoTime();
                    Sorting.mergeSort(temp, Comparator.reverseOrder());
                    times[1] += System.nanoTime() - t;

                    shuffle(temp, rand);

                    t = System.nanoTime();
                    Sorting.mergeSort(temp, Comparator.naturalOrder());
                    times[2] += System.nanoTime() - t;

                }
                write.print(length + ",");
                write.print(times[0] + ",");
                write.print(times[1] + ",");
                write.println(times[2]);
            }
        }
    }
    @Test(timeout = Integer.MAX_VALUE)
    public void cocktail() {
        File output = new File("cocktail.csv");
        PrintStream write = null;
        try {
            write = new PrintStream(output);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        write.println("length,sorted,reversed,random");
        for (int j = 0; j < MAX_LENGTH; j++) {
            length = (int) Math.pow(LOG_BASE, j);
            setUp();
            long[] times = new long[3];
            for (int i = 0; i < TEST_CASES; i++) {
                currSeed = rand.nextLong();
                rand.setSeed(currSeed);

                Thing[] temp = copy(arr);
                long t = 0;

                t = System.nanoTime();
                Sorting.mergeSort(temp, Comparator.naturalOrder());
                times[0] += System.nanoTime() - t;

                t = System.nanoTime();
                Sorting.mergeSort(temp, Comparator.reverseOrder());
                times[1] += System.nanoTime() - t;

                shuffle(temp, rand);

                t = System.nanoTime();
                Sorting.mergeSort(temp, Comparator.naturalOrder());
                times[2] += System.nanoTime() - t;

            }
            write.print(length + ",");
            write.print(times[0] + ",");
            write.print(times[1] + ",");
            write.println(times[2]);
        }
    }
    @Test(timeout = Integer.MAX_VALUE)
    public void insert() {
        File output = new File("insert.csv");
        PrintStream write = null;
        try {
            write = new PrintStream(output);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        write.println("length,sorted,reversed,random");
        for (int j = 0; j < MAX_LENGTH; j++) {
            length = (int) Math.pow(LOG_BASE, j);
            setUp();
            long[] times = new long[3];
            for (int i = 0; i < TEST_CASES; i++) {
                currSeed = rand.nextLong();
                rand.setSeed(currSeed);

                Thing[] temp = copy(arr);
                long t = 0;

                t = System.nanoTime();
                Sorting.insertionSort(temp, Comparator.naturalOrder());
                times[0] += System.nanoTime() - t;

                t = System.nanoTime();
                Sorting.insertionSort(temp, Comparator.reverseOrder());
                times[1] += System.nanoTime() - t;

                shuffle(temp, rand);

                t = System.nanoTime();
                Sorting.insertionSort(temp, Comparator.naturalOrder());
                times[2] += System.nanoTime() - t;

            }
            write.print(length + ",");
            write.print(times[0] + ",");
            write.print(times[1] + ",");
            write.println(times[2]);
        }
    }
    @Test(timeout = Integer.MAX_VALUE)
    public void quick() {
        File output = new File("quick.csv");
        PrintStream write = null;
        try {
            write = new PrintStream(output);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        write.println("length,sorted,reversed,random");
        for (int j = 0; j < MAX_LENGTH; j++) {
            length = (int) Math.pow(LOG_BASE, j);
            setUp();
            long[] times = new long[3];
            for (int i = 0; i < TEST_CASES; i++) {
                currSeed = rand.nextLong();
                rand.setSeed(currSeed);

                Thing[] temp = copy(arr);
                long t = 0;
                rand.setSeed(currSeed);

                t = System.nanoTime();
                Sorting.quickSort(temp, Comparator.naturalOrder(), rand);
                times[0] += System.nanoTime() - t;

                rand.setSeed(currSeed);

                t = System.nanoTime();
                Sorting.quickSort(temp, Comparator.reverseOrder(), rand);
                times[1] += System.nanoTime() - t;

                rand.setSeed(currSeed);
                shuffle(temp, rand);
                rand.setSeed(currSeed);

                t = System.nanoTime();
                Sorting.quickSort(temp, Comparator.naturalOrder(), rand);
                times[2] += System.nanoTime() - t;

            }
            write.print(length + ",");
            write.print(times[0] + ",");
            write.print(times[1] + ",");
            write.println(times[2]);
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
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TepisExtremeValueLsdRadixTest {
    @Test
    public void testAllExtremeLarge() {
        int[] values = new int[100];
        int[] correct = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = Integer.MAX_VALUE - i;
            correct[i] = Integer.MAX_VALUE - 99 + i;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testAllExtremeSmall() {
        int[] values = new int[100];
        int[] correct = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = Integer.MIN_VALUE + 99 - i;
            correct[i] = Integer.MIN_VALUE + i;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testExtremeMixed() {
        int[] values = new int[200];
        int[] correct = new int[200];
        for (int i = 0; i < 100; i++) {
            values[i] = Integer.MAX_VALUE - i;
            correct[i + 100] = Integer.MAX_VALUE - 99 + i;
        }
        for (int i = 0; i < 100; i++) {
            values[i + 100] = Integer.MIN_VALUE + 99 - i;
            correct[i] = Integer.MIN_VALUE + i;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testOneExtremeSmall() {
        int[] values = new int[101];
        int[] correct = new int[101];
        for (int i = 0; i < 100; i++) {
            values[i] = 99 - i;
            correct[i + 1] = i;
        }
        values[100] = Integer.MIN_VALUE;
        correct[0] = Integer.MIN_VALUE;
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testAllZeros() {
        int[] values = new int[100];
        int[] correct = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = 0;
            correct[i] = 0;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testAllOnes() {
        int[] values = new int[100];
        int[] correct = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = 1;
            correct[i] = 1;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
    @Test
    public void testAllNegativeOnes() {
        int[] values = new int[100];
        int[] correct = new int[100];
        for (int i = 0; i < 100; i++) {
            values[i] = -1;
            correct[i] = -1;
        }
        Sorting.lsdRadixSort(values);
        assertArrayEquals(values, correct);
    }
}
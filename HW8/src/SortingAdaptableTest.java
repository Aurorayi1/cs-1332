import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;

// Basic Adaptability tests
public class SortingAdaptableTest {
    private static final int TIMEOUT = 200;
    private SpecialInt[] intsSorted;
    private SpecialInt[] almostSorted;
    private Comparator<SpecialInt> comp;

    @Before
    public void setUp() {
        intsSorted = new SpecialInt[10];
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 9) {
                intsSorted[i] = new SpecialInt(i, 1);
            }
            intsSorted[i] = new SpecialInt(i, 2);
        }
        almostSorted = new SpecialInt[10];
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                almostSorted[i] = new SpecialInt(i, 1);
            }
            almostSorted[i] = new SpecialInt(i, 4);
        }
        almostSorted[8] = new SpecialInt(9, 2);
        almostSorted[9] = new SpecialInt(8, 2);
        comp = SpecialInt.getSpecialIntComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSorted() {
        Sorting.cocktailSort(intsSorted, comp);
        assertEquals(10, intsSorted.length);
        for (int i = 0; i < intsSorted.length; i++) {
            assertEquals(i, intsSorted[i].getValueNice());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailAlmostSorted() {
        Sorting.cocktailSort(almostSorted, comp);
        assertEquals(10, almostSorted.length);
        for (int i = 0; i < almostSorted.length; i++) {
            assertEquals(i, almostSorted[i].getValueNice());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSorted() {
        Sorting.insertionSort(intsSorted, comp);
        assertEquals(10, intsSorted.length);
        for (int i = 0; i < intsSorted.length; i++) {
            assertEquals(i, intsSorted[i].getValueNice());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionAlmostSorted() {
        Sorting.insertionSort(almostSorted, comp);
        assertEquals(10, almostSorted.length);
        for (int i = 0; i < almostSorted.length; i++) {
            assertEquals(i, almostSorted[i].getValueNice());
        }
    }

    private static class SpecialInt {
        private final int value; // the actual value of the integer
        private int numUses; //the number of times you can get the data

        private SpecialInt(int value, int numUses) {
            this.value = value;
            this.numUses = numUses;
        }

        public int getValueNice() {
            // for testing and not comparing
            return value;
        }

        public int getValue() { // used for making comparisons
            if (numUses == 0) {
                System.out.println("Adaptability test failed. The data"
                        + " with value " + value + " was accessed too "
                        + "many times");
                while (true) {
                    continue;
                }
            }
            numUses--;
            return value;
        }

        public static Comparator<SpecialInt> getSpecialIntComparator() {
            return new Comparator<SpecialInt>() {
                @Override
                public int compare(SpecialInt o1, SpecialInt o2) {
                    return o1.getValue() - o2.getValue();
                }
            };
        }
    }
}
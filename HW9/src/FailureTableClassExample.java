import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class FailureTableClassExample {

    private static final int TIMEOUT = 200;

    private CharacterComparator comparator;


    @Before
    public void setUp() {
        comparator = new CharacterComparator();
    }
    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable2() {
        /*
            pattern: babaababab
            failure table: [0, 0, 1, 2, 0, 1, 2, 3, 4, 3]
            comparisons: 4
         */
        int[] failureTable = PatternMatching
                .buildFailureTable("babaababab", comparator);
        int[] expected = {0, 0, 1, 2, 0, 1, 2, 3, 4, 3};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 11.", 11, comparator.getComparisonCount());
    }
}
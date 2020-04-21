import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LinearProbingTepisReGhostTest {
    private LinearProbingHashMap<TepisInt, String> createMap() {
        LinearProbingHashMap<TepisInt, String> map =
                new LinearProbingHashMap<>(8);
        map.put(new TepisInt(100), "a100");
        map.put(new TepisInt(200), "a200");
        map.put(new TepisInt(300), "a300");
        map.put(new TepisInt(400), "a400");
        map.put(new TepisInt(500), "a500");

        map.remove(new TepisInt(200));
        map.remove(new TepisInt(300));
        map.remove(new TepisInt(400));

        new HashMapShapeAsserter(2,
                new AE(100, "a100"),
                new AE(200, "a200", true),
                new AE(300, "a300", true),
                new AE(400, "a400", true),
                new AE(500, "a500"),
                null,
                null,
                null
        ).assertEquals(map);

        // If your error is above, make sure you pass regular Tepis test first
        return map;
    }
    @Test
    public void testPutReGhost() {
        LinearProbingHashMap<TepisInt, String> map = this.createMap();
        map.put(new TepisInt(300), "b300");

        new HashMapShapeAsserter(3,
                new AE(100, "a100"),
                new AE(300, "b300"),
                new AE(300, "a300", true),
                new AE(400, "a400", true),
                new AE(500, "a500"),
                null,
                null,
                null
        ).assertEquals(map);
    }

    /**
     * A container class that stores integers.
     * The hash of it is always its value % 100.
     * For example, new TepisInt(207).hashCode() = 7.
     */
    public static class TepisInt {
        private int value;
        public TepisInt(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            TepisInt tepisInt = (TepisInt) o;
            return this.value == tepisInt.value;
        }

        @Override
        public int hashCode() {
            return this.value % 100;
        }

        @Override
        public String toString() {
            return value + " (h=" + this.hashCode() + ")";
        }
    }

    /** Asserter entry */
    public static class AE {
        private TepisInt key;
        private String value;
        private boolean deleted;
        public AE(int k, String v, boolean d) {
            this.key = new TepisInt(k);
            this.value = v;
            this.deleted = d;
        }
        public AE(int k, String v) {
            this(k, v, false);
        }
        @Override
        public String toString() {
            return (this.deleted ? "[D] " : "") + this.key + ": "
                    + this.value;
        }
    }

    public static class HashMapShapeAsserter {
        private int size;
        private AE[] expected;
        public HashMapShapeAsserter(int size, AE... expected) {
            this.size = size;
            this.expected = expected;
        }
        public void assertEquals(
                LinearProbingHashMap<TepisInt, String> against
        ) {
            if (against.size() != this.size) {
                throw new AssertionError("HashMap returned wrong value"
                        + "for .size(). Expected: " + this.size + ". Actual: "
                        + against.size() + ".");
            }
            LinearProbingMapEntry<TepisInt, String>[] table =
                    against.getTable();
            if (table == null) {
                throw new AssertionError("Table is null.");
            }
            if (table.length != this.expected.length) {
                throw new HashMapShapeAssertionError("Size of the backing "
                        + "array is incorrect.", table);
            }
            for (int i = 0; i < this.expected.length; i++) {
                AE expected = this.expected[i];
                LinearProbingMapEntry<TepisInt, String> actual = table[i];
                if (expected == null && actual != null) {
                    throw new HashMapShapeAssertionError("The entry with "
                            + "index = " + i + " should be null.",
                            table);
                }
                if (expected != null && actual == null) {
                    throw new HashMapShapeAssertionError("The entry with "
                            + "index = " + i + " should not be null.",
                            table);
                }
                if (expected == null && actual == null) {
                    continue;
                }
                if (expected.deleted != actual.isRemoved()) {
                    throw new HashMapShapeAssertionError("The entry with "
                            + "index = " + i + " has wrong removed state.",
                            table);
                }
                if (!expected.key.equals(actual.getKey())) {
                    throw new HashMapShapeAssertionError("The entry with "
                            + "index = " + i + " has wrong key.",
                            table);
                }
                // Strict equality intended
                if (expected.value != actual.getValue()) {
                    throw new HashMapShapeAssertionError("The entry with "
                            + "index = " + i + " has wrong value.",
                            table);
                }
            }
        }

        public class HashMapShapeAssertionError extends AssertionError {
            public HashMapShapeAssertionError(
                    String message,
                    LinearProbingMapEntry<TepisInt, String>[] actual
            ) {
                super(message
                        + "\nExpected: "
                        + Arrays.stream(HashMapShapeAsserter.this.expected)
                        .map(ae -> ae == null ? "null" : ae.toString())
                        .collect(Collectors.joining(", ", "[", "]"))
                        + " Size = "
                        + HashMapShapeAsserter.this.expected.length
                        + "\n  Actual: "
                        + Arrays.stream(actual)
                        .map(entry -> (entry == null)
                                ? "null"
                                : ((entry.isRemoved())
                                ? "[D] "
                                : "") + entry.getKey() + ": "
                                + entry.getValue())
                        .collect(Collectors.joining(", ", "[", "]"))
                        + " Size = "
                        + actual.length
                );
            }
        }
    }
}
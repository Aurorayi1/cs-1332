import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Wenye Yi
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        int first;
        for (int i = arr.length - 1; i > 0; i--) {
            first = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[first]) > 0) {
                    first = j;
                }
            }

            swap(arr, first, i);
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j > -1 && comparator.compare(temp, arr[j]) < 0) {
                arr[j + 1] = arr[j];
                j--;

            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "The array or comparator is null!");
        }
        boolean swapped = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        T temp;
        while (swapped) {
            int end = endIndex;
            swapped = false;
            for (int i = startIndex; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    endIndex = i;
                }
            }
            int start = startIndex;
            if (swapped) {
                swapped = false;
                for (int j = endIndex; j > start; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        swapped = true;
                        startIndex = j;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (arr.length > 1) {
            int length = arr.length;
            int midIndex = (int) length / 2;
            T[] leftArray = (T[]) new Object[midIndex];
            T[] rightArray = (T[]) new Object[length - midIndex];

            for (int i = 0; i < midIndex; i++) {
                leftArray[i] = arr[i];
            }
            for (int i = midIndex; i < length; i++) {
                rightArray[i - midIndex] = arr[i];
            }
            int left = 0;
            int right = 0;
            int curr = 0;

            mergeSort(leftArray, comparator);
            mergeSort(rightArray, comparator);

            while (left < midIndex && right < (length - midIndex)) {
                if (comparator.compare(leftArray[left],
                        rightArray[right]) <= 0) {
                    arr[curr] = leftArray[left];
                    left++;
                } else {
                    arr[curr] = rightArray[right];
                    right++;
                }
                curr++;
            }

            while (left < midIndex) {
                arr[curr] = leftArray[left];
                left++;
                curr++;
            }

            while (right < length - midIndex) {
                arr[curr] = rightArray[right];
                right++;
                curr++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random cannot be null");
        }

        quickSortHelper(arr, comparator, rand, 0, arr.length);

    }

    /**
     * helper method for quicksort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param left the left side of the array
     * @param right the right side of the array
     * @param <T> data type to sort
     */

    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                            Random rand, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIndex];
        swap(arr, left, pivotIndex);
        int leftIndex = left + 1;
        int rightIndex = right - 1;

        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex
                    && comparator.compare(arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }
            while (leftIndex <= rightIndex
                    && comparator.compare(arr[rightIndex], pivot) > 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                swap(arr, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        swap(arr, left, rightIndex);

        if (left < rightIndex) {
            quickSortHelper(arr, comparator, rand, left, rightIndex);
        }

        if (right > leftIndex) {
            quickSortHelper(arr, comparator, rand, rightIndex + 1, right);
        }

    }

    /**
     * a helper method for swapping elements
     * @param arr the array to sort
     * @param index1 the first index to sort
     * @param index2 the second index to sort
     * @param <T> the data type to sort out
     */

    private static <T> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("null args are not supported");
        }
        LinkedList<Integer>[] buckets =
                (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrayIndex = 0;
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        arr[arrayIndex++] = num;
                    }
                    buckets[i].clear();
                }
            }
            div = div * 10;
        }
    }
}

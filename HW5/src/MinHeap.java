import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Wenye Yi
 * @version 1.0
 * @userid wyi31
 * @GTID 903448948
 * Collaborators: I worked on this assignment alone.
 *
 * Resources: I only used course materials.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        } else {
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) != null) {
                    backingArray[i + 1] = data.get(i);
                    size++;
                } else {
                    throw new IllegalArgumentException("Data is null.");
                }
            }
            for (int j = size / 2; j > 0; j--) {
                healifyHelper(j);
            }
        }
    }

    /**
     * helper method for MinHeap that heapifies one element at a time. The
     * index is compared to its left and right children and swapped with the
     * minchild if child is smaller than the parent
     *
     * @param index that we need to heapify
     */
    private void healifyHelper(int index) {
        T data = backingArray[index];
        // the left child is at index n * 2
        int leftChild = 2 * index;
        int rightChild;
        int minChild;
        while (leftChild <= size) {
            minChild = leftChild;
            rightChild = leftChild + 1;
            if ((rightChild <= size) && backingArray[rightChild].compareTo(
                    backingArray[minChild]) < 0) {
                minChild = rightChild;
            }
            if (data.compareTo(backingArray[minChild]) > 0) {
                backingArray[index] = backingArray[minChild];
                index = minChild;
                leftChild = 2 * index;
            } else {
                break;
            }
        }
        backingArray[index] = data;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        } else {
            // add the data to the last index
            // size + 1
            size++;
            if (size == backingArray.length) {
                // double the size if array is full
                T[] newArray = (T[]) new Comparable[size * 2];
                for (int i = 1; i < backingArray.length; i++) {
                    newArray[i] = backingArray[i];
                }
                backingArray = newArray;
            }
            // the last index of the array
            int index = size;
            // parent's index is at index / 2
            int parent = index / 2;
            // when parent's index is bigger than 0
            // and the data that needs to be added is smaller than parent
            while (parent > 0 && data.compareTo(backingArray[parent]) < 0) {
                // swap the data with parent
                backingArray[index] = backingArray[parent];
                index = parent;
                parent = index / 2;
            }
            backingArray[index] = data;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap is empty.");
        } else {
            T min = backingArray[1];
            // copy the last leaf to the root
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            healifyHelper(1);
            return min;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

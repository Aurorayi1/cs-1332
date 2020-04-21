/**
 * Your implementation of an ArrayList.
 *
 * @author Wenye Yi
 * @version 1.0
 * @userid wyi31
 * @GTID 903448948
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(
                    "Cannot insert data into a negative index.");
        } else if (index > size()) {
            throw new IndexOutOfBoundsException(
                    "Index is not within the array's capacity.");
        } else {
            if (size == backingArray.length) {
                T[] newArray = (T[]) new Object[INITIAL_CAPACITY * 2];
                for (int i = 0; i < index; i++) {
                    newArray[i] = backingArray[i];
                }
                newArray[index] = data;
                for (int i = index; i < backingArray.length; i++) {
                    newArray[i + 1] = backingArray[i];
                }
                backingArray = newArray;
            } else {
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }
                backingArray[index] = data;
            }
        }
        size++;
    }

    /**
     * double the capacity of the backingArray in this resizeArray method
     */
    private void resizeArray() {
        T[] newArray = (T[]) new Object[INITIAL_CAPACITY * 2];
        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }
    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        } else if (backingArray[backingArray.length - 1] != null) {
            resizeArray();
        }
        for (int i = size; i > 0; i--) {
            backingArray[i] = backingArray[i - 1];
        }
        backingArray[0] = data; // adding the data in the front
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        int index;
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        } else if (size >= backingArray.length) {
            index = backingArray.length;
            resizeArray();
        } else {
            index = size;
        }
        backingArray[index] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T remove;
        if (index < 0) {
            throw new IndexOutOfBoundsException(
                    "Cannot insert data into a negative index.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index is not within the array's capacity");
        } else {
            remove = backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
        }
        return remove;

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T removeFront;
        if (size != 0) {
            removeFront = backingArray[0];
            for (int i = 0; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
        } else {
            removeFront = null;
        }
        return removeFront;

    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size != 0) {
            T removeBack = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return removeBack;
        } else {
            return null;
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || backingArray.length == 0) {
            throw new IndexOutOfBoundsException(
                    "Cannot insert data into a negative index.");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index is not within the array's capacity.");
        } else {
            return backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        T[] newArray = (T[]) new Object[INITIAL_CAPACITY];
        backingArray = newArray;
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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

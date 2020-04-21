import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Wenye Yi
 *
 * Collaborators: I worked on the assignment alone.
 *
 * Resources: I only used course materials.
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        } else {
            if (size == 0) {
                head = newNode;
                tail = newNode;
                size++;
            } else {
                tail.setNext(newNode);
                newNode.setPrevious(tail);
                tail = newNode;
                size++;
            }
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Cannot remove data from an empty deque.");
        } else {
            T data = head.getData(); // get the head data out
            if (size == 1) {
                head = null;
                tail = null;
                size = 0;
            } else {
                head = head.getNext();
                head.setPrevious(null);
                size--;
            }
            return data;
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Cannot remove data from an empty deque.");
        } else {
            T data = tail.getData();
            if (size == 1) {
                head = null;
                tail = null;
                size = 0;
            } else {
                tail = tail.getPrevious();
                tail.setNext(null);
                size--;
            }
            return data;
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Cannot get data from an empty deque.");
        } else {
            T data = head.getData();
            return data;
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException(
                    "Cannot get data from an empty deque.");
        } else {
            T data = tail.getData();
            return data;
        }
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

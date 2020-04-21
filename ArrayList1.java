public class ArrayList1 {
    public static void main(String args[]) {
     for (int i = 5; i > 0; i--) {
         System.out.println(i);
     }

    }

}
/**
 * import java.util.NoSuchElementException;
 *
 * /**
 *  * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *  *
 *  *
 *  * Collaborators: I worked on the homework assignment alone.
 *  *
 *  * Resources: I only used course materials.
 *  */
 *

public class DoublyLinkedList<T> {
 *
         *     // Do not add new instance variables or modify existing ones.
         *
    private DoublyLinkedListNode<T> head;
 *
    private DoublyLinkedListNode<T> tail;
 *
    private int size;
 *
         *     // Do not add a constructor.
         *
         *     /**
  *      * Adds the element to the specified index.
  *      *
  *      * Must be O(1) for indices 0 and size and O(n) for all other cases.
  *      *
  *      * @param index the index at which to add the new element
  *      * @param data  the data to add at the specified index
  *      * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
  *      * @throws java.lang.IllegalArgumentException  if data is null
  *      */
         *

    public void addAtIndex(int index, T data) {
 *if (index < 0) {
 *             //index cannot be negative
 *throw new IndexOutOfBoundsException(
                    * "Cannot add at a negative index.");
 *} else if (index > size) {
 *             //index cannot be larger than the size
 *throw new IndexOutOfBoundsException(
                    * "Index is greater than the size.");
 *} else if (data == null) {
 *             //data cannot be null
 *throw new IllegalArgumentException("Cannot add null data.");
 *} else {
 *if (index == 0) {
 *                 // O(1)
 *addToFront(data);
 *} else if (index == size) {
 *                 // O(1)
 *addToBack(data);
 *} else {
 *                 //a temp head represents the current node
 *DoublyLinkedListNode current = head;
 *                 //create a newNode to store the data
 *DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);
 *                 //there was already a head so starts from index 1
 *for (int i = 1; i < index; i++) {
 *                     //get the next node of the current one
 *current = current.getNext();
 *}
 *                 //set the previous node of the node
 *                 //after the current node to the new node
 *current.getNext().setPrevious(newNode);
 *                 //set the next node of the new node to the node
 *                 //after the current node
 *newNode.setNext(current.getNext());
 *                 //set the previous node of the new node to the current node
 *newNode.setPrevious(current);
 *                 //set the next node of the current node to the new node
 *current.setNext(newNode);
 *size++;
 *}
 *}
 *}
 *
         *     /**
  *      * Adds the element to the front of the list.
  *      *
  *      * Must be O(1).
  *      *
  *      * @param data the data to add to the front of the list
  *      * @throws java.lang.IllegalArgumentException if data is null
  *      */
         *

    public void addToFront(T data) {
 *DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);
 *         // create the new node
 *if (data == null) {
 *throw new IllegalArgumentException("Cannot add null data.");
 *} else {
 *if (isEmpty()) {
 *head = newNode;
 *tail = head;
 *} else {
 *                 // set the new node's next to the head
 *newNode.setNext(head); // connect the new node to the LL
 *                 // set the head's prev to the new node
 *head.setPrevious(newNode); // connect the LL to the node
 *head = newNode; // point the head to the new node
 *}
 *size++;
 *}
 *
 *}
 *
         *     /**
  *      * Adds the element to the back of the list.
  *      *
  *      * Must be O(1).
  *      *
  *      * @param data the data to add to the back of the list
  *      * @throws java.lang.IllegalArgumentException if data is null
  *      */
         *

    public void addToBack(T data) {
 *if (data == null) {
 *throw new IllegalArgumentException("Cannot add null data.");
 *} else {
 *DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);
 *if (isEmpty()) {
 *head = newNode;
 *tail = head;
 *} else {
 *                 // set the new node's prev to the tail
 *newNode.setPrevious(tail); // connect the node to the LL
 *                 // set the tail's next to the new node
 *tail.setNext(newNode); // connect the LL to the node
 *tail = newNode; // point the tail to the new node
 *}
 *size++;
 *}
 *
 *}
 *
         *     /**
  *      * Removes and returns the element at the specified index.
  *      *
  *      * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
  *      *
  *      * @param index the index of the element to remove
  *      * @return the data formerly located at the specified index
  *      * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
  *      */
         *

    public T removeAtIndex(int index) {
 *if (index < 0) {
 *throw new IndexOutOfBoundsException(
                    * "Cannot remove at a negative index.");
 *} else if (index >= size) {
 *throw new IndexOutOfBoundsException(
                    * "Index is greater than the size.");
 *} else {
 *if (index == 0) {
 *return removeFromFront();
 *} else if (index == size - 1) {
 *return removeFromBack();
 *} else {
 *DoublyLinkedListNode current = head;
 *for (int i = 0; i < index; i++) {
 *current = current.getNext();
 *}
 *T result = (T) current.getData();
 *current.getPrevious().setNext(current.getNext());
 *current.getNext().setPrevious(current.getPrevious());
 *size--;
 *return result;
 *}
 *}
 *}
 *
         *     /**
  *      * Removes and returns the first element of the list.
  *      *
  *      * Must be O(1).
  *      *
  *      * @return the data formerly located at the front of the list
  *      * @throws java.util.NoSuchElementException if the list is empty
  *      */
         *

    public T removeFromFront() {
 *if (isEmpty()) {
 *throw new NoSuchElementException("The list is empty.");
 *} else {
 *T result = head.getData();
 *head = head.getNext(); //set the head to head's next
 *if (head != null) {
 *head.setPrevious(null);
 *} else {
 *tail = head;
 *}
 *             // set the head's previous to null
 *size--;
 *return result;
 *}
 *
 *}
 *
         *     /**
  *      * Removes and returns the last element of the list.
  *      *
  *      * Must be O(1).
  *      *
  *      * @return the data formerly located at the back of the list
  *      * @throws java.util.NoSuchElementException if the list is empty
  *      */
         *

    public T removeFromBack() {
 *if (isEmpty()) {
 *throw new NoSuchElementException("The list is empty.");
 *} else {
 *T result = tail.getData();
 *tail = tail.getPrevious();
 *if (tail != null) {
 *tail.setNext(null);
 *} else {
 *head = tail;
 *}
 *size--;
 *return result;
 *}
 *}
 *
         *     /**
  *      * Returns the element at the specified index.
  *      *
  *      * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
  *      *
  *      * @param index the index of the element to get
  *      * @return the data stored at the index in the list
  *      * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
  *      */
         *

    public T get(int index) {
 *if (index < 0) {
 *throw new IndexOutOfBoundsException("Index is less than 0.");
 *} else if (index >= size) {
 *throw new IndexOutOfBoundsException("Index is not within range.");
 *} else {
 *DoublyLinkedListNode current = head;
 *for (int i = 0; i < index; i++) {
 *current = current.getNext();
 *}
 *return (T) current.getData();
 *}
 *
 *}
 *
         *     /**
  *      * Returns whether or not the list is empty.
  *      *
  *      * Must be O(1).
  *      *
  *      * @return true if empty, false otherwise
  *      */
         *

    public boolean isEmpty() {
 *return head == null;
 *}
 *
         *     /**
  *      * Clears the list.
  *      *
  *      * Clears all data and resets the size.
  *      *
  *      * Must be O(1).
  *      */
         *

    public void clear() {
 *head = null;
 *tail = null;
 *size = 0;
 *}
 *
         *     /**
  *      * Removes and returns the last copy of the given data from the list.
  *      *
  *      * Do not return the same data that was passed in. Return the data that
  *      * was stored in the list.
  *      *
  *      * Must be O(1) if data is in the tail and O(n) for all other cases.
  *      *
  *      * @param data the data to be removed from the list
  *      * @return the data that was removed
  *      * @throws java.lang.IllegalArgumentException if data is null
  *      * @throws java.util.NoSuchElementException   if data is not found
  *      */
         *

    public T removeLastOccurrence(T data) {
 *if (data == null) {
 *throw new IllegalArgumentException(
                    * "Cannot search for data that is null.");
 *} else {
 *DoublyLinkedListNode current = tail;
 *while (current != null
                    * && current.getPrevious() != null
                    * && current.getData() != data) {
 *current = current.getPrevious();
 *}
 *if (current != null && current.getData() == data) {
 *T result = (T) current.getData();
 *if (current.getPrevious() != null) {
 *current.getPrevious().setNext(current.getNext());
 *} else {
 *head = current.getNext();
 *}
 *if (current.getNext() != null) {
 *current.getNext().setPrevious(current.getPrevious());
 *} else {
 *tail = current.getPrevious();
 *}
 *size--;
 *return result;
 *} else {
 *throw new NoSuchElementException(
                        * "Cannot find this data in the list.");
 *}
 *}
 *}
 *
         *     /**
  *      * Returns an array representation of the linked list.
  *      *
  *      * Must be O(n) for all cases.
  *      *
  *      * @return an array of length size holding all of the objects in the
  *      * list in the same order
  *      */
         *

    public Object[] toArray() {
 *Object[] nodeArray = new String[size];
 *DoublyLinkedListNode current = head;
 *int index = 0;
 *while (index < size) {
 *nodeArray[index] = current.getData();
 *current = current.getNext();
 *index++;
 *}
 *return nodeArray;
 *
 *}
 *
         *     /**
  *      * Returns the head node of the list.
  *      *
  *      * For grading purposes only. You shouldn't need to use this method since
  *      * you have direct access to the variable.
  *      *
  *      * @return the node at the head of the list
  *      */
         *

    public DoublyLinkedListNode<T> getHead() {
 *         // DO NOT MODIFY!
 *return head;
 *}
 *
         *     /**
  *      * Returns the tail node of the list.
  *      *
  *      * For grading purposes only. You shouldn't need to use this method since
  *      * you have direct access to the variable.
  *      *
  *      * @return the node at the tail of the list
  *      */
         *

    public DoublyLinkedListNode<T> getTail() {
 *         // DO NOT MODIFY!
 *return tail;
 *}
 *
         *     /**
  *      * Returns the size of the list.
  *      *
  *      * For grading purposes only. You shouldn't need to use this method since
  *      * you have direct access to the variable.
  *      *
  *      * @return the size of the list
  *      */
         *

    public int size() {
 *         // DO NOT MODIFY!
 *return size;
 *}
 *
}
 */


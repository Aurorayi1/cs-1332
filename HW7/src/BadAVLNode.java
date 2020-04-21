public class BadAVLNode<T extends Comparable<? super T>> extends AVLNode<T> {

    /**
     * Create an AVLNode with the given data.
     *
     * @param data the data stored in the new node
     */
    public BadAVLNode(T data) {
        super(data);
    }

    /**
     * Throws an exception if a BadAVLNode's data is requested
     *
     * @return the data
     */
    public T getData() {
        throw new java.lang.IllegalStateException("Your AVL accessed"
                + " a node it didn't have to");
    }

    /**
     * Throws an exception if a BadAVLNode's left child is requested
     *
     * @return the left child
     */
    public AVLNode<T> getLeft() {
        throw new java.lang.IllegalStateException("Your AVL accessed"
                + " a node it didn't have to");
    }

    /**
     * Throws an exception if a BadAVLNode's right child is requested
     *
     * @return the right child
     */
    public AVLNode<T> getRight() {
        throw new java.lang.IllegalStateException("Your AVL accessed"
                + " a node it didn't have to");
    }
}
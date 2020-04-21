import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Your implementation of an AVL.
 *
 * @author Wenye Yi
 * @version 1.0

 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null!");
        } else {
            Iterator<T> iterator = data.iterator();
            while (iterator.hasNext()) {
                add(iterator.next());
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        }
        root = addHelper(root, data);
    }

    /**
     * recursive helper method to add, it takes in the current node and the data
     * to insert into the tree
     *
     * @param curr the node the data is being compared against
     * @param data the data to be inserted into the tree
     * @return returns the new node of the tree that was just inserted
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return updateNode(new AVLNode<T>(data));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return updateNode(curr);
    }

    /**
     * private method to update the height and balance factor of the node
     *
     * @param curr takes in the node to update
     * @return curr the current node
     */
    private AVLNode<T> updateNode(AVLNode<T> curr) {
        if (curr.getLeft() != null && curr.getRight() != null) {
            curr.setHeight(Math.max(curr.getLeft().getHeight(),
                    curr.getRight().getHeight()) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight()
                    - curr.getRight().getHeight());
        } else if (curr.getLeft() == null && curr.getRight() != null) {
            curr.setHeight(curr.getRight().getHeight() + 1);
            curr.setBalanceFactor((-1) - curr.getRight().getHeight());
        } else if (curr.getLeft() != null && curr.getRight() == null) {
            curr.setHeight(curr.getLeft().getHeight() + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
        } else {
            curr.setHeight(0);
            curr.setBalanceFactor(0);
        }
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft() != null
                    && curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            curr = rotateRight(curr);
        }
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight() != null
                    && curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
            }
            return rotateLeft(curr);
        }
        return curr;
    }

    /**
     * a private helper method to do a left rotation
     * @param current the node that needs to be rotated
     * @return returns the node that is now in its place
     */
    private AVLNode<T> rotateLeft(AVLNode<T> current) {
        AVLNode<T> newParent = current.getRight();
        current.setRight(newParent.getLeft());
        newParent.setLeft(current);
        updateNode(current);
        return updateNode(newParent);
    }

    /**
     * a private helper method to do a right rotation
     * @param current the node that needs to be rotated
     * @return returns the node that is now in its place
     */
    private AVLNode<T> rotateRight(AVLNode<T> current) {
        AVLNode<T> newParent = current.getLeft();
        current.setLeft(newParent.getRight());
        newParent.setRight(current);
        updateNode(current);
        return updateNode(newParent);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                    "Cannot remove null data!");
        } else {
            AVLNode<T> tmp = new AVLNode<T>(null);
            root = removeHelper(root, data, tmp);
            return tmp.getData();
        }
    }

    /**
     * helper method to recursively remove a node from the tree.
     * @param curr the current node that the data is being compared to
     * @param data the data that is being removed
     * @param tmp the temp that stores the data that needs to be removed
     * @return returns the balanced node
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> tmp) {
        if (curr == null) {
            throw new java.util.NoSuchElementException(
                    "Data is not in the tree!");
        } else if (curr.getData().compareTo(data) == 0) {
            tmp.setData(curr.getData());
            size--;
            if (curr.getLeft() == null & curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return updateNode(curr.getRight());
            } else if (curr.getRight() == null) {
                return updateNode(curr.getLeft());
            } else {
                AVLNode<T> temp = new AVLNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), temp));
                curr.setData(temp.getData());
            }
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, tmp));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, tmp));
        }
        return updateNode(curr);
    }

    /**
     * helper method to recursively remove a node from the tree.
     * It replaces the node with its successor and then balance the tree
     * out after the removal
     * @param curr the current node is being compared to
     * @param data the data that is being removed
     * @return returns the balanced node
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> data) {
        if (curr.getLeft() == null) {
            data.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), data));
        }
        return updateNode(curr);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null || !contains(data)) {
            throw new NoSuchElementException("Element does not exist");
        } else {
            return getHelper(data, root);
        }
    }

    /**
     * a recursive helper function that searches for a data in the tree and
     * then returns the curr if found
     * @param data the data it searches for
     * @param current the current node the data is being compared to
     * @return returns the node if found in the tree
     */
    private T getHelper(T data, AVLNode<T> current) {

        if (current == null) {
            return null;
        }
        int compare = data.compareTo(current.getData());

        if (compare > 0) {
            return getHelper(data, current.getRight());
        } else if (compare < 0) {
            return getHelper(data, current.getLeft());
        } else {
            return current.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            return containsHelper(root, data);
        }
    }

    /**
     * recursive helper function for contains, it returns true if a data is
     * in the tree, false if it isn't
     * @param current the current node the data is being compared to
     * @param data the data that is being searched for
     * @return returns true if data is found, false if it isn't
     */
    private boolean containsHelper(AVLNode<T> current, T data) {
        if (current == null) {
            return false;
        }
        int compare = data.compareTo(current.getData());
        if (compare < 0) {
            return containsHelper(current.getLeft(), data);
        } else if (compare > 0) {
            return containsHelper(current.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null!");
        }
        AVLNode<T> curr = pre(root, null, data);
        if (curr == null) {
            throw new java.util.NoSuchElementException(
                    "Data is not in the tree");
        }
        return curr.getData();
    }

    /**
     * recursive helper function for predecessor, it returns the predecessor
     * of the data that passed in
     * @param curr the current node in the tree
     * @param pre the predecessor that will be returned
     * @param data the data that is being compared to
     * @return returns the predecessor of the data
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private AVLNode<T> pre(AVLNode<T> curr, AVLNode<T> pre, T data) {
        if (curr == null) {
            return null;
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                return pre(curr.getLeft(), pre, data);
            } else if (data.compareTo(curr.getData()) > 0) {
                pre = curr;
                return pre(curr.getRight(), pre, data);
            } else {
                if (curr.getLeft() != null) {
                    return preHelp(curr.getLeft());
                }
            }
            return pre;
        }
    }

    /**
     * recursive helper function for predecessor, it returns the predecessor
     * of the data that passed in
     * @param curr the current node that is being compared to
     * @return returns the predecessor of the data
     */
    private AVLNode<T> preHelp(AVLNode<T> curr) {
        if (curr.getRight() != null) {
            return preHelp(curr.getRight());
        } else {
            return curr;
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new java.lang.IllegalArgumentException("K is invalid!");
        }
        List<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        return kHelper(list, root, k);
    }

    /**
     *
     * helper method to recursively find the smallest nodes
     * @param list the list that is contained the smallest nodes
     * @param curr the current node that is being compared
     * @param k the data that is the smallest
     * @return returns the list of smallest nodes
     */
    private List<T> kHelper(List<T> list, AVLNode<T> curr, int k) {
        if (list.size() < k && curr.getLeft() != null) {
            kHelper(list, curr.getLeft(), k);
        }
        if (list.size() < k && curr != null) {
            list.add(curr.getData());
        }
        if (list.size() < k && curr.getRight() != null) {
            kHelper(list, curr.getRight(), k);
        }
        return list;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

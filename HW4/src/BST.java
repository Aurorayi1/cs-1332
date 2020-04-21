import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.ArrayList;

/**
 * Your implementation of a BST.
 *
 * @author Wenye Yi
 * @version 1.0
 *
 * Collaborators: I worked on this assignment alone.
 *
 * Resources: I only used course materials.
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data.");
        } else {
            size = 0;
            // for each loop
            for (T t : data) {
                if (t == null) {
                    throw new IllegalArgumentException("Data cannot be null.");
                }
                add(t);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(data, root);
        }

    }

    /**
     * finds the correct location for a node and adds it to
     * the tree recursively by using CompareTo
     *
     * @param data the data that needs to be added
     * @param current current node for recursive purposes
     */
    private void add(T data, BSTNode<T> current) {
        int comparison = data.compareTo(current.getData());
        if (comparison > 0) {
            BSTNode<T> newNode = new BSTNode<>(data);
            if (current.getRight() == null) {
                current.setRight(newNode);
                size++;
            } else {
                add(data, current.getRight());
            }
        } else if (comparison < 0) {
            BSTNode<T> newNode = new BSTNode<>(data);
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                size++;
            } else {
                add(data, current.getLeft());
            }
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        } else if (root == null) {
            throw new NoSuchElementException("Cannot find the data");
        } else {
            T result;
            if (root.getData().compareTo(data) == 0) {
                result = root.getData();
                T tem;
                if (root.getLeft() != null) {
                    tem = getPredecessor(root.getLeft());
                    root.setData(tem);
                    removeHelper(root, root.getLeft(), tem);
                } else {
                    root = root.getRight();
                }
            } else {
                if (root.getData().compareTo(data) > 0) {
                    result = removeHelper(root, root.getLeft(), data);
                } else {
                    result = removeHelper(root, root.getRight(), data);
                }
            }
            size--;
            return result;
        }
    }

    /**
     * helper method to remove a node from the tree recursively
     *
     * @param parent the parent of the current node
     * @param current the current node
     * @param data the data that needs to be removed
     * @return returns the node to be removed
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T removeHelper(BSTNode<T> parent, BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("Data does not exist.");
        } else if (data.compareTo(current.getData()) > 0) {
            return removeHelper(current, current.getRight(), data);
        } else if (data.compareTo(current.getData()) < 0) {
            return removeHelper(current, current.getLeft(), data);
        } else {
            T target = current.getData();
            if (current.getLeft() != null
                    && current.getRight() != null) {
                BSTNode<T> predecessor = new BSTNode<>(getPredecessor(
                        current.getLeft()));
                current.setData(predecessor.getData());
                removeHelper(current, current.getLeft(),
                        predecessor.getData());
            } else if (current.getRight() == null
                    && current.getLeft() == null) {
                if (parent.getLeft() == current) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            } else if (current.getRight() == null) {
                current.setData(current.getLeft().getData());
                current.setRight(current.getLeft().getRight());
                current.setLeft(current.getLeft().getLeft());
            } else if (current.getLeft() == null) {
                current.setData(current.getRight().getData());
                current.setLeft(current.getRight().getLeft());
                current.setRight(current.getRight().getRight());
            }
            return target;
        }
    }

    /**
     * method that finds the predecessors for the node
     *
     * @param current the current node
     * @return returns the predecessor
     */
    private T getPredecessor(BSTNode<T> current) {
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getData();
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            throw new NoSuchElementException("Element does not exist.");
        }
        T findData = getHelper(data, root);
        if (findData != null) {
            return findData;
        } else {
            throw new NoSuchElementException("Element does not exist.");
        }
    }

    /**
     * helper method for find to recursively find the node in the tree
     * @param data to be found
     * @param current the current node in the search
     * @return the node found
     */

    private T getHelper(T data, BSTNode<T> current) {
        int compare = data.compareTo(current.getData());
        if (compare < 0) {
            if (current.getLeft() == null) {
                return null;
            } else {
                return getHelper(data, current.getLeft());
            }
        } else if (compare > 0) {
            if (current.getRight() == null) {
                return null;
            } else {
                return getHelper(data, current.getRight());
            }
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
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else if (root == null) {
            return false;
        } else {
            return (getHelper(data, root) != null);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root != null) {
            list.add(root.getData());
            preorderHelper(list, root);
        }
        return list;
    }

    /**
     * helper method to sort the elements in postorder traversal
     * @param list takes in a list that is sorted in preorder
     * @param current the current node
     */
    private void preorderHelper(ArrayList<T> list, BSTNode<T> current) {
        // look at data, left, right
        if (current.getLeft() != null) {
            list.add(current.getLeft().getData());
            preorderHelper(list, current.getLeft());
        }
        if (current.getRight() != null) {
            list.add(current.getRight().getData());
            preorderHelper(list, current.getRight());
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root != null) {
            inorderHelper(list, root);
        }
        return list;
    }

    /**
     * helper method to sort the tree inorder recursively
     * @param list list of elements in the tree sorted inorder
     * @param current the current node being sorted
     */
    private void inorderHelper(ArrayList<T> list, BSTNode<T> current) {
        if (current.getLeft() != null) {
            inorderHelper(list, current.getLeft());
        }
        list.add(current.getData());
        if (current.getRight() != null) {
            inorderHelper(list, current.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root != null) {
            postorderHelper(list, root);
        }
        return list;
    }

    /**
     * helper method to sort the tree in posorder recursively
     * @param list list of elements sorted in posorder
     * @param current current node that is being sorted
     */
    private void postorderHelper(ArrayList<T> list, BSTNode<T> current) {
        if (current.getLeft() != null) {
            postorderHelper(list, current.getLeft());
        }
        if (current.getRight() != null) {
            postorderHelper(list, current.getRight());
        }
        list.add(current.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> list = new ArrayList<T>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            levelorderHelper(list, queue);
        }
        return list;
    }

    /**
     * helper method to recursively sort elements in level order
     * @param list a list of elements in levelorder
     * @param queue a queue of nodes to add to the list
     */

    private void levelorderHelper(ArrayList<T> list, Queue<BSTNode<T>> queue) {
        // poll -- returns the element at the front of the container
        BSTNode<T> current = queue.poll();
        if (current != null) {
            list.add(current.getData());
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
            levelorderHelper(list, queue);
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return getHeight(root, 0);
        }
    }

    /**
     * recursively finds the height of the tree
     * @param current the current node being compared
     * @param i an int that represents the height
     * @return the height of the tree
     */
    private int getHeight(BSTNode<T> current, int i) {
        int right = i;
        int left = i;
        if (current.getRight() != null) {
            right = getHeight(current.getRight(), i + 1);
        }
        if (current.getLeft() != null) {
            left = getHeight(current.getLeft(), i + 1);
        }
        if (right > left) {
            return right;
        } else {
            return left;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        LinkedList<T> list = new LinkedList<T>();
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException(
                    "Cannot find path between null data.");
        } else if (data1 != null && data2 != null) {
            try {
                return helperOne(data1, data2, root, list);
            } catch (Exception e) {
                throw new NoSuchElementException(
                        "Cannot find data in the tree");
            }
        } else {
            throw new NoSuchElementException(
                    "Cannot find data in the tree.");
        }

    }

    /**
     * helperOne method to sort the elements
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @param temRoot the temporary root
     * @param list the list that stores the data path
     * @return the list
     */
    private LinkedList<T> helperOne(T data1, T data2, BSTNode<T> temRoot,
                                    LinkedList<T> list) {
        int comparison1 = data1.compareTo(temRoot.getData());
        int comparison2 = data2.compareTo(temRoot.getData());
        // if root is between data1 and data2
        if (comparison1 <= 0 && comparison2 >= 0
                || comparison1 >= 0 && comparison2 <= 0) {
            list.addFirst(temRoot.getData());
            helperTwo(data1, temRoot, list);
            helperThree(data2, temRoot, list);
            return list;
        } else if (comparison1 > 0 && comparison2 > 0) {
            helperOne(data1, data2, temRoot.getRight(), list);
            return list;
        } else {
            helperOne(data1, data2, temRoot.getLeft(), list);
            return list;
        }
    }

    /**
     * helperOne method to sort the elements
     * @param data1 the data to start the path from
     * @param temRoot the temporary root
     * @param list the list that stores the data path
     */
    private void helperTwo(T data1, BSTNode<T> temRoot, LinkedList<T> list) {
        int comparison = data1.compareTo(temRoot.getData());
        if (comparison < 0) {
            list.addFirst(temRoot.getLeft().getData());
            helperTwo(data1, temRoot.getLeft(), list);
        } else if (comparison > 0) {
            list.addFirst((temRoot.getRight().getData()));
            helperTwo(data1, temRoot.getRight(), list);
        } else {
            return;
        }
    }

    /**
     * helperOne method to sort the elements
     * @param data2 the data to start the path from
     * @param temRoot the temporary root
     * @param list the list that stores the data path
     */
    private void helperThree(T data2, BSTNode<T> temRoot, LinkedList<T> list) {
        int comparison = data2.compareTo(temRoot.getData());
        if (comparison > 0) {
            list.addLast(temRoot.getRight().getData());
            helperThree(data2, temRoot.getRight(), list);
        } else if (comparison < 0) {
            list.addLast((temRoot.getLeft().getData()));
            helperThree(data2, temRoot.getLeft(), list);
        } else {
            return;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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

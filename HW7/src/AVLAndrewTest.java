import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AVLAndrewTest {

    private static final int TIMEOUT = 200;
    private AVL<Integer> avl;

    @Before
    public void setup() {
        avl = new AVL<>();
    }

    @Test(expected = IllegalArgumentException.class)
    // Construction from null collection throws exception
    public void testIllegalConstructor1() {
        avl = new AVL<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    // Construction from non-null collection with null elements
    // throws exception
    public void testIllegalConstructor2() {
        ArrayList<Integer> badList = new ArrayList<Integer>();
        badList.add(1);
        badList.add(null);
        badList.add(3);
        avl = new AVL<Integer>(badList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalAdd() {
        avl.add(null);
    }

    // tests single left rotation
    @Test(timeout = TIMEOUT)
    public void testAddSingleLeftRotation() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(1);
        addList.add(2);
        addList.add(3);
        for (Integer num: addList) {
            avl.add(num);
        }
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }

    // tests single right rotation
    @Test(timeout = TIMEOUT)
    public void testAddSingleRightRotation() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(3);
        addList.add(2);
        addList.add(1);
        for (Integer num: addList) {
            avl.add(num);
        }
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }

    // tests double rotation in order right-left
    @Test(timeout =  TIMEOUT)
    public void testAddRightLeftRotation() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(1);
        addList.add(3);
        addList.add(2);
        for (Integer num: addList) {
            avl.add(num);
        }
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }

    // tests double rotation in order left-right
    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotation() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(3);
        addList.add(1);
        addList.add(2);
        for (Integer num: addList) {
            avl.add(num);
        }
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }

    // tests removing a node with no children
    // tests remove not triggering rotation
    @Test(timeout = TIMEOUT)
    public void testRemoveNoRotationNoChild() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(9);
        addList.add(5);
        addList.add(13);
        addList.add(3);
        addList.add(7);
        addList.add(11);
        addList.add(15);
        for (Integer num: addList) {
            avl.add(num);
        }
        assertEquals((Integer) 3, avl.remove(3));
        assertEquals((Integer) 15, avl.remove(15));
        assertEquals(5, avl.size());
        assertEquals((Integer) 9, avl.getRoot().getData());
        assertEquals((Integer) 5, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 13, avl.getRoot().getRight().getData());
        assertEquals((Integer) 7, avl.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 11,
                avl.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 1,
                (Integer) avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 1,
                (Integer) avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getLeft().getRight().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getRight().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());
        assertEquals(-1, avl.getRoot().getLeft().getBalanceFactor());
        assertEquals(1, avl.getRoot().getRight().getBalanceFactor());
    }

    // tests remove triggering single left rotation
    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRotationNoChild() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(1);
        addList.add(0);
        addList.add(2);
        addList.add(3);
        for (Integer num: addList) {
            avl.add(num);
        }
        avl.remove(0);
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getRight().getBalanceFactor());
    }

    // tests remove triggering single right rotation
    @Test(timeout = TIMEOUT)
    public void testRemoveRightRotationNoChild() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(3);
        addList.add(2);
        addList.add(4);
        addList.add(1);
        for (Integer num: addList) {
            avl.add(num);
        }
        avl.remove(4);
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0,
                (Integer) avl.getRoot().getRight().getBalanceFactor());
    }

    // tests remove triggering double right-left rotation
    @Test(timeout = TIMEOUT)
    public void testRemoveRightLeftRotationNoChild() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(1);
        addList.add(0);
        addList.add(3);
        addList.add(2);
        for (Integer num: addList) {
            avl.add(num);
        }
        avl.remove(0);
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }

    // tests remove triggering double left-right rotation
    @Test(timeout = TIMEOUT)
    public void testRemoveLeftRightRotationNoChild() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(3);
        addList.add(1);
        addList.add(4);
        addList.add(2);
        for (Integer num: addList) {
            avl.add(num);
        }
        avl.remove(4);
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, (Integer) avl.getRoot().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getLeft().getData());
        assertEquals((Integer) 3, (Integer) avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, (Integer) avl.getRoot().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getHeight());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getHeight());
        assertEquals((Integer) 0, (Integer) avl.getRoot().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 0, (Integer)
                avl.getRoot().getRight().getBalanceFactor());
    }


    // tests right rotation following removal of node with single child
    @Test(timeout = TIMEOUT)
    public void testRemoveSingleRotateRight() {
        avl.add(5);
        avl.add(3);
        avl.add(6);
        avl.add(2);
        avl.add(4);
        avl.add(7);
        avl.add(1);
        avl.remove(6);
        assertEquals(6, avl.size());
        assertEquals((Integer) 3, avl.getRoot().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 5, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                avl.getRoot().getRight().getRight().getData());
    }

    // tests left rotation following removal of node with single child
    @Test(timeout = TIMEOUT)
    public void testRemoveSingleRotateLeft() {
        avl.add(3);
        avl.add(2);
        avl.add(5);
        avl.add(1);
        avl.add(4);
        avl.add(6);
        avl.add(7);
        avl.remove(2);
        assertEquals(6, avl.size());
        assertEquals((Integer) 5, avl.getRoot().getData());
        assertEquals((Integer) 3, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 6, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 7,
                avl.getRoot().getRight().getRight().getData());
    }

    // tests LR double rotation following single-child node removal
    @Test(timeout = TIMEOUT)
    public void testRemoveSingleRotateRightLeft() {
        avl.add(3);
        avl.add(2);
        avl.add(6);
        avl.add(1);
        avl.add(5);
        avl.add(7);
        avl.add(4);
        avl.remove(2);
        assertEquals(6, avl.size());
        assertEquals((Integer) 5, avl.getRoot().getData());
        assertEquals((Integer) 3, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 6, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 7,
                avl.getRoot().getRight().getRight().getData());
    }

    // tests right-left rotation following removal of single-child node
    @Test(timeout = TIMEOUT)
    public void testRemoveSingleRotateLeftRight() {
        avl.add(5);
        avl.add(2);
        avl.add(6);
        avl.add(1);
        avl.add(3);
        avl.add(7);
        avl.add(4);
        avl.remove(6);
        assertEquals(6, avl.size());
        assertEquals((Integer) 3, avl.getRoot().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 5, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                avl.getRoot().getRight().getRight().getData());
    }

    // tests left rotation following removal of node with two children
    @Test(timeout = TIMEOUT)
    public void testRemoveTwoRotateLeft() {
        avl.add(2);
        avl.add(1);
        avl.add(4);
        avl.add(3);
        avl.add(5);
        avl.add(6);
        avl.remove(2);
        assertEquals(5, avl.size());
        assertEquals((Integer) 4, avl.getRoot().getData());
        assertEquals((Integer) 3, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 5, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 6,
                avl.getRoot().getRight().getRight().getData());
    }

    // tests right rotation following removal of node with two children
    @Test(timeout = TIMEOUT)
    public void testRemoveTwoRotateRight() {
        avl.add(3);
        avl.add(2);
        avl.add(4);
        avl.add(1);
        avl.remove(3);
        assertEquals(3, avl.size());
        assertEquals((Integer) 2, avl.getRoot().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getRight().getData());
    }

    // tests left-right rotation following removal of node with two children
    @Test(timeout = TIMEOUT)
    public void testRemoveTwoRotateLeftRight() {
        avl.add(5);
        avl.add(2);
        avl.add(7);
        avl.add(1);
        avl.add(3);
        avl.add(6);
        avl.add(4);
        avl.remove(5);
        assertEquals(6, avl.size());
        assertEquals((Integer) 3, avl.getRoot().getData());
        assertEquals((Integer) 2, avl.getRoot().getLeft().getData());
        assertEquals((Integer) 6, avl.getRoot().getRight().getData());
        assertEquals((Integer) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 4, avl.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 7,
                avl.getRoot().getRight().getRight().getData());
    }

    // For the get method, test that the proper route is taken
    // There is a definitive most efficient route
    @Test(timeout = TIMEOUT)
    public void testGetEfficient() {
        ArrayList<Integer> okayList = new ArrayList<Integer>();
        okayList.add(9);
        okayList.add(5);
        okayList.add(13);
        okayList.add(3);
        okayList.add(7);
        okayList.add(11);
        okayList.add(15);
        //               9
        //             /   \
        //            5     13
        //           / \   /  \
        //          3   7 11   15
        avl = new AVL<Integer>(okayList);
        assertEquals((Integer) 9, (Integer) avl.get(9));
        avl.getRoot().setRight(new BadAVLNode<Integer>(13));
        //               9
        //             /   \
        //            5     B
        //           / \   /  \
        //          3   7 11   15
        assertEquals((Integer) 5, avl.get(5));
        avl.getRoot().getLeft().setLeft(new BadAVLNode<Integer>(3));
        //               9
        //             /   \
        //            5     B
        //           / \   /  \
        //          B   7 11   15
        assertEquals((Integer) 7, avl.get(7));
    }


    // For the kSmallest method, test that not all data is touched
    // Doing so is inefficient
    // in the test, this should trigger a timeout exception
    @Test(timeout = TIMEOUT)
    public void testKSmallestEfficient() {
        ArrayList<Integer> okayList = new ArrayList<Integer>();
        okayList.add(1);
        okayList.add(2);
        okayList.add(3);
        okayList.add(4);
        //          2
        //         / \
        //        1   3
        //             \
        //              4
        avl = new AVL(okayList);
        Integer[] expected = new Integer[2];
        expected[0] = 1;
        expected[1] = 2;
        List<Integer> smallest = avl.kSmallest(2);
        assertEquals(expected[0], smallest.get(0));
        assertEquals(expected[1], smallest.get(1));
        avl.getRoot().setRight(new BadAVLNode<Integer>(14));
        //          2
        //         / \
        //        1   B
        //             \
        //              4
        smallest = avl.kSmallest(2);
        assertEquals(expected[0], smallest.get(0));
        assertEquals(expected[1], smallest.get(1));

        //trying another tree
        okayList = new ArrayList<Integer>();
        okayList.add(4);
        okayList.add(2);
        okayList.add(6);
        okayList.add(1);
        okayList.add(3);
        okayList.add(5);
        okayList.add(7);
        avl = new AVL(okayList);
        //          4
        //       /    \
        //      2       6
        //     / \     / \
        //    1   3   5   7
        expected = new Integer[6];
        expected[0] = 1;
        expected[1] = 2;
        expected[2] = 3;
        expected[3] = 4;
        expected[4] = 5;
        expected[5] = 6;
        smallest = avl.kSmallest(6);
        assertEquals(expected[0], smallest.get(0));
        assertEquals(expected[1], smallest.get(1));
        assertEquals(expected[2], smallest.get(2));
        assertEquals(expected[3], smallest.get(3));
        assertEquals(expected[4], smallest.get(4));
        assertEquals(expected[5], smallest.get(5));
        avl.getRoot().getRight().setRight(new BadAVLNode<Integer>(14));
        //          4
        //       /    \
        //      2       6
        //     / \     / \
        //    1   3   5   B
        smallest = avl.kSmallest(6);
        assertEquals(expected[0], smallest.get(0));
        assertEquals(expected[1], smallest.get(1));
        assertEquals(expected[2], smallest.get(2));
        assertEquals(expected[3], smallest.get(3));
        assertEquals(expected[4], smallest.get(4));
        assertEquals(expected[5], smallest.get(5));
        avl.getRoot().setRight(new BadAVLNode<Integer>(14));
        //          4
        //       /    \
        //      2       B
        //     / \     / \
        //    1   3   5   B
        smallest = avl.kSmallest(4);
        assertEquals(expected[0], smallest.get(0));
        assertEquals(expected[1], smallest.get(1));
        assertEquals(expected[2], smallest.get(2));
        assertEquals(expected[3], smallest.get(3));
    }

    // Make sure the height method isn't recursive
    @Test(timeout = TIMEOUT)
    public void testHeightEfficient() {
        ArrayList<Integer> addList = new ArrayList<Integer>();
        addList.add(1);
        addList.add(2);
        addList.add(3);
        addList.add(4);
        avl = new AVL<Integer>(addList);
        //      2
        //     / \
        //    1   3
        //         \
        //          4
        assertEquals(2, avl.height());
        avl.getRoot().setRight(new BadAVLNode<Integer>(3));
        avl.getRoot().setLeft(new BadAVLNode<Integer>(1));
        //      2
        //     / \
        //    B   B
        //         \
        //          4
        assertEquals(2, avl.height());
    }
}
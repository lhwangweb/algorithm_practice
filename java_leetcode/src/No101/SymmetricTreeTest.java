package No101;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymmetricTreeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution a = new Solution();

        // leaf
        TreeNode leaf_a1 = new TreeNode(1);
        TreeNode leaf_b1 = new TreeNode(1);
        TreeNode leaf_a2 = new TreeNode(2);
        TreeNode leaf_b2 = new TreeNode(2);
        TreeNode leaf_a3 = new TreeNode(3);
        TreeNode leaf_b3 = new TreeNode(3);
        TreeNode leaf_a4 = new TreeNode(4);
        TreeNode leaf_b4 = new TreeNode(4);
        TreeNode leaf_a5 = new TreeNode(5);
        TreeNode leaf_b5 = new TreeNode(5);
        TreeNode leaf_a6 = new TreeNode(6);
        TreeNode leaf_b6 = new TreeNode(6);
        TreeNode leaf_a7 = new TreeNode(7);
        TreeNode leaf_b7 = new TreeNode(7);
        TreeNode leaf_a8 = new TreeNode(8);
        TreeNode leaf_b8 = new TreeNode(8);

        // case 1
        System.out.println("res1");

        TreeNode case1 = new TreeNode(7, leaf_a6, leaf_b6);
        boolean res1 = a.isSymmetric(case1);
        assertEquals(res1, true);
        System.out.println(res1);

        // case 2
        System.out.println("res2");

        TreeNode case2 = new TreeNode(8, leaf_a6, leaf_a5);
        boolean res2 = a.isSymmetric(case2);
        assertEquals(res2, false);
        System.out.println(res2);

        // case 3
        System.out.println("res3");

        TreeNode case3 = new TreeNode(8, leaf_b5, null);
        boolean res3 = a.isSymmetric(case3);
        assertEquals(res3, false);
        System.out.println(res3);

        case3 = new TreeNode(8, null, leaf_a4);
        res3 = a.isSymmetric(case3);
        assertEquals(res3, false);
        System.out.println(res3);

        case3 = new TreeNode(8, null, null);
        res3 = a.isSymmetric(case3);
        assertEquals(true, res3);
        System.out.println(res3);

        // case 4
        System.out.println("res4");

        //       8
        //     /   \
        //    1     1
        //   / \   / \
        //  4   4 2   2
        TreeNode n1 = new TreeNode(1, leaf_a4, leaf_b4);
        TreeNode m1 = new TreeNode(1, leaf_a2, leaf_b2);
        TreeNode case4 = new TreeNode(8, n1, m1);
        boolean res4 = a.isSymmetric(case4);
        assertEquals(false, res4);
        System.out.println(res4);

        //       8
        //     /   \
        //    1     1
        //   / \   / \
        //  3   3 3   3
        n1 = new TreeNode(1, leaf_a3, leaf_b3);
        m1 = new TreeNode(1, leaf_a3, leaf_b3);
        case4 = new TreeNode(8, n1, m1);
        res4 = a.isSymmetric(case4);
        assertEquals(true, res4);
        System.out.println(res4);

        //       8
        //     /   \
        //    1     1
        //   / \   / \
        //  2   3 2   3
        n1 = new TreeNode(1, leaf_a2, leaf_b3);
        m1 = new TreeNode(1, leaf_b2, leaf_b3);
        case4 = new TreeNode(8, n1, m1);
        res4 = a.isSymmetric(case4);
        assertEquals(false, res4);
        System.out.println(res4);

        //       8
        //     /   \
        //    1     1
        //   / \   / \
        //  3   2 2   3
        n1 = new TreeNode(1, leaf_b3, leaf_a2);
        m1 = new TreeNode(1, leaf_b2, leaf_b3);
        case4 = new TreeNode(8, n1, m1);
        res4 = a.isSymmetric(case4);
        assertEquals(true, res4);
        System.out.println(res4);

        //       8
        //     /   \
        //    1     1
        //   / \   /
        //  2   3 2
        n1 = new TreeNode(1, leaf_a2, leaf_b3);
        m1 = new TreeNode(1, leaf_b2, null);
        case4 = new TreeNode(8, n1, m1);
        res4 = a.isSymmetric(case4);
        assertEquals(false, res4);
        System.out.println(res4);

        // case 5
        //           11
        //       /       \
        //      7         7
        //     / \       /  \
        //   10   10    71   71
        //  /  \ /  \  /  \ /  \
        // 8   8 7   7 4  4 5   5
        //
        TreeNode nnn = new TreeNode(10, leaf_a8, leaf_b8);
        TreeNode mmm = new TreeNode(10, leaf_a7, leaf_b7);

        TreeNode nn = new TreeNode(71, leaf_a4, leaf_b4);
        TreeNode mm = new TreeNode(71, leaf_a5, leaf_b5);

        TreeNode n_left = new TreeNode(7, nnn, mmm);
        TreeNode m_right = new TreeNode(7, nn, mm);

        TreeNode case5 = new TreeNode(11, n_left, m_right);
        boolean res5 = a.isSymmetric(case5);
        assertEquals(false, res5);
        System.out.println(res5);

        //           12
        //       /       \
        //      7         7
        //     / \       /  \
        //   10   10    71   71
        //  /  \ /  \  /  \ /  \
        // 7   8 8   7 5  4 4   5
        nnn = new TreeNode(71, new TreeNode(5), new TreeNode(4));
        mmm = new TreeNode(71, new TreeNode(4), new TreeNode(5));

        nn = new TreeNode(10, new TreeNode(7), new TreeNode(8));
        mm = new TreeNode(10, new TreeNode(8), new TreeNode(7));

        n_left = new TreeNode(7, nn, mm);
        m_right = new TreeNode(7, nnn, mmm);

        case5 = new TreeNode(12, n_left, m_right);
        res5 = a.isSymmetric(case5);
        assertEquals(false, res5);
        System.out.println(res5);

        //           12
        //       /       \
        //      7         7
        //     / \       /  \
        //   10   10    71   71
        //  /  \ /  \  /  \ /  \
        // 1   2 3   4 4  3 2   1
        nnn = new TreeNode(71, new TreeNode(4), new TreeNode(3));
        mmm = new TreeNode(71, new TreeNode(2), new TreeNode(1));

        nn = new TreeNode(10, new TreeNode(1), new TreeNode(2));
        mm = new TreeNode(10, new TreeNode(3), new TreeNode(4));

        n_left = new TreeNode(7, nn, mm);
        m_right = new TreeNode(7, nnn, mmm);

        case5 = new TreeNode(12, n_left, m_right);
        res5 = a.isSymmetric(case5);
        assertEquals(false, res5);
        System.out.println(res5);

        //           12
        //       /       \
        //      7         7
        //     / \       /  \
        //   10   71    71   10
        //  /  \ /  \  /  \ /  \
        // 1   2 3   4 4  3 2   1
        nnn = new TreeNode(71, new TreeNode(4), new TreeNode(3));
        mmm = new TreeNode(10, new TreeNode(2), new TreeNode(1));

        nn = new TreeNode(10, new TreeNode(1), new TreeNode(2));
        mm = new TreeNode(71, new TreeNode(3), new TreeNode(4));

        n_left = new TreeNode(7, nn, mm);
        m_right = new TreeNode(7, nnn, mmm);

        case5 = new TreeNode(12, n_left, m_right);
        res5 = a.isSymmetric(case5);
        assertEquals(true, res5);
        System.out.println(res5);

        //           12
        //       /       \
        //      7         7
        //     / \       /  \
        //   10   71    71   10
        //  /  \ /  \  /    /  \
        // 1   2 3   4 4    2   1
        nnn = new TreeNode(71, new TreeNode(4), null);
        mmm = new TreeNode(10, new TreeNode(2), new TreeNode(1));

        nn = new TreeNode(10, new TreeNode(1), new TreeNode(2));
        mm = new TreeNode(71, new TreeNode(3), new TreeNode(4));

        n_left = new TreeNode(7, nn, mm);
        m_right = new TreeNode(7, nnn, mmm);

        case5 = new TreeNode(12, n_left, m_right);
        res5 = a.isSymmetric(case5);
        assertEquals(false, res5);
        System.out.println(res5);

        //           12
        //       /       \
        //      7         7
        //     / \       /
        //   10   71    71
        //  /  \ /  \  /  \
        // 1   2 3   4 4   3
        nnn = new TreeNode(71, new TreeNode(4), new TreeNode(3));

        nn = new TreeNode(10, new TreeNode(1), new TreeNode(2));
        mm = new TreeNode(71, new TreeNode(3), new TreeNode(4));

        n_left = new TreeNode(7, nn, mm);
        m_right = new TreeNode(7, nnn, null);

        case5 = new TreeNode(12, n_left, m_right);
        res5 = a.isSymmetric(case5);
        assertEquals(false, res5);
        System.out.println(res5);
    }
}
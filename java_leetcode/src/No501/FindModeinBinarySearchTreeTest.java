package No501;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FindModeinBinarySearchTreeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution test_solution = new Solution();
        TreeNode root;
        int[] res;

        TreeNode b3 = new TreeNode(3);
        TreeNode b7 = new TreeNode(7);
        TreeNode b8 = new TreeNode(8);
        TreeNode b10 = new TreeNode(10);
        TreeNode b5 = new TreeNode(5, b3, b7);
        TreeNode b4 = new TreeNode(4, b8, b10);
        root = new TreeNode(2, b5, b4);
        //      2
        //    /   \
        //   5     4
        //  / \   / \
        // 3   7 8   10

        res = test_solution.findMode_1(root);
        System.out.println(Arrays.toString(res));
        assertArrayEquals(res, new int[] {2, 3, 4, 5, 7, 8, 10});

        TreeNode c5_3 = new TreeNode(5);
        TreeNode c5_2 = new TreeNode(5);
        TreeNode c5_1 = new TreeNode(5);
        TreeNode c4_3 = new TreeNode(4);
        TreeNode c4_2 = new TreeNode(4, c5_3, c5_2);
        TreeNode c4 = new TreeNode(4, c5_1, c4_3);
        root = new TreeNode(2, c4_2, c4);
        //      2
        //    /   \
        //   4     4
        //  / \   / \
        // 4   5 5   5

        res = test_solution.findMode_1(root);
        System.out.println(Arrays.toString(res));
        assertArrayEquals(res, new int[] {4, 5});

        TreeNode d_a = new TreeNode(2);
        TreeNode d_b = new TreeNode(4);
        TreeNode d_c = new TreeNode(6);
        TreeNode d_d = new TreeNode(10);
        TreeNode d_e = new TreeNode(3, d_a, d_b);
        TreeNode d_f = new TreeNode(7, d_c, d_d);
        root = new TreeNode(5, d_e, d_f);
        //      5
        //    /   \
        //   3     7
        //  / \   / \
        // 2   4 6   10

        res = test_solution.findMode_2(root);
        System.out.println(Arrays.toString(res));
        assertArrayEquals(res, new int[] {2,3,4,5,6,7,10});

        TreeNode e_a = new TreeNode(6);
        TreeNode e_b = new TreeNode(6);
        TreeNode e_c = new TreeNode(4);
        TreeNode e_d = new TreeNode(4);
        TreeNode e_4 = new TreeNode(4, e_c, e_d);
        TreeNode e_6 = new TreeNode(6, e_a, e_b);
        root = new TreeNode(5, e_4, e_6);
        //      5
        //    /   \
        //   4     6
        //  / \   / \
        // 4   4 6   6

        res = test_solution.findMode_2(root);
        System.out.println(Arrays.toString(res));
        assertArrayEquals(res, new int[] {4,6});

        //             6
        //        /       \
        //      4           8
        //    /  \       /     \
        //  2     4     8      11
        // / \   / \   / \     / \
        //1   3  4 5   7 8    12 13
    }
}
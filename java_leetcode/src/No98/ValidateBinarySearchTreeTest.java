package No98;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidateBinarySearchTreeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution test_solution = new Solution();
        Boolean res;

        TreeNode c = new TreeNode(3);
        TreeNode b = new TreeNode(2, c, null);
        TreeNode a = new TreeNode(1, null, b);
        //   1
        //    \
        //     2
        //    /
        //   3

        res = test_solution.isValidBST(a);
        System.out.println(res);
        assertEquals(false, res);

        TreeNode a3 = new TreeNode(3);
        TreeNode a1 = new TreeNode(1);
        TreeNode a4 = new TreeNode(4, a3, null );
        TreeNode a2 = new TreeNode(2, a1, a4);

        //   2
        //  / \
        // 1   4
        //    /
        //   3

        res = test_solution.isValidBST(a2);
        System.out.println(res);
        assertEquals(true, res);

        TreeNode b3 = new TreeNode(3);
        TreeNode b7 = new TreeNode(7);
        TreeNode b8 = new TreeNode(8);
        TreeNode b5 = new TreeNode(5, b3, b7);
        TreeNode b4 = new TreeNode(4, b8, null);
        TreeNode b2 = new TreeNode(2, b5, b4);

        //      2
        //    /   \
        //   5     4
        //  / \   /
        // 3   7 8

        res = test_solution.isValidBST(b2);
        System.out.println(res);
        assertEquals(false, res);

        // [5,4,6,null,null,3,7]
        TreeNode c3 = new TreeNode(3);
        TreeNode c7 = new TreeNode(7);
        TreeNode c4 = new TreeNode(4);
        TreeNode c6 = new TreeNode(6, c3, c7);
        TreeNode root = new TreeNode(5, c4, c6);

        //      5
        //    /   \
        //   4     6
        //        / \
        //       3   7

        res = test_solution.isValidBST(root);
        System.out.println(res);
        assertEquals(false, res);


        //           5
        //       /      \
        //      3          6
        //     / \       /  \
        //    1   5     3    7
        //   / \ / \   / \  / \
        //  3  4 5 7  8  9  8  9
    }
}
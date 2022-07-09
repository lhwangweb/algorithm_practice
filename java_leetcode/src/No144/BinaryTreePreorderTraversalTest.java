package No144;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class BinaryTreePreorderTraversalTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution test_solution = new Solution();
        List<Integer> res;

        TreeNode c = new TreeNode(3);
        TreeNode b = new TreeNode(2, c, null);
        TreeNode a = new TreeNode(1, null, b);
        //   1
        //    \
        //     2
        //    /
        //   3

        res = test_solution.preorderTraversal(a);
        System.out.println(Arrays.toString(res.toArray()));
        assertArrayEquals(res.toArray(), new Integer[] {1, 2, 3});

        TreeNode a3 = new TreeNode(3);
        TreeNode a1 = new TreeNode(1);
        TreeNode a4 = new TreeNode(4, a3, null );
        TreeNode a2 = new TreeNode(2, a1, a4);

        //   2
        //  / \
        // 1   4
        //    /
        //   3

        res = test_solution.preorderTraversal(a2);
        System.out.println(Arrays.toString(res.toArray()));
        assertArrayEquals(res.toArray(), new Integer[] {2, 1, 4, 3});

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

        res = test_solution.preorderTraversal(b2);
        System.out.println(Arrays.toString(res.toArray()));
        assertArrayEquals(res.toArray(), new Integer[] {2, 5, 3, 7, 4, 8});
    }
}
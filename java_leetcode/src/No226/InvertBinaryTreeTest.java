package No226;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InvertBinaryTreeTest {

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

        TreeNode c = new TreeNode(3);
        TreeNode b = new TreeNode(2, c, null);
        root = new TreeNode(1, null, b);
        //   1
        //    \
        //     2
        //    /
        //   3
        System.out.println("before");
        test_solution.invertTree(root);
        System.out.println("after");

        TreeNode a3 = new TreeNode(3);
        TreeNode a1 = new TreeNode(1);
        TreeNode a4 = new TreeNode(4, a3, null );
        root = new TreeNode(2, a1, a4);
        //   2
        //  / \
        // 1   4
        //    /
        //   3
        System.out.println("before");
        test_solution.invertTree(root);
        System.out.println("after");

        TreeNode b3 = new TreeNode(3);
        TreeNode b7 = new TreeNode(7);
        TreeNode b8 = new TreeNode(8);
        TreeNode b5 = new TreeNode(5, b3, b7);
        TreeNode b4 = new TreeNode(4, b8, null);
        root = new TreeNode(2, b5, b4);
        //      2
        //    /   \
        //   5     4
        //  / \   /
        // 3   7 8
        System.out.println("before");
        test_solution.invertTree(root);
        System.out.println("after");
    }
}
package No105;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ConstructBinaryTreefromPreorderandInorderTraversalTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution a = new Solution();

        // Case 1
        int[] preorder_1 = new int[]{3, 9, 20, 15, 7};
        int[] inorder_1 = new int[]{9, 3, 15, 20, 7};

        TreeNode node_1 = a.buildTree(preorder_1, inorder_1);

        ArrayList<Integer> preorder_1_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_1_result = new ArrayList<Integer>();

        a.traversal_preorder(node_1, preorder_1_result);
        a.traversal_inorder(node_1, inorder_1_result);

        int[] preorder_1_check = preorder_1_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_1_check = inorder_1_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(preorder_1, preorder_1_check);
        assertArrayEquals(inorder_1, inorder_1_check);

        System.out.println(Arrays.toString(preorder_1_check));
        System.out.println(Arrays.toString(inorder_1_check));

        // Case 2
        int[] preorder_2 = new int[]{10, 9, 7, 3, 6, 2, 8, 5, 4, 1};
        int[] inorder_2 = new int[]{3, 7, 9, 6, 2, 10, 5, 8, 4, 1};

        TreeNode node_2 = a.buildTree(preorder_2, inorder_2);

        ArrayList<Integer> preorder_2_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_2_result = new ArrayList<Integer>();

        a.traversal_preorder(node_2, preorder_2_result);
        a.traversal_inorder(node_2, inorder_2_result);

        int[] preorder_2_check = preorder_2_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_2_check = inorder_2_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(preorder_2, preorder_2_check);
        assertArrayEquals(inorder_2, inorder_2_check);

        System.out.println(Arrays.toString(preorder_2_check));
        System.out.println(Arrays.toString(inorder_2_check));

        // Case 3
        int[] preorder_3 = new int[]{5, 2, 1, 4, 3, 6, 7};
        int[] inorder_3 = new int[]{1, 2, 3, 4, 5, 6, 7};

        TreeNode node_3 = a.buildTree(preorder_3, inorder_3);

        ArrayList<Integer> preorder_3_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_3_result = new ArrayList<Integer>();

        a.traversal_preorder(node_3, preorder_3_result);
        a.traversal_inorder(node_3, inorder_3_result);

        int[] preorder_3_check = preorder_3_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_3_check = inorder_3_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(preorder_3, preorder_3_check);
        assertArrayEquals(inorder_3, inorder_3_check);

        System.out.println(Arrays.toString(preorder_3_check));
        System.out.println(Arrays.toString(inorder_3_check));
    }

}
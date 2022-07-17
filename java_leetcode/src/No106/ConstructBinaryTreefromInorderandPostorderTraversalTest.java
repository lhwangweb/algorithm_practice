package No106;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConstructBinaryTreefromInorderandPostorderTraversalTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_main() {
        Solution a = new Solution();

        int[] postorder_1 = new int[] {9, 15, 7, 20, 3};
        int[] inorder_1 = new int[] {9, 3, 15, 20, 7};

        TreeNode node_1 = a.buildTree(inorder_1, postorder_1);

        ArrayList<Integer> postorder_1_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_1_result = new ArrayList<Integer>();

        a.traversal_postorder(node_1, postorder_1_result);
        a.traversal_inorder(node_1, inorder_1_result);

        int[] postorder_1_check = postorder_1_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_1_check = inorder_1_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_1, postorder_1_check);
        assertArrayEquals(inorder_1, inorder_1_check);

        System.out.println(Arrays.toString(postorder_1_check));
        System.out.println(Arrays.toString(inorder_1_check));

        int[] postorder_2 = new int[]{26, 6, 30, 21, 9, 5, 17};
        int[] inorder_2 = new int[]{26, 6, 30, 17, 5, 9, 21};

        TreeNode node_2 = a.buildTree(inorder_2, postorder_2);

        ArrayList<Integer> postorder_2_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_2_result = new ArrayList<Integer>();

        a.traversal_postorder(node_2, postorder_2_result);
        a.traversal_inorder(node_2, inorder_2_result);

        int[] postorder_2_check = postorder_2_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_2_check = inorder_2_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_2, postorder_2_check);
        assertArrayEquals(inorder_2, inorder_2_check);

        System.out.println(Arrays.toString(postorder_2_check));
        System.out.println(Arrays.toString(inorder_2_check));

        int[] postorder_3 = new int[]{1, 3, 4, 2, 7, 6, 5};
        int[] inorder_3 = new int[]{1, 2, 3, 4, 5, 6, 7};

        TreeNode node_3 = a.buildTree(inorder_3, postorder_3);

        ArrayList<Integer> postorder_3_result = new ArrayList<Integer>();
        ArrayList<Integer> inorder_3_result = new ArrayList<Integer>();

        a.traversal_postorder(node_3, postorder_3_result);
        a.traversal_inorder(node_3, inorder_3_result);

        int[] postorder_3_check = postorder_3_result.stream().mapToInt(i -> i).toArray();
        int[] inorder_3_check = inorder_3_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_3, postorder_3_check);
        assertArrayEquals(inorder_3, inorder_3_check);

        System.out.println(Arrays.toString(postorder_3_check));
        System.out.println(Arrays.toString(inorder_3_check));
    }
}
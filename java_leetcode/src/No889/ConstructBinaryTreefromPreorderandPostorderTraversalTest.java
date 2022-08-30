package No889;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConstructBinaryTreefromPreorderandPostorderTraversalTest {

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
        int[] preorder_1 = new int[]{3, 9, 20, 15, 7};

        TreeNode node_1 = a.constructFromPrePost(preorder_1, postorder_1);

        ArrayList<Integer> postorder_1_result = new ArrayList<Integer>();
        ArrayList<Integer> preorder_1_result = new ArrayList<Integer>();

        a.traversal_postorder(node_1, postorder_1_result);
        a.traversal_preorder(node_1, preorder_1_result);

        int[] postorder_1_check = postorder_1_result.stream().mapToInt(i -> i).toArray();
        int[] preorder_1_check = preorder_1_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_1, postorder_1_check);
        assertArrayEquals(preorder_1, preorder_1_check);

        System.out.println(Arrays.toString(postorder_1_check));
        System.out.println(Arrays.toString(preorder_1_check));


        int[] postorder_3 = new int[]{1, 3, 4, 2, 7, 6, 5};
        int[] preorder_3 = new int[]{5, 2, 1, 4, 3, 6, 7};

        TreeNode node_3 = a.constructFromPrePost(preorder_3, postorder_3);

        ArrayList<Integer> postorder_3_result = new ArrayList<Integer>();
        ArrayList<Integer> preorder_3_result = new ArrayList<Integer>();

        a.traversal_postorder(node_3, postorder_3_result);
        a.traversal_preorder(node_3, preorder_3_result);

        int[] postorder_3_check = postorder_3_result.stream().mapToInt(i -> i).toArray();
        int[] preorder_3_check = preorder_3_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_3, postorder_3_check);
        assertArrayEquals(preorder_3, preorder_3_check);

        System.out.println(Arrays.toString(postorder_3_check));
        System.out.println(Arrays.toString(preorder_3_check));


        int[] postorder_2 = new int[]{8, 9, 4, 5, 2, 6, 7, 3, 1};
        int[] preorder_2 = new int[]{1, 2, 4, 8, 9, 5, 3, 6, 7};

        TreeNode node_2 = a.constructFromPrePost(preorder_2, postorder_2);

        ArrayList<Integer> postorder_2_result = new ArrayList<Integer>();
        ArrayList<Integer> preorder_2_result = new ArrayList<Integer>();

        a.traversal_postorder(node_2, postorder_2_result);
        a.traversal_preorder(node_2, preorder_2_result);

        int[] postorder_2_check = postorder_2_result.stream().mapToInt(i -> i).toArray();
        int[] preorder_2_check = preorder_2_result.stream().mapToInt(i -> i).toArray();

        assertArrayEquals(postorder_2, postorder_2_check);
        assertArrayEquals(preorder_2, preorder_2_check);

        System.out.println(Arrays.toString(postorder_2_check));
        System.out.println(Arrays.toString(preorder_2_check));

    }
}
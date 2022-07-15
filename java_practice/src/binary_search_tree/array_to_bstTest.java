package binary_search_tree;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class array_to_bstTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void test_search_by_recursive() {
        // 第一個測試 array
        Integer[] test_array_data = new Integer[]{1, 2, 7, 14, 7, 3, 8, 12, 15, 26, 17, 8, 8, 34, 5, 8, 7, 7};
        Arrays.sort(test_array_data);

        // init instance
        array_to_bst test_bst = new array_to_bst(test_array_data);
        System.out.println(String.format("Array 1: %s, length: %s", Arrays.toString(test_bst.data), test_bst.data.length));

        Integer result;
        Integer check_result;
        // test case of target value
        Integer[] target_values = new Integer[]{5, 6, 7, 3, 14, 8};
        for (Integer target_value : target_values) {
            result = test_bst.search_first_match_by_recursive(target_value);
            check_result = Arrays.binarySearch(test_array_data, target_value);
            check_result = check_result < 0 ? null : check_result; // 跟我寫的行為不同，需替換一下才能 assert。找不到的 return 為 -(insertpoint) - 1, https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#binarySearch%28byte%5b%5d,%20byte%29

            System.out.println(String.format("find %s result index: %s", target_value, result));
            assertEquals(result, check_result);
        }

        // 第二個測試 array (無 重複)
        test_array_data = new Integer[]{1, 2, 7, 14, 3, 8, 12, 15, 26, 17, 34, 5};
        Arrays.sort(test_array_data);

        // init instance
        test_bst = new array_to_bst(test_array_data);
        System.out.println(String.format("Array 2: %s, length: %s", Arrays.toString(test_bst.data), test_bst.data.length));

        // test case of target value
        target_values = new Integer[]{5, 6, 7, 3, 14, 8};
        for (Integer target_value : target_values) {
            result = test_bst.search_first_match_by_recursive(target_value);
            check_result = Arrays.binarySearch(test_array_data, target_value);
            check_result = check_result < 0 ? null : check_result; // 跟我寫的行為不同，需替換一下才能 assert。找不到的 return 為 -(insertpoint) - 1, https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#binarySearch%28byte%5b%5d,%20byte%29

            System.out.println(String.format("find %s result index: %s", target_value, result));
            assertEquals(result, check_result);
        }

    }

    /**
     * 測試二元搜尋 (找第一個 match 到的)
     */
    @Test
    public void test_binary_search_by_loop() {
        // 第一個測試 array
        Integer[] test_array_data = new Integer[]{1, 2, 7, 14, 7, 3, 8, 12, 15, 26, 17, 8, 8, 34, 5, 8, 7, 7};

        // 先排序
        Arrays.sort(test_array_data);

        // init instance
        array_to_bst test_bst = new array_to_bst(test_array_data);
        System.out.println(String.format("Array 1: %s, length: %s", Arrays.toString(test_bst.data), test_bst.data.length));

        Integer result;
        Integer check_result;

        // test case of target value
        Integer[] target_values = new Integer[]{5, 6, 7, 3, 14, 8};
        for (Integer target_value : target_values) {
            result = test_bst.search_first_match_by_loop(target_value);
            check_result = Arrays.binarySearch(test_array_data, target_value);
            check_result = check_result < 0 ? null : check_result; // 跟我寫的行為不同，需替換一下才能 assert。找不到的 return 為 -(insertpoint) - 1, https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#binarySearch%28byte%5b%5d,%20byte%29

            System.out.println(String.format("find %s result index: %s", target_value, result));
            assertEquals(result, check_result);
        }

        // 第二個測試 array (無 重複)
        test_array_data = new Integer[]{1, 2, 7, 14, 3, 8, 12, 15, 26, 17, 34, 5};

        // 先排序
        Arrays.sort(test_array_data);

        // init instance
        test_bst = new array_to_bst(test_array_data);
        System.out.println(String.format("Array 2: %s, length: %s", Arrays.toString(test_bst.data), test_bst.data.length));

        // test case of target value
        target_values = new Integer[]{5, 6, 7, 3, 14, 8};
        for (Integer target_value : target_values) {
            result = test_bst.search_first_match_by_loop(target_value);
            check_result = Arrays.binarySearch(test_array_data, target_value);
            check_result = check_result < 0 ? null : check_result; // 跟我寫的行為不同，需替換一下才能 assert。找不到的 return 為 -(insertpoint) - 1, https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#binarySearch%28byte%5b%5d,%20byte%29

            System.out.println(String.format("find %s result index: %s", target_value, result));
            assertEquals(result, check_result);
        }

        // 第三個測試 empty array (無 重複)
        test_array_data = new Integer[]{};

        test_bst = new array_to_bst(test_array_data);
        System.out.println(String.format("Array 3: %s, length: %s", Arrays.toString(test_bst.data), test_bst.data.length));
        test_array_data = new Integer[]{};
        target_values = new Integer[]{1, 3, 19};
        for (Integer target_value : target_values) {
            result = test_bst.search_first_match_by_loop(target_value);
            check_result = Arrays.binarySearch(test_array_data, target_value);
            check_result = check_result < 0 ? null : check_result; // 跟我寫的行為不同，需替換一下才能 assert。找不到的 return 為 -(insertpoint) - 1, https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html#binarySearch%28byte%5b%5d,%20byte%29

            System.out.println(String.format("find %s result index: %s", target_value, result));
            assertEquals(result, check_result);
        }

    }

    /**
     * 測試 Traversal
     */
    @Test
    public void test_bst_and_traversal() {
        // Case 1
        Integer[] test_array_data_1 = new Integer[]{17, 5, 8, 7, 8, 7, 33, 8, 26, 7, 14, 1, 4, 12, 15, 59, 42, 49, 83, 75, 98, 2, 8, 7};
        array_to_bst bst1 = new array_to_bst(test_array_data_1);
        Tree tree_1 = bst1.build_bst();

        /**
         *  Case 1 搜尋樹 (build 過程是把 array for loop 一次，因此 index 0 的 17 是根)
         *        17
         *     /      \
         *     5       33
         *  /    \    /  \
         * 1      8  26   59
         *  \    /      /   \
         *   4  7      42    83
         *  /  / \      \    / \
         * 2  7   8     49  75  98
         *   /   /
         *  7   8
         */

        System.out.println(" -- Pre Order -- (by recursive)");
        tree_1.traversal_pre_order();
        System.out.println("");
        System.out.println(" -- In Order -- (by recursive)");
        tree_1.traversal_in_order();
        System.out.println("");
        System.out.println(" -- Post Order -- (by recursive)");
        tree_1.traversal_post_order();
        System.out.println("");

        System.out.println(" -- Pre Order -- (by stack)");
        ArrayList<Integer> pre_traversal_result = tree_1.traversal_pre_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(pre_traversal_result.toArray())));
        assertArrayEquals(pre_traversal_result.toArray(), new Integer[] {17, 5, 1, 4, 2, 8, 7, 7, 7, 7, 8, 8, 8, 14, 12, 15, 33, 26, 59, 42, 49, 83, 75, 98});

        System.out.println(" -- In Order -- (by stack)");
        ArrayList<Integer> in_traversal_result = tree_1.traversal_in_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(in_traversal_result.toArray())));
        assertArrayEquals(in_traversal_result.toArray(), new Integer[] {1, 2, 4, 5, 7, 7, 7, 7, 8, 8, 8, 8, 12, 14, 15, 17, 26, 33, 42, 49, 59, 75, 83, 98});

        System.out.println(" -- Post Order -- (by stack)");
        ArrayList<Integer> post_traversal_result = tree_1.traversal_post_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(post_traversal_result.toArray())));
        assertArrayEquals(post_traversal_result.toArray(), new Integer[] {2, 4, 1, 7, 7, 7, 8, 8, 8, 7, 12, 15, 14, 8, 5, 26, 49, 42, 75, 98, 83, 59, 33, 17});

        // Case 2 (同樣數字組成，但往左側)
        Integer[] test_array_data_2 = new Integer[]{42, 17, 5, 8, 7, 33, 8, 7, 8, 26, 7, 14, 1, 4, 12, 15, 59, 49, 83, 75, 98, 2, 8, 7};
        array_to_bst bst_2 = new array_to_bst(test_array_data_2);
        Tree tree_2 = bst_2.build_bst();

        System.out.println(" -- Pre Order -- (by recursive)");
        tree_2.traversal_pre_order();
        System.out.println("");
        System.out.println(" -- In Order -- (by recursive)");
        tree_2.traversal_in_order();
        System.out.println("");
        System.out.println(" -- Post Order -- (by recursive)");
        tree_2.traversal_post_order();
        System.out.println("");

        System.out.println(" -- Pre Order -- (by stack)");
        pre_traversal_result = tree_1.traversal_pre_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(pre_traversal_result.toArray())));
        assertArrayEquals(pre_traversal_result.toArray(), new Integer[] {17, 5, 1, 4, 2, 8, 7, 7, 7, 7, 8, 8, 8, 14, 12, 15, 33, 26, 59, 42, 49, 83, 75, 98});

        System.out.println(" -- In Order -- (by stack)");
        in_traversal_result = tree_1.traversal_in_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(in_traversal_result.toArray())));
        assertArrayEquals(in_traversal_result.toArray(), new Integer[] {1, 2, 4, 5, 7, 7, 7, 7, 8, 8, 8, 8, 12, 14, 15, 17, 26, 33, 42, 49, 59, 75, 83, 98});

        System.out.println(" -- Post Order -- (by stack)");
        post_traversal_result = tree_1.traversal_post_order_by_stack();
        System.out.println(String.format(" %s", Arrays.toString(post_traversal_result.toArray())));
        assertArrayEquals(post_traversal_result.toArray(), new Integer[] {2, 4, 1, 7, 7, 7, 8, 8, 8, 7, 12, 15, 14, 8, 5, 26, 49, 42, 75, 98, 83, 59, 33, 17});

        Node n3 = new Node(3);
        Node n7 = new Node(7);
        n7.left_node = n3;
        Node n2 = new Node(2);
        Node n6 = new Node(6);
        n6.right_node = n2;
        Node n9 = new Node(9);
        n9.left_node = n7;
        n9.right_node = n6;

        Node n1 = new Node(1);
        Node n5 = new Node(5);
        Node n4 = new Node(4);
        n4.right_node = n1;
        Node n8 = new Node(8);
        n8.left_node = n5;
        n8.right_node = n4;

        Node root = new Node(10);
        root.left_node = n9;
        root.right_node = n8;

//                   10
//               /       \
//            9            8
//         /    \        /   \
//        7      6      5      4
//       /         \            \
//      3           2            1

        System.out.println(" -- Pre Order -- (by recursive)");
        root.traversal_pre_order();
        System.out.println("");
        System.out.println(" -- In Order -- (by recursive)");
        root.traversal_in_order();
        System.out.println("");
        System.out.println(" -- Post Order -- (by recursive)");
        root.traversal_post_order();
        System.out.println("");
    }


}
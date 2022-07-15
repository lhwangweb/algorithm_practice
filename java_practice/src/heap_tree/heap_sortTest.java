package heap_tree; /**
 * 測試 heap_tree.heap_sort
 */

import heap_tree.heap_sort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class heap_sortTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void test_sort() {
        ArrayList<Map<String, Object>> test_cases = new ArrayList();

        // static case
        test_cases.add(Map.ofEntries(
            entry("name", "範例 1"),
            entry("data", new ArrayList<Integer>(Arrays.asList(1, 3, 5, 4, 6, 13, 10, 9, 8, 15, 17)))
        ));
        test_cases.add(Map.ofEntries(
            entry("name", "範例 2"),
            entry("data", new ArrayList<Integer>(Arrays.asList(1, 3, 5, 4, 6, 13, 32, 8, 55, 16, 10, 9, 8, 15, 17, 91, 2)))
        ));

        // random case
        Random rd = new Random();
        Integer [] this_array;
        int random_length;
        ArrayList<Integer> this_case;

        // case 1
        this_case = new ArrayList();
        random_length = 18; // 長度 18
        for (int i = 0; i < random_length; i++) {
            this_case.add(rd.nextInt(100) + 1);
        }
        Collections.shuffle(this_case);
        test_cases.add(Map.ofEntries(
            entry("name", "範例 4，長度 18，隨機"),
            entry("data", this_case)
        ));

        // case 2
        this_case = new ArrayList();
        random_length = rd.nextInt(30) + 7; // 7~36
        for (int i = 0; i < random_length; i++) {
            this_case.add(rd.nextInt(100) + 1); // element 1 ~100
        }
        Collections.shuffle(this_case);
        test_cases.add(Map.ofEntries(
            entry("name", "範例 5，長度 7~36，隨機"),
            entry("data", this_case)
        ));

        // case 3
        this_case = new ArrayList();
        random_length = rd.nextInt(20) + 7; // 7~26
        for (int i = 0; i < random_length; i++) {
            this_case.add(rd.nextInt(100) + 1); // element 1 ~100
        }
        this_case.add(30); this_case.add(30); this_case.add(30); this_case.add(30); this_case.add(30);
        Collections.shuffle(this_case);
        test_cases.add(Map.ofEntries(
            entry("name", "範例 6，長度 7~26，隨機，加入相同元素"),
            entry("data", this_case)
        ));

        // case 4
        this_case = new ArrayList();
        random_length = rd.nextInt(18) + 8; // 8~25
        for (int i = 0; i < random_length; i++) {
            this_case.add(rd.nextInt(75) + 1); // element 1 ~100
        }
        this_case.add(60); this_case.add(60);
        this_case.add(25); this_case.add(25); this_case.add(25);
        this_case.add(17); this_case.add(17); this_case.add(17); this_case.add(17); this_case.add(17);
        Collections.shuffle(this_case);
        test_cases.add(Map.ofEntries(
            entry("name", "範例 7，長度 8~25，隨機，加入相同元素"),
            entry("data", this_case)
        ));

        // 開始測試
        heap_sort hb_instance;
        ArrayList<Integer> test_data;
        ArrayList<Integer> answer_data;

        // 跑測試
        for (Map entry : test_cases) {
            try {
                System.out.println(entry.get("name"));
                test_data = (ArrayList<Integer>) entry.get("data");
                answer_data =  (ArrayList<Integer>) test_data.clone();

                // max heap and asc
                hb_instance = new heap_sort(test_data, true);
                hb_instance.heap_sort();
                System.out.println(String.format(
                    "ASC: %s -> %s", Arrays.toString(test_data.toArray()), Arrays.toString(hb_instance.array_data.toArray())
                ));

                // asc sort answer and assert
                Collections.sort(answer_data);
                assertArrayEquals(hb_instance.array_data.toArray(), answer_data.toArray());

                // min heap and desc
                hb_instance = new heap_sort(test_data, false);
                hb_instance.heap_sort();
                System.out.println(String.format(
                    "DESC: %s -> %s", Arrays.toString(test_data.toArray()), Arrays.toString(hb_instance.array_data.toArray())
                ));

                // desc sort answer and assert
                Collections.sort(answer_data, Collections.reverseOrder());
                assertArrayEquals(hb_instance.array_data.toArray(), answer_data.toArray());

            } catch (Exception eee) {
                System.out.println(eee);
            }

        }
    }

    @Test
    public void test_deletion() {
        ArrayList<Integer> test_case = new ArrayList<Integer>(
                Arrays.asList(1, 3, 5, 4, 6, 13, 10, 9, 8, 15, 17)
        );

        // remove case 1 - top node
        heap_sort hb_instance = new heap_sort(test_case, true);
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        hb_instance.delete_top_node();

        System.out.println("case 1 (remove top node) after");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        // remove case 2 - any node
        hb_instance = new heap_sort(test_case, true); // 重新 init
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        hb_instance.delete_one_node(3);

        System.out.println("case 2 (remove 3 node, value 9) (17, 15, 13, 9 ...)after");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        // remove case 3 - any node
        hb_instance = new heap_sort(test_case, true); // 重新 init
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);
        hb_instance.delete_one_node(7);

        System.out.println("case 3 (remove 7 node, value 4) (17, 15, 13, 9, 6, 5, 10, 4) after");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        // remove case 4 - any node
        hb_instance = new heap_sort(test_case, true); // 重新 init
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);
        hb_instance.delete_one_node(2);

        System.out.println("case 4 (remove 2 node, value 13) (17, 15, 13, ...) after");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        // remove case 4 - any node
        hb_instance = new heap_sort(test_case, true); // 重新 init
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);
        hb_instance.delete_one_node(1);

        System.out.println("case 5 (remove 1 node, value 15) (17, 15, 13, ...) after");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");
    }

    @Test
    public void test_insertion() {
        ArrayList<Integer> test_case = new ArrayList<Integer>(
            Arrays.asList(1, 3, 4, 6, 13, 10, 9, 15, 17)
        );

        // insert case 1
        heap_sort hb_instance = new heap_sort(test_case, true);
        System.out.println("before");
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println("case 1, insert 40");
        hb_instance.insert_node(40);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 2, insert 14 (還沒有的 element)");
        hb_instance.insert_node(14);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 3, insert 15 (有相同的 element)");
        hb_instance.insert_node(15);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 4, insert 15 (有相同的 element)");
        hb_instance.insert_node(15);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 5, insert 15 (有相同的 element)");
        hb_instance.insert_node(15);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println(" re-init heap ");

        hb_instance = new heap_sort(test_case, true);
        System.out.println("case 1, insert 15 (有相同的 element)");
        hb_instance.insert_node(15);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 2, insert 15 (有相同的 element)");
        hb_instance.insert_node(15);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 3, insert 17 (有相同的 element)");
        hb_instance.insert_node(17);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 4, insert 17 (有相同的 element)");
        hb_instance.insert_node(17);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

        System.out.println("case 5, insert 36 (有相同的 element)");
        hb_instance.insert_node(36);
        System.out.println(Arrays.toString(hb_instance.array_data.toArray(new Integer[0])));
        printFourLevelHeap(hb_instance.array_data);

        System.out.println(" -- ");

    }

    /**
     * 印出 4 層 Heap
     * @param heap_data
     */
    static void printFourLevelHeap(ArrayList<Integer> heap_data)
    {
        int len = heap_data.size();
        if (len <= 0) {
            return;
        }

        System.out.println(String.format("             %d    ", heap_data.get(0)));
        System.out.println("       /         \\  ");
        System.out.println(String.format("      %d            %d", heap_data.get(1), heap_data.get(2)));
        System.out.println("    /   \\       /    \\");
        System.out.println(String.format("  %d    %d      %d     %d", heap_data.get(3), heap_data.get(4), heap_data.get(5), heap_data.get(6)));
        System.out.println("  / \\   / \\    / \\   / \\");
        System.out.println(String.format(" %s  %s  %s  %s  %s  %s  %s  %s",
            len >= 8 ? heap_data.get(7):"x",
            len >= 9 ? heap_data.get(8):"x",
            len >= 10 ? heap_data.get(9):"x",
            len >= 11 ? heap_data.get(10):"x",
            len >= 12 ? heap_data.get(11):"x",
            len >= 13 ? heap_data.get(12):"x",
            len >= 14 ? heap_data.get(13):"x",
            len >= 15 ? heap_data.get(14):"x"
        ));

    }
}
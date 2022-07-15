package heap_tree; /**
 *  測試 heap_tree.build_heap
 */

import heap_tree.build_heap;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class build_heapTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public static void main(String[] args) {

        int[] init_data = new int[] { 1, 3, 5, 4, 6, 13, 10, 9, 8, 15, 17 };
        System.out.println(String.format("Origin Heap is: %s", Arrays.toString(init_data)));
        printFourLevelHeap(init_data);
        build_heap hb = new build_heap(init_data);
        System.out.println(String.format("Max Heap is: %s", Arrays.toString(hb.array_data)));
        printFourLevelHeap(hb.array_data);
        // 檢查 array
        assertArrayEquals(hb.array_data, new int[] { 17, 15, 13, 9, 6, 5, 10, 4, 8, 3, 1 });


        init_data = new int[] { 1, 3, 5, 4, 6, 13, 10, 8, 15, 17, 9, 27 };
        System.out.println(String.format("Origin Heap is: %s", Arrays.toString(init_data)));
        printFourLevelHeap(init_data);
        hb = new build_heap(init_data);
        System.out.println(String.format("Max Heap is: %s", Arrays.toString(hb.array_data)));
        printFourLevelHeap(hb.array_data);
        // 檢查 array
        assertArrayEquals(hb.array_data, new int[] { 27, 17, 13, 15, 9, 5, 10, 8, 4, 6, 3, 1 });

        init_data = new int[] { 1, 15, 17, 9, 225, 5, 14, 6, 13, 10, 8, 91, 45};
        System.out.println(String.format("Origin Heap is: %s", Arrays.toString(init_data)));
        printFourLevelHeap(init_data);
        hb = new build_heap(init_data);
        System.out.println(String.format("Max Heap is: %s", Arrays.toString(hb.array_data)));
        printFourLevelHeap(hb.array_data);
        // 檢查 array
        assertArrayEquals(hb.array_data, new int[] { 1, 15, 17, 9, 273, 5, 14, 6, 13, 10, 8, 91, 45 });
    }

    /**
     * 印出 4 層 Heap
     * @param heap_data
     */
    static void printFourLevelHeap(int[] heap_data)
    {
        int len = heap_data.length;
        if (len <= 0) {
            return;
        }

        System.out.println(String.format("             %d    ", heap_data[0]));
        System.out.println("       /         \\  ");
        System.out.println(String.format("      %d            %d", heap_data[1], heap_data[2]));
        System.out.println("    /   \\       /    \\");
        System.out.println(String.format("  %d    %d      %d     %d", heap_data[3], heap_data[4], heap_data[5], heap_data[6]));
        System.out.println("  / \\   / \\    / \\   / \\");
        System.out.println(String.format(" %s  %s  %s  %s  %s  %s  %s  %s",
            len >= 8 ? heap_data[7]:"x",
            len >= 9 ? heap_data[8]:"x",
            len >= 10 ? heap_data[9]:"x",
            len >= 11 ? heap_data[10]:"x",
            len >= 12 ? heap_data[11]:"x",
            len >= 13 ? heap_data[12]:"x",
            len >= 14 ? heap_data[13]:"x",
            len >= 15 ? heap_data[14]:"x"
        ));

    }
}
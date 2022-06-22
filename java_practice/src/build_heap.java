/**
 * Build Heap 練習
 * 基本上是 follow https://www.geeksforgeeks.org/building-heap-from-array/ 的邏輯
 *
 */
public class build_heap {

    public int[] array_data;

    /**
     * 建構式
     *   在建構式中，會完成 Max-Heap 的 heapify
     * @param data
     */
    public build_heap(int[] data) {

        this.array_data = data;
        int array_length = this.array_data.length;

        // 從最後一個 element，也就是樹最底層最右邊的 parent
        int start_index = (array_length - 2) / 2;

        for (int i = start_index; i >= 0; i--) {
            this.siftDown(i);
        }
    }

    public void siftDown(int i) {
        int array_length = this.array_data.length;

        if (i > array_length) {
            return;
        }

        int largest_index = i;

        int left_child_index = 2 * i + 1;
        int right_child_index = 2 * i + 2;

        // 有左子節點、左子節點比較大
        if (left_child_index < array_length && this.array_data[left_child_index] > this.array_data[largest_index]) {
            largest_index = left_child_index;
        }

        // 有右子節點、右子節點比較大 (注意 條件是跟 largest_index 比，由於上面先比過左子節點，如果左子節點較大，就會替換到 largest_index ，故這裏再比右子節點，就可以完成三者之間最大的放到 parent)
        if (right_child_index < array_length && this.array_data[right_child_index] > this.array_data[largest_index]) {
            largest_index = right_child_index;
        }

        if (largest_index != i) {

            System.out.println(
                String.format("swap %d(idx %d) <-> %d(idx %d)",
                    this.array_data[largest_index],
                    largest_index,
                    this.array_data[i],
                    i
                )
            );
            swap_element(largest_index, i);
            siftDown(largest_index);
        }

    }

    public void swap_element(int index_a, int index_b) {
        int temp = this.array_data[index_a];
        this.array_data[index_a] = this.array_data[index_b];
        this.array_data[index_b] = temp;

    }

}

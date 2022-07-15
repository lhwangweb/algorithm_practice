package heap_tree;

import java.util.ArrayList;
import java.util.List;

/**
 *  練習 heap 的插入與刪除，以及 heap sort
 *
 *  https://www.geeksforgeeks.org/insertion-and-deletion-in-heaps/
 */

public class heap_sort {
    ArrayList<Integer> array_data;

    boolean max_heap = true;

    boolean DEBUG = false;

    /**
     * 建構式
     * @param data
     * @param max_heap 堆積類型
     *    true Max Heap
     *    false Min Heap
     */
    public heap_sort(ArrayList<Integer> data, boolean max_heap)
    {
        // 賦予 init data to variable
        this.array_data = (ArrayList<Integer>) data.clone();  // clone a duplicate to class variable
        this.max_heap = max_heap;

        // heapify
        this.heapify();

    }

    /**
     * 完成 Max/Min Heap 的過程
     *
     */
    public void heapify() {
        // 進行 build
        int array_length = this.array_data.size();

        // 從最後一個 element，也就是樹最底層最右邊的 parent
        int start_index = (array_length - 2) / 2;

        for (int i = start_index; i >= 0; i--) {
            this.siftDown(i, array_length + 1);
        }
    }

    /**
     * 移除任意一個節點
     * 是 delete_top_node 的廣義延伸版本
     * @param i
     */
    public void delete_one_node(int i)
    {
        int array_length = this.array_data.size();

        // 要刪除的 element ，與最後一個交換
        swap_element(i, array_length - 1);

        // 為了明確，最後一個 element 設定為 Null 表示刪除 (懶得把 variable 改 arraylist 了)
        this.array_data.set(array_length - 1, null);

        // sift down No. i node
        this.siftDown(i, array_length - 1);
    }

    /**
     *  練習移除最頂端的一個 node
     *
     */
    public void delete_top_node()
    {
        delete_one_node(0);
    }

    /**
     *
     *
     *  heap sort 跟 build heap 都講得不錯的影片 https://www.youtube.com/watch?v=j-DqQcNPGbE
     */

    public void heap_sort()
    {
        int array_length = this.array_data.size();

        for (int k = array_length - 1; k > 0 ; k--) {
            if (this.DEBUG == true) {
                System.out.println(String.format(
                    "swap %d(0) <-> %s(%d)", this.array_data.get(0), this.array_data.get(k), k
                ));
            }

            // 第一個與最後一個交換
            this.swap_element(0, k);

            if (this.DEBUG == true) {
                System.out.println(String.format("sift %d", k));
            }

            // sift down top node
            this.siftDown(0, k);
        }
    }

    // 筆記:
    // 1. Java 似乎沒辦法直接的給 function argument 預設值
    // https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    // 2. 類似 keyword argument, named parameter
    // https://stackoverflow.com/questions/1988016/named-parameter-idiom-in-java
    //
    // 怎麼以上兩個事情都有人提 builder pattern ?

    /**
     *
     * @param i   要 sift down 的節點 index
     */
    public void siftDown(int i, int skip_node_index) {
        int array_length = this.array_data.size();

        if (i > array_length) {
            return;
        }

        int largest_index = i;

        int left_child_index = 2 * i + 1;
        int right_child_index = 2 * i + 2;

        if (this.max_heap == true) {
            // 左子節點存在、左子節點比較大、左子節點沒有要跳過
            if (
                left_child_index < array_length &&
                this.array_data.get(left_child_index) > this.array_data.get(largest_index) &&
                left_child_index < skip_node_index
            ) {
                largest_index = left_child_index;
            }

            // 右子節點存在、右子節點比較大、右子節點沒有要跳過
            // (注意 條件是跟 largest_index 比，由於上面先比過左子節點，如果左子節點較大，就會替換到 largest_index ，故這裏再比右子節點，就可以完成三者之間最大的放到 parent)
            if (
                right_child_index < array_length &&
                this.array_data.get(right_child_index) > this.array_data.get(largest_index) &&
                right_child_index < skip_node_index
            ) {
                largest_index = right_child_index;
            }
        } else {
            // 左子節點存在、左子節點比較小、左子節點沒有要跳過
            if (
                left_child_index < array_length &&
                this.array_data.get(left_child_index) < this.array_data.get(largest_index) &&
                left_child_index < skip_node_index
            ) {
                largest_index = left_child_index;
            }

            // 右子節點存在、右子節點比較小、右子節點沒有要跳過
            if (
                right_child_index < array_length &&
                this.array_data.get(right_child_index) < this.array_data.get(largest_index) &&
                right_child_index < skip_node_index
            ) {
                largest_index = right_child_index;
            }
        }

        if (largest_index != i) {

            if (this.DEBUG == true) {
                System.out.println(
                    String.format("swap %d(idx %d) <-> %d(idx %d)",
                        this.array_data.get(largest_index),
                        largest_index,
                        this.array_data.get(i),
                        i
                    )
                );
            }

            swap_element(largest_index, i);
            siftDown(largest_index, skip_node_index);
        }

    }

    /**
     * 交換 this.array 裡面的 element
     *
     * @param index_a
     * @param index_b
     */
    public void swap_element(int index_a, int index_b) {
        int temp = this.array_data.get(index_a);
        this.array_data.set(index_a, this.array_data.get(index_b));
        this.array_data.set(index_b, temp);
    }

    public void insert_node(Integer new_element)
    {
        // 新 element 加在最後
        this.array_data.add(new_element);

        // 重新做一輪 heapify
        this.heapify();
    }
}

/**
 * 501. Find Mode in Binary Search Tree
 * https://leetcode.com/problems/find-mode-in-binary-search-tree/
 *
 * 解法一
 * Runtime: 13 ms, faster than 19.37% of Java online submissions for Find Mode in Binary Search Tree.
 * Memory Usage: 47.1 MB, less than 32.65% of Java online submissions for Find Mode in Binary Search Tree.
 *
 * 解法二
 * Runtime: 21 ms, faster than 6.84% of Java online submissions for Find Mode in Binary Search Tree.
 * Memory Usage: 46.2 MB, less than 45.36% of Java online submissions for Find Mode in Binary Search Tree.
 * 退步
 *
 */
package No501;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindModeinBinarySearchTree {
    public static void Main(String[] args) {
    }
}

/**
 * Definition for a binary tree node.
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    /**
     * for 解法一
     *   Traversal 並統計數值出現次數，採 PreOrder，也就是先統計再往子節點走
     * @param node
     * @param count_data
     */
    public void count_traversal_1(TreeNode node, Map<Integer, Integer> count_data) {
        if (node == null) {
            return;
        }

        // 統計數量
        if (count_data.containsKey(node.val)) {
            count_data.put(node.val, count_data.get(node.val) + 1);
        } else {
            count_data.put(node.val, 1);
        }

        // 對左邊遞迴下去，直到 leaf 為止
        if (node.left != null) {
            count_traversal_1(node.left, count_data);
        }

        // 對右邊遞迴下去，直到 leaf 為止
        if (node.right != null) {
            count_traversal_1(node.right, count_data);
        }
    }

    /**
     * 解法一
     *   採用比較暴力的方式，先 Traversal 過一次，過程中把 count 資訊統計在一個 map 裡，Traversal 結束後，從 map 中提取 count 數最多的 N 個
     *
     * @param root
     * @return
     */
    public int[] findMode_1(TreeNode root) {
        // 儲存每個不同的 node value 出現幾次
        // key: node value
        // value: 該 node value 出現次數
        Map<Integer, Integer> count_data = new HashMap<Integer, Integer>();

        count_traversal_1(root, count_data);

        // 從 map 內找出最多 count
        Integer max_count = -1;

        for (Map.Entry<Integer, Integer> entry : count_data.entrySet()){
            if (max_count == null) {
                max_count = entry.getValue();
            } else {
                if (max_count < entry.getValue()) {
                    max_count = entry.getValue();
                }
            }
        }

        // 最多 count 的可能不只一個，這裏重新把所有達到 count 的 key 都挑出來
        ArrayList<Integer> result = new ArrayList<Integer>(){};
        for (Map.Entry<Integer, Integer> entry : count_data.entrySet()){
            if (entry.getValue() == max_count) {
                result.add(entry.getKey());
            }
        }

        // ArrayList<Integer> -> int[]
        return result.stream().mapToInt(i->i).toArray();

    }


    /* 紀錄 modes */
    ArrayList<Integer> modes = new ArrayList<Integer>();

    /* 目前 走訪到的 Node 值，同時也是走到下一個 Node 時，可以作為上一個 Node 值來比較，比較完後才變更為目前 Node 值 */
    Integer current_number_value = -1;

    /* 目前走訪到的 Node 值的計次變數 */
    Integer current_number_count = 0;

    /* 目前已知的 Mode 的計次 */
    Integer mode_count = 0;

    /**
     * Traversal InOrder for 解法二
     *
     *  1,2,3,4,5,6
     *  1,3,4,4,4,5,5,5
     *  1,3,4,4,4,5,6,7
     *
     * @param node
     */
    public void count_traversal_2(TreeNode node) {
        if (node == null) {
            return;
        }

        // 對左邊遞迴下去，直到 leaf 為止
        if (node.left != null) {
            count_traversal_2(node.left);
        }

        if (this.current_number_value == node.val) {
            // 上一個跟這一個一樣，表示這個數字至少出現兩次
            this.current_number_count += 1;
        } else {
            // 上一個跟這一個不一樣，重設 current
            this.current_number_value = node.val;
            this.current_number_count = 1;
        }

        if (this.mode_count < this.current_number_count) {
            // 出現比 mode_count 更高的數字，表示有某個值的 count 更高
            this.modes.clear(); // 之前的 modes 必然可以作廢，所以之前的 modes 清掉
            this.mode_count = this.current_number_count; // 更新目前 mode 的計次，以便之後繼續比較
            this.modes.add(this.current_number_value); // 加入 modes arraylist
        } else if (this.mode_count == this.current_number_count) {
            // count 與之前另一個值的 count 一樣，可以
            if (!this.modes.contains(this.current_number_value)) {
                this.modes.add(this.current_number_value);
            }
        }

        // 對右邊遞迴下去，直到 leaf 為止
        if (node.right != null) {
            count_traversal_2(node.right);
        }
    }

    /**
     * 解法二
     * 由於解法一成績相對不好，參考了其他跑比較快的 Code，意識到本題由於已經有說是整理好的二元搜尋樹，所以如果使用 In Order 走訪，就可以得到 ASC 排序，此時就算有相同數字，也會緊連著一起，可以用這一點在走訪時就把數字紀錄好，以改善速度
     *
     * @param root
     * @return
     */
    public int[] findMode_2(TreeNode root) {
         if (root == null) {
             return new int[] {};
         }

         // 走訪並完成計次，最多次數的 modes 存到 this.modes
         count_traversal_2(root);

         // 如果沒有，就回傳空
         if (this.modes.size() == 0) {
             return new int[] {};
         }

         // 建構 this.modes.size() 長度的 int[]
         int[] result = new int[this.modes.size()];

         // 填充所有 modes
         for (Integer i = 0; i < this.modes.size(); i++) {
             result[i] = this.modes.get(i);
         }

         // return
         return result;
    }
}
/**
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal
 *
 * 第一次
 * Runtime: 311 ms, faster than 5.04% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
 * Memory Usage: 118.2 MB, less than 5.01% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
 *
 * 第二次 調整，至少 map 的部分不要做 argument 一直遞迴，可能是慢速＆多耗空間的兇手
 * Runtime: 339 ms, faster than 5.04% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
 * Memory Usage: 117.8 MB, less than 5.01% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
 * 沒有改善，不是遞迴傳 Map 的問題？  (也對，這是 mutable 的，傳遞進去不會增加 Stack )
 *
 */

package No105;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConstructBinaryTreefromPreorderandInorderTraversal {
    public static void Main(String[] args) {
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    /** 儲存每個 value 在 inorder 內的 index，方便之後快速取用
     *   key: node value
     *   value: 在 array 裡的 index
     */
    Map<Integer, Integer> inorder_index_map = new HashMap<Integer, Integer>();

    /**
     * 新增一個 Node - 嘗試把新節點要放到目前節點的左或右子節點
     * 要丟到左還是右，依據兩個節點的值在 In Order 內的相對位置判斷
     *   -> In Order 內，如果新節點的位置在目前節點的左邊，就丟到目前節點的左子節點
     *   -> In Order 內，如果新節點的位置在目前節點的右邊，就丟到目前節點的右子節點
     *
     * @param node  目標節點
     * @param new_node  要加入的新節點
     */
    public void add_node(TreeNode node, TreeNode new_node) {
        // 目前節點 在 inorder 內的 index
        Integer node_index = this.inorder_index_map.get(node.val);

        // 新節點 在 inorder 內的 index
        Integer new_node_index = this.inorder_index_map.get(new_node.val);

        // 比較兩個 index ，就可以得到兩者在 inorder 內的相對位置
        if (node_index > new_node_index) {
            // 新節點的值 index 比較小 - 所以 新節點的值 在 inorder 內相對左邊的位置，所以要往左邊節點丟。
            if (node.left == null) {
                // 左邊還沒有子節點，直接 assign
                node.left = new_node;
            } else {
                // 左邊已有子節點，遞迴，把左子節點重新當成目標節點，重新嘗試把新節點掛到該節點的下面
                add_node(node.left, new_node);
            }

        } else if (node_index < new_node_index) {
            // 新節點的值 index 比較大 - 所以 新節點的值 在 inorder 內相對右邊的位置，所以要往右邊節點丟。
            if (node.right == null) {
                // 右邊還沒有子節點，直接 assign
                node.right = new_node;
            } else {
                // 右邊已有子節點，遞迴，把右子節點重新當成目標節點，重新嘗試把新節點掛到該節點的下面
                add_node(node.right, new_node);
            }
        } else {
            // 相等時不考慮，不應該出現兩個 node 所處 index 相同
        }
    }

    /**
     * 建立 Tree (方法一)
     *
     * 此方法是採用類似 Binary Search Tree 的 INSERT 方法
     * 每次從 root Node 加入新的 child node，並依據次序關係決定右或左
     *   1. 加入的順序
     *      - 就是 Pre Order 的 ASC 順序 (Pre Order ASC 的順序就是一個從根向下的順序)
     *   2. 決定是左或右子節點
     *      - 在一般的 Binary Tree 建構過程中，決定左右的是 Node Value 本身的大小關係
     *      - 在這裡的話，則依據 InOrder 的次序 (因為 InOrder 的次序本身就代表了相對在左或者相對在右)
     *
     * 時間複雜度應該是 N log N - 新增 N 個節點 * 每次新增預期跑 log N 次
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree_v1(int[] preorder, int[] inorder) {
        // 有任一 array 為空
        if (preorder.length <= 0 || inorder.length <= 0) {
            return null;
        }

        // 長度不符
        if (preorder.length != inorder.length) {
            return null;
        }

        // 填充 map
        for (int i = 0; i < inorder.length; i++) {
            this.inorder_index_map.put(inorder[i], i);
        }

        // root - Pre Order 的第一個 Element
        TreeNode root = new TreeNode(preorder[0]);

        // 如果 inorder 長度只有 1，就只有一個 Node, 直接 return root
        if (inorder.length == 1) {
            return root;
        }

        // 從 Pre Order 第二個開始依序新增進去
        for (int i = 1; i < preorder.length; i++) {
            // 每個新節點的加入，都先從 root 出發，如果需要會不斷遞迴向最適合的子節點前進
            add_node(root, new TreeNode(preorder[i]));
        }

        return root;
    }

    /**
     *  建立 Tree (方法二)
     *
     *  由於原本想的方法經 Leetcode submission 檢驗，很慢，參考了 Leetcode 較快的 Solution，練習一次
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree_v2(int[] preorder, int[] inorder) {

    }

    /**
     * Pre Order Traversal (驗算用)
     * @param node
     * @param result
     */
    public void traversal_preorder(TreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }

        result.add(node.val);

        if (node.left != null) {
            traversal_preorder(node.left, result);
        }

        if (node.right != null) {
            traversal_preorder(node.right, result);
        }

    }

    /**
     * In Order Traversal (驗算用)
     * @param node
     * @param result
     */
    public void traversal_inorder(TreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            traversal_inorder(node.left, result);
        }

        result.add(node.val);

        if (node.right != null) {
            traversal_inorder(node.right, result);
        }
    }
}
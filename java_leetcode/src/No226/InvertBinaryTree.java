/**
 * 226. Invert Binary Tree
 * https://leetcode.com/problems/invert-binary-tree/
 *
 * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
 * Memory Usage: 41.2 MB, less than 77.42% of Java online submissions for Invert Binary Tree.
 *
 */
package No226;


public class InvertBinaryTree {
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
     * 遞迴處理樹的 invert，遍歷方式採 Pre Order (根->左->右)
     *
     * @param node
     */
    public void invert_traversal(TreeNode node) {
        if (node == null) {
            return;
        }

        // Swap 對每個 node 的兩個子節點交換
        TreeNode tmp_left = node.left;
        node.left = node.right;
        node.right = tmp_left;

        // 對左邊遞迴下去，直到 leaf 為止
        if (node.left != null) {
            invert_traversal(node.left);
        }

        // 對右邊遞迴下去，直到 leaf 為止
        if (node.right != null) {
            invert_traversal(node.right);
        }
    }

    public TreeNode invertTree(TreeNode root) {
        invert_traversal(root);
        return root;
    }
}
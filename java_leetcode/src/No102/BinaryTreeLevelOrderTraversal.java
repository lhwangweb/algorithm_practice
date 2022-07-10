/**
 * 102. Binary Tree Level Order Traversal
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 *
 * Runtime: 2 ms, faster than 33.63% of Java online submissions for Binary Tree Level Order Traversal.
 * Memory Usage: 43.7 MB, less than 34.17% of Java online submissions for Binary Tree Level Order Traversal.
 *
 */
package No102;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeLevelOrderTraversal {
    public static void Main(String[] args) {
    }
}

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
    public void traversal(List<TreeNode> nodes, ArrayList<List<Integer>> results) {
        if (nodes.size() <= 0) {
            return;
        }

        List<TreeNode> next_cycle_nodes = new ArrayList<TreeNode>();
        List<Integer> this_level_results = new ArrayList<Integer>();

        // 把子節點加入下一輪待處理清單
        for (TreeNode node : nodes) {

            this_level_results.add(node.val);

            if (node.left != null) {
                next_cycle_nodes.add(node.left);
            }
            if (node.right != null) {
                next_cycle_nodes.add(node.right);
            }
        }

        // 這一層的 Element 加入 list
        results.add(this_level_results);

        if (next_cycle_nodes.isEmpty()) {
            return; // 早點結束，不用多遞迴一次 - 如果已經沒有 下一輪待處理清單 的話
        }

        traversal(next_cycle_nodes, results);

    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<List<Integer>>();
        }

        ArrayList<List<Integer>> results = new ArrayList<List<Integer>>();
        List<TreeNode> next_cycle_nodes = new ArrayList<TreeNode>();
        next_cycle_nodes.add(root);

        traversal(next_cycle_nodes, results);
        return results;
    }
}

/**
 * 98. Validate Binary Search Tree
 * https://leetcode.com/problems/validate-binary-search-tree/
 *
 * Runtime: 1 ms, faster than 61.08% of Java online submissions for Validate Binary Search Tree.
 * Memory Usage: 43.9 MB, less than 48.46% of Java online submissions for Validate Binary Search Tree.
 */

package No98;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ValidateBinarySearchTree {
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
     * In Order Traversal, Traversal 過程中，順便檢查每次加入 list 的數字是否可使 list 嚴格遞增，如果否，就不是 OK 的 Binary Search Tree
     * @param node
     * @param result
     * @return
     */
    public boolean traversal_and_validate(TreeNode node, List<Integer> result) {
        if (node == null) {
            return true; // leaf 節點
        }

        if(!traversal_and_validate(node.left, result)) {
            return false; // 如果左側子樹結果為否則否，不必繼續往下
        }

        if (!result.isEmpty()) {
            Integer latest_value = result.get(result.size()-1);
            if (latest_value >= node.val) {
                return false; // 之前 latest element 不符合嚴格遞增 -> 不符合 binary tree -> return false;
            }
        }

        result.add(node.val);// 加入 traversal list

        if(!traversal_and_validate(node.right, result)) {
            return false;  // 如果右側子樹結果為否則否
        }

        return true;
    }


    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true; // 空樹
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        return traversal_and_validate(root, result);
    }
}
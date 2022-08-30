package No101;

import java.util.ArrayList;

/**
 * 101. Symmetric Tree
 *
 * Runtime: 0 ms, faster than 100.00% of Java online submissions for Symmetric Tree.
 * Memory Usage: 40.1 MB, less than 99.70% of Java online submissions for Symmetric Tree.
 */
public class SymmetricTree {
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

    public boolean isSymmetric(TreeNode root) {
        return traversal_and_check(root.left, root.right);
    }


    /**
     * 基本上採用 preorder 的走法
     * 只是 左子樹的 preorder 是 left first, 而 右子樹 則為 right first
     *   左子樹： 根->左->右
     *   右子樹： 根->右->左
     *
     * @param left_node
     * @param right_node
     * @return
     */
    public boolean traversal_and_check(TreeNode left_node, TreeNode right_node) {
        // 如果其中一個 node 為 NULL
        if (left_node == null && right_node == null) {
            return true;
        } else if (left_node == null && right_node != null) {
            return false;
        } else if (left_node != null && right_node == null) {
            return false;
        }

        // 到這裡 兩個應該都是 NOT NULL

        // 如果 兩個 node 不相等
        if (left_node.val != right_node.val) {
            return false;
        }

        // 左子樹 取左邊 ; 右子樹 取右邊
        boolean res1 = traversal_and_check(left_node.left, right_node.right);

        // 左子樹 取右邊 ; 右子樹 取左邊
        boolean res2 = traversal_and_check(left_node.right, right_node.left);

        return res1 && res2;
    }
}

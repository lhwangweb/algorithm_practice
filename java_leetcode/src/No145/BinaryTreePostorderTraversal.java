/**
 * 145. Binary Tree Postorder Traversal
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 *
 * Stack
 * Runtime: 2 ms, faster than 6.66% of Java online submissions for Binary Tree Postorder Traversal.
 * Memory Usage: 42.3 MB, less than 49.21% of Java online submissions for Binary Tree Postorder Traversal.
 *
 * Recursive
 *
 * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Postorder Traversal.
 * Memory Usage: 42.2 MB, less than 49.02% of Java online submissions for Binary Tree Postorder Traversal.
 *
 */
package No145;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreePostorderTraversal {
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
    public void traversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            traversal(node.left, result);
            traversal(node.right, result);
            result.add(node.val);
        }
    }

    public List<Integer> postorderTraversal_recursive(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        traversal(root, result);
        return result;
    }

    public List<Integer> postorderTraversal_stack(TreeNode root) {
        List<Integer> traversal_result = new ArrayList<Integer>() {};
        Stack<TreeNode> traversal_stack = new Stack<TreeNode>();

        // root null 就可以 return [] 了
        if (root == null) {
            return traversal_result;
        }

        TreeNode tmp_clone_node = null;
        TreeNode work_node = root;

        while (true) {
            // 1. 把 根、右 丟進 Stack
            if (work_node.left != null || work_node.right != null) {
                // 1.1 非 leaf，則把自己(根節點)丟進 Stack
                //     丟進去前，要把所有子節點刪除，以免之後從 Stack 取出時，又會重複走訪子節點
                tmp_clone_node = new TreeNode(work_node.val, null, null); // clone 並使所有子節點 null
                traversal_stack.push(tmp_clone_node); // 加入 stack
            }

            if (work_node.right != null) {
                // 1.2 有右子節點，就把右子節點丟進 Stack
                traversal_stack.push(work_node.right); // 加入 stack
            }

            // 1. 是否有左子節點？
            if (work_node.left != null) {
                // 有左子節點

                // 往左走
                work_node = work_node.left;
                continue;
            }

            // 走到這裡的節點沒有左子節點

            if (work_node.right == null) {
                // 也沒有右 -> 是個 leaf， 加入
                traversal_result.add(work_node.val);
            }

            // 取出下一個 節點
            if (traversal_stack.isEmpty()) {
                break;
            }

            work_node = traversal_stack.pop();

        }

        return traversal_result;
    }
}
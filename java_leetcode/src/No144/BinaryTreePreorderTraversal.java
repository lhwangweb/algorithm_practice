/**
 *
 * Runtime: 1 ms, faster than 49.81% of Java online submissions for Binary Tree Preorder Traversal.
 * Memory Usage: 42.8 MB, less than 5.17% of Java online submissions for Binary Tree Preorder Traversal.
 */
package No144;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreePreorderTraversal {
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> traversal_result = new ArrayList<Integer>() {};
        Stack<TreeNode> traversal_stack = new Stack<TreeNode>();

        // root null 就可以 return [] 了
        if (root == null) {
            return traversal_result;
        }

        TreeNode tmp_clone_node = null;
        TreeNode work_node = root;

        while (true) {
            // 1. 把 左、右節點丟入 Stack，由於之後需要先取左、再取右，所以丟入時，要先丟右、再丟左
            if (work_node.right != null) {
                // 1.1 右 (如果有)
                traversal_stack.push(work_node.right);
            }

            if (work_node.left != null) {
                // 1.2 左 (如果有)
                traversal_stack.push(work_node.left);
            }

            // 2. value 加入 ArrayList (根節點)
            traversal_result.add(work_node.val);

            // 3. 從 Stack 取出下一個節點
            if (traversal_stack.isEmpty()) {
                // 3.1
                break;
            }

            // 3.2
            work_node = traversal_stack.pop();

        }


        return traversal_result;
    }
}


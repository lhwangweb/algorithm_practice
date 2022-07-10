/**
 * 94. Binary Tree Inorder Traversal
 * https://leetcode.com/problems/binary-tree-inorder-traversal/
 *
 * Stack
 * Runtime: 1 ms, faster than 48.73% of Java online submissions for Binary Tree Inorder Traversal.
 * Memory Usage: 42.3 MB, less than 49.14% of Java online submissions for Binary Tree Inorder Traversal.
 *
 * Recursive
 * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
 * Memory Usage: 42.5 MB, less than 30.10% of Java online submissions for Binary Tree Inorder Traversal.
 */

package No94;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeInorderTraversal {
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
            result.add(node.val);
            traversal(node.right, result);
        }
    }

    public List<Integer> inorderTraversal_recursive(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        traversal(root, result);
        return result;
    }
    /**
     * 以 Stack + while loop 完成 in order traversal
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal_stack(TreeNode root) {
        List<Integer> traversal_result = new ArrayList<Integer>() {};
        Stack<TreeNode> traversal_stack = new Stack<TreeNode>();

        // root null 就可以 return [] 了
        if (root == null) {
            return traversal_result;
        }

        TreeNode tmp_clone_node = null;
        TreeNode work_node = root;

        while (true) {

            // 1. 是否有左子節點？
            if (work_node.left != null) {
                // 1.1 有左子節點

                // 1.1.1 把自己(根節點)丟進 Stack，但在丟進去要把 左子節點刪除，以免之後從 Stack 取出時，又會重複走訪左子節點
                tmp_clone_node = new TreeNode(work_node.val, null, work_node.right); // clone 並使左子節點 null
                traversal_stack.push(tmp_clone_node); // 加入 stack

                // 1.1.2 往左走
                work_node = work_node.left;
                continue;
            }

            // 2. 走到這裡的節點，應該都沒有左子節點

            // 3. value 加入 ArrayList (此節點可能是左 leaf or 根，都是在這個環節加入 ArrayList)
            traversal_result.add(work_node.val);

            // 4. 是否有右子節點？
            if (work_node.right != null) {
                // 4.1 有右子節點

                // 4.1.1 向右走
                work_node = work_node.right;
                continue;
            }

            // 5. 準備從 Stack 取出下一個
            if (traversal_stack.isEmpty()) {
                // 5.1 若 Stack 空了就結束
                break;
            }

            // 5.2 取出節點
            work_node = traversal_stack.pop();
        }

        return traversal_result;
    }
}


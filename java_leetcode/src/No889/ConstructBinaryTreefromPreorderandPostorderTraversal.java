package No889;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 889. Construct Binary Tree from Preorder and Postorder Traversal
 * 
 * Runtime: 1 ms, faster than 99.16% of Java online submissions for Construct Binary Tree from Preorder and Postorder Traversal.
 * Memory Usage: 41.8 MB, less than 97.70% of Java online submissions for Construct Binary Tree from Preorder and Postorder Traversal.
 */
public class ConstructBinaryTreefromPreorderandPostorderTraversal {
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

    Map<Integer, Integer> preorder_index_map = new HashMap<Integer, Integer>();

    Map<Integer, Integer> postorder_index_map = new HashMap<Integer, Integer>();

    int[] preorder;

    int[] postorder;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        // 有任一 array 為空 or 長度不符
        if (postorder.length <= 0 || preorder.length <= 0 || postorder.length != preorder.length) {
            return null;
        }

        // 長度不符
        if (postorder.length != postorder.length) {
            return null;
        }

        // 填充 map
        for (int i = 0; i < preorder.length; i++) {
            this.preorder_index_map.put(preorder[i], i);
        }

        for (int i = 0; i < postorder.length; i++) {
            this.postorder_index_map.put(postorder[i], i);
        }

        // 放到 property
        this.preorder = preorder;
        this.postorder = postorder;

        TreeNode node = build_tree(0, postorder.length-1, 0, preorder.length - 1);

        return node;

    }

    public TreeNode build_tree(int postorder_start, int postorder_end, int preorder_start, int preorder_end) {
        if (postorder_start > postorder_end || preorder_start > preorder_end || postorder_start < 0 || postorder_end < 0 || preorder_start < 0 || preorder_end < 0) {
            // 各個起訖 index 不可能出現 開始比結束大也不可能為負數。
            return null;
        }

        // postorder [ ...左子樹... ][ ...右子樹... ][root]
        // preorder [root][ ...左子樹... ][ ...右子樹... ]

        // 兩個 array 僅能靠 頭、尾 確定 root 目前的根，但不能確定 左子樹 跟 右子樹 的成分
        // 所以這邊假設是 Full Binary Tree
        // Full Binary tree 的話，首先每個 root 要嘛 沒有children，要嘛一定是兩個 children
        // 所以，每次從 preorder postorder 取走 root 後，都假設剩下的

        int this_root_value = this.preorder[preorder_start];

        // 題目保證 tree ＯＫ，所以就不檢查 preorder, postorder 是否有可能不相符

        TreeNode this_node = new TreeNode(this_root_value);
        if (postorder_start == postorder_end) {
            // 沒有 children
            return this_node;
        }

        // 去掉 root 後的 postorder 頭，是左子樹的 root -> 在 preorder 內，則會位在該左子樹的尾，如下示意
        // postorder [ ...左子樹... (children root)][ ...右子樹... ][root]
        // preorder  [root][(children root) ...左子樹... ][ ...右子樹... ]
        // 右子樹同理，以此規則標記左右子樹的邊界

        int left_pre_start = preorder_start + 1;
        int left_post_root_index = this.postorder_index_map.get(this.preorder[left_pre_start]);

        int left_post_end = left_post_root_index;
        int left_post_start = postorder_start;

        int left_length = left_post_root_index - postorder_start + 1;
        int left_pre_end = left_pre_start + left_length - 1;

        int right_post_start =  left_post_end + 1;
        int right_post_end = postorder_end - 1;

        int right_pre_start = left_pre_end + 1;
        int right_pre_end = preorder_end;

        this_node.left = build_tree(left_post_start, left_post_end, left_pre_start, left_pre_end);
        this_node.right = build_tree(right_post_start, right_post_end, right_pre_start, right_pre_end);

        return this_node;
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
     * Post Order Traversal (驗算用)
     *
     * @param node
     * @param result
     */
    public void traversal_postorder(TreeNode node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            traversal_postorder(node.left, result);
        }

        if (node.right != null) {
            traversal_postorder(node.right, result);
        }

        result.add(node.val);
    }
}
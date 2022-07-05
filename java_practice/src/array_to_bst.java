import java.util.ArrayList;
import java.util.Stack;

public class array_to_bst {

    Integer[] data;

    public array_to_bst(Integer[] data) {
        this.data = data;
    }

    /**
     * 遞迴 Binary Search
     *
     * @param target_value
     * @return
     */
    public Integer search_first_match_by_recursive(Integer target_value) {
        if (this.data.length <= 0) {
            return null;
        }

        Integer mid_index = (this.data.length - 1) / 2; // 無條件捨去
        Integer target_index;
        if (this.data[mid_index] == target_value) {
            // 找到了！ 回傳 index
            target_index = mid_index;
            // 如果想找出所有相同數值的最左邊，或最右邊，就在這裡繼續往下 binary search
            //

        } else if (this.data[mid_index] < target_value) {
            // 找的東西在 mid_index 右側
            target_index = search_first_match_by_recursive(target_value, mid_index + 1, this.data.length - 1);
        } else if (this.data[mid_index] > target_value) {
            // 找的東西在 mid_index 左側
            target_index = search_first_match_by_recursive(target_value, 0, mid_index - 1);
        } else {
            target_index = null;
        }
        return target_index;
    }

    /**
     * 遞迴 Binary Search
     *
     * @param target_value
     * @param start_index
     * @param end_index
     * @return
     */
    public Integer search_first_match_by_recursive(Integer target_value, Integer start_index, Integer end_index) {
        if (end_index < start_index) {
            // end index 過頭
            return null;
        }

        Integer mid_index = (start_index + end_index) / 2;
        Integer target_index;
        if (this.data[mid_index] == target_value) {
            // 找到了！ 回傳 index
            target_index = mid_index;
            // 如果想找出所有相同數值的最左邊，或最右邊，就在這裡繼續往下 binary search
            //

        } else if (this.data[mid_index] < target_value) {
            // 找的東西在 mid_index  右側
            target_index = search_first_match_by_recursive(target_value, mid_index + 1, end_index);
        } else if (this.data[mid_index] > target_value) {
            // 找的東西在 mid_index  左側
            target_index = search_first_match_by_recursive(target_value, start_index, mid_index - 1);
        } else {
            target_index = null;
        }
        return target_index;
    }

    /**
     * 用 loop 做 binary search
     *
     * @return
     */
    public Integer search_first_match_by_loop(Integer target_value) {
        if (this.data.length <= 0) {
            return null;
        }

        // 限縮範圍用
        Integer start_index = 0;
        Integer end_index = this.data.length - 1;
        Integer mid_index;
        Integer target_index = null;

        while (true) {
            if (end_index < start_index) {
                break;
            }

            // 中間 index
            mid_index = (end_index + start_index) / 2;

            if (this.data[mid_index] == target_value) {
                // 找到了，結束 loop
                target_index = mid_index;
                break;
            } else if (this.data[mid_index] < target_value) {
                // 目標值比較大，應該往右找 -> 將起點更新
                start_index = mid_index + 1;
            } else if (this.data[mid_index] > target_value) {
                // 目標值比較小，應該往左找 -> 將終點更新
                end_index = mid_index - 1;
            } else {
                // 奇怪的例外！
                target_index = null;
                break;
            }
        }

        return target_index;
    }

    /**
     * Build a Binary Search Tree
     *
     * @return Tree
     */
    public Tree build_bst() {
        Tree a_tree = new Tree();
        for (Integer value : this.data) {
            a_tree.add(new Node(value));
        }
        return a_tree;
    }
}

/**
 * Binary Search Tree
 */
class Tree {
    /**
     * 根節點
     */
    public Node root = null;

    /**
     * 建構式
     */
    public Tree() {
    }

    /**
     * 加入節點
     *
     * @param new_node
     */
    public void add(Node new_node) {
        if (this.root == null) {
            // 如果還沒有節點，就會成為根節點
            this.root = new_node;
        } else {
            // 有節點，就用節點的 Method 加到節點的下屬
            this.root.add_child(new_node);
        }
    }

    public void traversal_pre_order() {
        // 呼叫根節點的遞迴 Traversal pre order
        this.root.traversal_pre_order();
    }

    public void traversal_in_order() {
        // 呼叫根節點的遞迴 Traversal in order
        this.root.traversal_in_order();
    }

    public void traversal_post_order() {
        // 呼叫根節點的遞迴 Traversal post order
        this.root.traversal_post_order();
    }

    public ArrayList<Integer> traversal_pre_order_by_stack() {
        ArrayList<Integer> traversal_result = new ArrayList<Integer>();
        Stack<Node> traversal_stack = new Stack<Node>();
        // 想法：
        //   走訪順序 根 -> 左 -> 右
        //   步驟
        //     1. 先把這個節點的左、右節點丟入 Stack (如果有)
        //     2. value 加入 result
        //     3. 從 Stack 取出下一個節點
        //     再回到 1.

        Node work_node = this.root;

        while (true) {
            // 因為是 走訪是 左->右，所以右要先丟進 Stack

            // 如果有右，丟入 Stack
            if (work_node.right_node != null) {
                traversal_stack.push(work_node.right_node);
            }

            // 如果有左，丟入 Stack
            if (work_node.left_node != null) {
                traversal_stack.push(work_node.left_node);
            }

            // value 加入 result
            traversal_result.add(work_node.node_value);

            if (traversal_stack.empty()) {
                // Stack 已空
                break;
            }

            // 取出下一個 Stack Element 作為根，重複步驟
            work_node = traversal_stack.pop();
        }

        return traversal_result;
    }

    public ArrayList<Integer> traversal_in_order_by_stack() {
        ArrayList<Integer> traversal_result = new ArrayList<Integer>();
        Stack<Node> traversal_stack = new Stack<Node>();
        // 左 -> 根 -> 右
        // 1. 是否有左？
        //    如果有左 -> 先把自己(根節點)的副本丟入 Stack，該副本刪除左節點(避免下次重複走訪左節點) -> 向左走，把左子節點當根 -> 回到步驟 1.
        //    如果沒左 -> this Node value 加入 result -> 繼續執行 2.
        // 2. 是否有右？
        //    -> 如果有右 -> 向右走，把右子節點當根 -> 回到步驟 1.
        //    -> 如果沒右 -> 從 Stack 取出前一個根節點 -> 回到步驟 1.

        Node work_node = this.root;
        Node tmp_node;
        try {
            while (true) {

                // 1. 是否有左？
                if (work_node.left_node != null) {
                    // 如果有左

                    // 先把自己(根節點)的副本丟入 Stack
                    tmp_node = (Node) work_node.clone(); // clone Object 副本
                    tmp_node.left_node = null; // 副本的左節點刪掉，如此才不會在處理根節點時，又重複回到左節點
                    traversal_stack.add(tmp_node); // 丟入 Stack

                    // 向左走
                    work_node = work_node.left_node;
                    // 回到 1. 的步驟
                    continue;
                }

                // 走到這裡的沒有左子節點

                // value 加入 result (處理根節點)
                traversal_result.add(work_node.node_value);

                // 是否有右？
                if (work_node.right_node != null) {
                    // 有右， 向右走，並把右作為根
                    work_node = work_node.right_node;
                    // 回到 1. 的步驟
                    continue;
                }

                // 走到這裡的沒左也沒右子節點

                // 如果 Stack 空了就結束
                if (traversal_stack.empty()) {
                    break;
                }

                // 從 Stack 取出前一個根節點
                work_node = traversal_stack.pop();

                // 回到 1. 的步驟
            }
        } catch (Exception e) {
            System.out.println(String.format("In Order Traversal 發生錯誤 %s", e.getMessage()));
        }


        return traversal_result;

    }

    public ArrayList<Integer> traversal_post_order_by_stack() {
        ArrayList<Integer> traversal_result = new ArrayList<Integer>();
        Stack<Node> traversal_stack = new Stack<Node>();
        // 左 -> 右 -> 根
        // 1. 自己是否 leaf ？
        //     如果是 -> this Node value 加入 result -> 從 Stack 取下一個 -> 以該節點為根，回到步驟 1.
        //     如果不是 leaf -> 先把自己(根節點, 刪除子節點的副本)、右，加入 Stack -> 繼續進行 2.
        // 2. 左節點是否存在？
        //      不存在 -> 從 Stack 取下一個 -> 以該節點為根，回到步驟 1.
        //      存在 -> 繼續進行 3.
        // 3. 檢查左節點是否 leaf?
        //   如果是 leaf -> 左子節點 value 加入 result -> 從 Stack 取下一個 -> 以該節點為根，回到 1.
        //   如果不是 leaf ，就把 左 當作根節點，回到步驟 1.

        Node work_node = this.root;
        Node tmp_node;

        try {
            while (true) {
                // 是否 leaf
                if (work_node.left_node == null && work_node.right_node == null) {
                    // 為 leaf
                    // value 加入 result
                    traversal_result.add(work_node.node_value);

                    // 如果 Stack 空就結束
                    if (traversal_stack.empty()) {
                        break;
                    }

                    // 從 Stack 取下一個
                    work_node = traversal_stack.pop();
                    // 回到步驟 1.
                    continue;
                }

                // 走到這裡的，至少有一個子節點，不是 leaf

                // 把自己(的副本)，加入 Stack (副本需刪除所有子節點)
                tmp_node = (Node) work_node.clone(); // clone Object 副本
                tmp_node.left_node = null; // 副本的左右子節點刪掉，如此才不會在最後處理根節點時，又重複回到左或者右子節點
                tmp_node.right_node = null;
                traversal_stack.add(tmp_node); // 丟入 Stack

                // 把右子節點加入 Stack (如果右存在)
                if (work_node.right_node != null) {
                    traversal_stack.add(work_node.right_node);
                }

                // 左節點是否存在
                if (work_node.left_node == null) {
                    // 左不存在

                    // 如果 Stack 空就結束
                    if (traversal_stack.empty()) {
                        break;
                    }

                    // 從 Stack 取下一個
                    work_node = traversal_stack.pop();
                    // 回到步驟 1.
                    continue;
                }

                // 走到這裡，應該一定有左子節點

                // 檢查左節點是否 leaf?
                if (work_node.left_node.left_node == null && work_node.left_node.right_node == null) {
                    // 是 leaf

                    // 把左子節點 value 加入 list
                    traversal_result.add(work_node.left_node.node_value);

                    // 如果 Stack 空就結束
                    if (traversal_stack.empty()) {
                        break;
                    }

                    // 從 Stack 取下一個
                    work_node = traversal_stack.pop();
                    // 回到步驟 1.
                    continue;
                }

                // 走到這裡，一定有左子節點，而且非 leaf

                // 走向左，把左子節點當成根
                work_node = work_node.left_node;

                // 回到步驟 1.
            }
        } catch (Exception e) {
            System.out.println(String.format("Post Order Traversal 發生錯誤 %s", e.getMessage()));
        }
        return traversal_result;
    }
}

class Node implements Cloneable {
    Integer node_value;
    Node left_node;
    Node right_node;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Node(Integer value) {
        this.node_value = value;
    }

    public void add_child(Node new_node) {
        if (new_node.node_value > this.node_value) {
            // 大於，丟到右側
            if (this.right_node == null) {
                // 沒有右子樹，直接 assign
                this.right_node = new_node;
            } else {
                // 有右子樹
                this.right_node.add_child(new_node);
            }
        } else if (new_node.node_value <= this.node_value) {
            // 小於跟等於，丟到左側
            if (this.left_node == null) {
                // 沒有左子樹，直接 assign
                this.left_node = new_node;
            } else {
                // 有左子樹
                this.left_node.add_child(new_node);
            }
        } else {
            /* -- */
        }

    }

    /**
     * Node Recursive Traversal
     */
    public void traversal_in_order() {

        // 左
        if (this.left_node != null) {
            this.left_node.traversal_in_order();
        }
        // 根
        System.out.print(this.node_value);
        System.out.print(" ");

        // 右
        if (this.right_node != null) {
            this.right_node.traversal_in_order();
        }
    }

    public void traversal_pre_order() {

        // 根
        System.out.print(this.node_value);
        System.out.print(" ");

        // 左
        if (this.left_node != null) {
            this.left_node.traversal_pre_order();
        }

        // 右
        if (this.right_node != null) {
            this.right_node.traversal_pre_order();
        }
    }

    public void traversal_post_order() {

        // 左
        if (this.left_node != null) {
            this.left_node.traversal_post_order();
        }

        // 右
        if (this.right_node != null) {
            this.right_node.traversal_post_order();
        }

        // 根
        System.out.print(this.node_value);
        System.out.print(" ");

    }
}
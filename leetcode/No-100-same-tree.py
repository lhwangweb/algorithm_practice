"""
No. 100
https://leetcode.com/problems/same-tree/
"""
import unittest
import typing



# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def isSameTree(self, p: typing.Optional[TreeNode], q: typing.Optional[TreeNode]) -> bool:
        # empty input protection
        if not isinstance(p, TreeNode) and not isinstance(q, TreeNode):
            return True
        elif not isinstance(p, TreeNode) and isinstance(q, TreeNode):
            return False
        elif isinstance(p, TreeNode) and not isinstance(q, TreeNode):
            return False

        # 根->左->右
        # 先比自身值 -> 一樣 -> 再比子節點([1] 都無 [2] 都有 [3] 一個有一個無)
        if p.val == q.val:
            # 值相同，繼續比較 children 狀況
            if not isinstance(p.left, TreeNode) and not isinstance(q.left, TreeNode) and not isinstance(p.right, TreeNode) and not isinstance(q.right, TreeNode):
                # p 與 q 的左右，都已經沒有子節點
                return True
            elif isinstance(p.left, TreeNode) and isinstance(q.left, TreeNode) and isinstance(p.right, TreeNode) and isinstance(q.right, TreeNode):
                # p 與 q 的左右兩邊都還有子節點，都還有 False 的可能，兩邊都遞迴檢查 (DFS Left)，結果以 and 結合
                return self.isSameTree(p=p.left, q=q.left) and self.isSameTree(p=p.right, q=q.right)
            elif not isinstance(p.left, TreeNode) and not isinstance(q.left, TreeNode) and isinstance(p.right, TreeNode) and isinstance(q.right, TreeNode):
                # p, q 左邊同為空，故只剩 右邊子節點還有 False 的可能，遞迴檢查右邊子節點
                return self.isSameTree(p=p.right, q=q.right)
            elif isinstance(p.left, TreeNode) and isinstance(q.left, TreeNode) and not isinstance(p.right, TreeNode) and not isinstance(q.right, TreeNode):
                # p, q 右邊同為空，故只剩 左邊子節點還有 False 的可能，遞迴檢查左邊子節點
                return self.isSameTree(p=p.left, q=q.left)
            else:
                # 其他狀況都不可能 True (都屬於 一個有一個無)
                # 例如:
                #   p.left None v.s. q.left NOT None，不符 (或者反過來)
                #   p.right None v.s. q.right NOT None，不符 (或者反過來)
                #   以上兩者兼有
                #     ... 等
                return False
        else:
            # 值不同，立刻確定不同
            return False

class TestMethod(unittest.TestCase):
    test_cases = []

    def setUp(self):
        # Example 1
        p1 = TreeNode(val=1)
        p2 = TreeNode(val=2)
        p3 = TreeNode(val=3, left=p1, right=p2)
        q1 = TreeNode(val=1)
        q2 = TreeNode(val=2)
        q3 = TreeNode(val=3, left=q1, right=q2)
        self.test_cases.append({
            "p": p3,
            "q": q3,
            "answer": True
        })
        # Example 2
        p2 = TreeNode(val=2)
        p1 = TreeNode(val=1, left=p2)

        q2 = TreeNode(val=2)
        q1 = TreeNode(val=1, right=q2)

        self.test_cases.append({
            "p": p1,
            "q": q1,
            "answer": False
        })

        # Example 3
        p1 = TreeNode(val=1)
        p2 = TreeNode(val=2)
        p3 = TreeNode(val=1, left=p1, right=p2)
        q1 = TreeNode(val=1)
        q2 = TreeNode(val=1)
        q3 = TreeNode(val=2, left=q1, right=q2)
        self.test_cases.append({
            "p": p3,
            "q": q3,
            "answer": False
        })

        self.test_cases.append({
            "p": None,
            "q": None,
            "answer": True
        })

        self.test_cases.append({
            "p": TreeNode(val=1),
            "q": None,
            "answer": False
        })

        self.test_cases.append({
            "p": None,
            "q": TreeNode(val=2),
            "answer": False
        })

        p1 = TreeNode(val=1)
        p0 = TreeNode(val=1, right=p1)
        q1 = TreeNode(val=1)
        q0 = TreeNode(val=1, right=q1)
        self.test_cases.append({
            "p": p0,
            "q": q0,
            "answer": True
        })

    def test_solution(self):
        s = Solution()
        for test_case in self.test_cases:
            res = s.isSameTree(
                p=test_case["p"],
                q=test_case["q"]
            )
            self.assertEqual(res, test_case["answer"])

if __name__ == "__main__":
    unittest.main()
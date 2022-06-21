"""
2. Add Two Numbers
https://leetcode.com/problems/add-two-numbers/

做法簡述
因為 linked-list 起點就是低位數，所以順著兩個 linked-list 的方向做遞迴遍歷(也就是往高位數遞迴)，把每一位數都加總，最後遞迴結束時，所有的 Node 也會組成一條新的 linked-list
進位的部分，除了正常進位還要注意，可能進位會造成更高的位數也要連動進位，例如 99,935 + 100 = 100,035，或請參考題目的 Example 3

成果
Runtime: 79 ms, faster than 70.05% of Python3 online submissions for Add Two Numbers.
Memory Usage: 14.1 MB, less than 9.99% of Python3 online submissions for Add Two Numbers.

討論
可能是因為採用遞迴，記憶體耗用相對較高
可能可以考慮改成 while loop 方式減少記憶體耗用

"""
import unittest
from typing import Optional

def convert_to_list(node):
    """ 把 ListNode 轉換成題目的 list 形式"""
    if node.next is None:
        return [node.val,]

    val_list = convert_to_list(node.next)

    return [node.val] + val_list

def convert_to_node(list_data):
    """
    把 題目的 list 轉換成 ListNode 的輸入
    """
    prev_node = None
    for val in reversed(list_data):
        this_node = ListNode(val=val, next=prev_node)
        prev_node = this_node

    return this_node

# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        if l1 is None and l2 is None:
            # l1, l2 已經到最高位數
            return None
        elif l1 is not None and l2 is None:
            # l2 已經到最高位數 l1 還沒，所以剩下的 l1 就是最高位數(說不定不只一位)，可以全部回傳
            return l1
        elif l1 is None and l2 is not None:
            # l1 已經到最高位數 l2 還沒，所以剩下的 l2 就是最高位數(說不定不只一位)，可以全部回傳
            return l2

        # 遞迴方向是從 linked-list 開始，所以是 低位數 往 高位數 走
        sum_result_node = self.addTwoNumbers(l1=l1.next, l2=l2.next)

        if sum_result_node is None:
            #  -- 兩者都沒有更高位數 --
            # 把目前位數加總
            this_digit_value = l1.val+l2.val
            left_hand_digit = None
            if this_digit_value >= 10:
                # 大於10要進位，產生一個新 digit
                this_digit_value -= 10
                left_hand_digit = ListNode(val=1)
            # 完成目前位數的 List Node
            this_digit = ListNode(val=this_digit_value, next=left_hand_digit)
        else:
            #  -- 已經有最高位數 --
            # 先加總目前位數
            this_digit_value = l1.val + l2.val
            if this_digit_value >= 10:
                # 大於10要進位到較高位數，也就是前一次總和的 ListNode
                this_digit_value -= 10
                sum_result_node.val += 1

                # 高位數有可能 +1 後也要進位，用 loop 處理掉，藉由一個 tmp_node 每次在迴圈末尾都覆寫成 tmp_node.next 達成往前進位的目的
                tmp_node = sum_result_node  # 因 Python Object mutable, 所以 while 第一輪 操作，相當於對著 sum_result_node 操作
                while tmp_node.val >= 10:
                    # 目前位數 -= 10
                    tmp_node.val -= 10

                    # 進到下一位數
                    if tmp_node.next is None:
                        # 下一位數是空的，新增一個 1 的 Node
                        new_digit_node = ListNode(val=1)
                        tmp_node.next = new_digit_node
                    else:
                        # 下一位數不是空的，在下一位 +=1
                        tmp_node.next.val += 1

                    # tmp_node 覆寫為下一位數的 Node
                    tmp_node = tmp_node.next

            this_digit = ListNode(val=this_digit_value, next=sum_result_node)

        # 處理完畢，回傳到目前位數，也就是這個 ListNode(Node已包含從目前位數到最高位數的結果)
        return this_digit


class TestMethod(unittest.TestCase):
    test_cases = []

    def setUp(self):
        # Example 1
        l1 = convert_to_node([2, 4, 3])
        l2 = convert_to_node([5, 6, 4])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [7, 0, 8]
        })

        # Example 2
        l1 = convert_to_node([1,5,9])
        l2 = convert_to_node([5,6,4])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [6, 1, 4, 1]
        })

        # Example 3
        l1 = convert_to_node([1, 5, 9])
        l2 = convert_to_node([5, 6, 4, 2, 6, 7, 2])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [6, 1, 4, 3, 6, 7, 2]
        })

        # Example 4
        l1 = convert_to_node([9,9,9,9,9,9,9])
        l2 = convert_to_node([9,9,9,9])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [8,9,9,9,0,0,0,1]
        })

        # Example
        l1 = convert_to_node([9, 9, 9, 9, 9, 9])
        l2 = convert_to_node([9, 9, 9, 9])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [8, 9, 9, 9, 0, 0, 1]
        })

        # Example
        l1 = convert_to_node([9, 9, 9, 9, 8, 9, 9])
        l2 = convert_to_node([9, 9, 9, 9])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [8, 9, 9, 9, 9, 9, 9]
        })

        # Example
        l1 = convert_to_node([9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9])
        l2 = convert_to_node([9, 9, 9, 9])

        self.test_cases.append({
            "l1": l1,
            "l2": l2,
            "answer": [8, 9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 1]
        })

    def test_solution(self):
        s = Solution()
        for test_case in self.test_cases:
            res = s.addTwoNumbers(
                l1=test_case["l1"],
                l2=test_case["l2"]
            )

            # 轉成 list 來比結果
            self.assertEqual(convert_to_list(res), test_case["answer"])

if __name__ == "__main__":
    unittest.main()
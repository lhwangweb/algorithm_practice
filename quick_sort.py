import copy
import math
import random
import unittest

DEBUG = False


#
# 比較用函數
#
def be_less(a, b):
    """ 比較規則 func, 當 a<b 時回傳 True"""
    return True if a < b else False


def be_less_equal(a, b):
    """ 比較規則 func, 當 a<=b 時回傳 True"""
    return True if a <= b else False


def be_bigger(a, b):
    """ 比較規則 func, 當 a>b 時回傳 True"""
    return True if a > b else False


def be_bigger_equal(a, b):
    """ 比較規則 func, 當 a>=b 時回傳 True"""
    return True if a >= b else False


#
# 第一種
#
def quick1(data, compare_rule_function):
    """
    快速排序
    每次遞迴 D&C 分割的子任務 都使用新的記憶體空間，比較費空間，但是 Code 相對比較單純

    :param data: List, 要排序的 list
    :param compare_rule_function: Func, 自訂的比較規則
        根據 func(a,b) 行為來決定 ASC, DESC
         - 當 a>b 時如果回傳 True -> DESC
         - 反之，當 a<b 時 True -> ASC
    :return: list, 本階段完成的排序結果

    """
    length = len(data)

    # 0~2 均為 base case
    if length == 0:
        return data
    elif length == 1:
        return data

    # 2 以上使用 recursive case 拆解
    pivot = data[0]  # 以第一個 elem 為比較 pivot, 分拆左右
    left_part = []
    right_part = []

    for elem in data[1:]:
        # 滿足比較規則的 elem 放到左 (所以假設比較規則是 be_less 函數 -> 會把比 pivot 小的 elem 放到左邊 -> ASC)
        if compare_rule_function(elem, pivot):
            left_part.append(elem)
        else:
            right_part.append(elem)

    # print 細節
    if DEBUG:
        print(" 拆成: %s, %s, %s" % (str(left_part), pivot, str(right_part)))
    left_result = quick1(left_part, compare_rule_function)
    right_part = quick1(right_part, compare_rule_function)

    # 回傳 累計步數, 排序結果
    return left_result + [pivot, ] + right_part


#
# 第二種
#
def quick2(data, compare_rule_function, lpdx=None, rpdx=None):
    """
    快速排序
    減少空間使用的版本，永遠都在同一個 list 上操作(in-place list)
    即使拆成子任務，也是同一個 list傳入，只是用 index 表示子任務的工作範圍 (而不是用另外一個較短 list 傳入)

    Code 流程參考 https://ithelp.ithome.com.tw/articles/10202330

    :param data: List, 要排序的 list
    :param compare_rule_function: Func, 自訂的比較規則
        根據 func(a,b) 行為來決定 ASC, DESC
         - 當 a>b 時如果回傳 True -> DESC
         - 反之，當 a<b 時 True -> ASC
    """
    if DEBUG:
        print("now data: %s" % str(data))

    # python arg list 不會被動 (mutuable)，所以不用 return data
    if not isinstance(data, list):
        raise Exception("僅限處理 list")

    length = len(data)
    lpdx = lpdx if lpdx is not None else 0
    rpdx = rpdx if rpdx is not None else length - 1

    if lpdx >= rpdx:
        # 左 index 碰到右 index ，長度為 0 -> 代表沒有 elem 要處理
        return

    # 最左邊第一個 elem 為 pivot
    pivot_idx = lpdx

    # 待會左邊 index 起點
    left_idx = lpdx

    # 待會右邊 index 起點
    right_idx = rpdx

    while left_idx != right_idx:
        # 開始一個新的回合，向中間尋找相遇點
        if DEBUG:
            print(" - 向中間找 [%s %s]" % (left_idx, right_idx))

        # 從右起，找到第一個符合(比 pivot 小)的 index (且不要找過頭)
        while not compare_rule_function(data[right_idx], data[pivot_idx]) and left_idx < right_idx:
            if DEBUG:
                print("   - 因為 %s(%s) > pivot %s" % (data[right_idx], right_idx, data[pivot_idx]))
            right_idx -= 1
            if DEBUG:
                print("      - right_idx 推進為 %s" % (right_idx,))
        if DEBUG:
            print("   - right_idx 停在 %s" % (right_idx,))

        # 從左起，找到第一個符合(比 pivot 大)的 index (且不要找過頭)
        while compare_rule_function(data[left_idx], data[pivot_idx]) and left_idx < right_idx:
            if DEBUG:
                print("   - 因為 %s(%s) <= pivot %s" % (data[left_idx], left_idx, data[pivot_idx]))
            left_idx += 1
            if DEBUG:
                print("      - left_idx 推進為 %s" % (left_idx,))
        if DEBUG:
            print("   - left_idx 停在 %s" % (left_idx,))

        # 1. elem == pivot 的條件，放在 左
        # 如果不放在左，則 left_idx 會永遠停在 最左 (eg. index 0)
        #   -> 因為 pivot 是 最左 elem (eg. data[0])，而每次從左邊開始比對時，最左 elem > pivot 永遠不成立，所以 left_idx 永遠不會 += 1)
        # 因此，需要 == 使 left_idx 可以突破 最左 (eg. index 0)
        # 雖然，這樣做也可以排序，不過沒有用到 D&C 精神，因為 left_idx 永遠 0，因此永遠相遇在 0 ，也就是左側 Divide 出來的子任務永遠是 empty list
        # 2.
        # 因為相遇點的 elem 總是交換到最左邊 pivot 的 index
        # 因此有兩種狀況不應該發生：
        #  (1) 排 ASC，若相遇點 elem 值相對較大時，不該被換到最左邊去 (eg. index 0)
        #  (2) 排 DESC 時，若相遇點是較小值，不該被交換到最左邊去 (eg. index 0)
        # 一個解決方式，是每一回合固定先從右邊開始找，不要先從左邊開始找
        # 如此，可以確保相遇點不會向右超出
        # 範例：
        # 排 ASC ， data 為 [8, ..., 7, 2, 9, ...] -> pivot 8，目前 left_idx 在 7, right_idx 在 9
        # 此時，若左邊先找，因為 7、2 都 <=8 ，就會把 left_idx 一路推進到 9 所在，但如果 9 是相遇點 elem，則接下來 8 跟 9 會交換 -> 這樣是有問題的

        if left_idx < right_idx:
            # 沒有找過頭的情況下，符合 左大 右小 的 pair 交換
            if DEBUG:
                print("   - 滿足條件，兩者交換 %s(%s) <-> %s(%s)" % (data[left_idx], left_idx, data[right_idx], right_idx))
            data[left_idx], data[right_idx] = data[right_idx], data[left_idx]

            if DEBUG:
                print("   - 交換後 data %s" % str(data))

    # pivot 跟 相遇點(left_idx==right_idx)交換
    if DEBUG:
        print("  - pivot 跟 相遇點 交換 %s(%s,pivot) <-> %s(%s,相遇)" % (data[pivot_idx], pivot_idx, data[left_idx], left_idx))
    data[pivot_idx], data[left_idx] = data[left_idx], data[pivot_idx]
    if DEBUG:
        print("  - 交換後 data %s" % str(data))

    if DEBUG:
        print("完成這一輪 data: %s " % str(data))

    # 左邊
    if DEBUG:
        print(" 子任務左 %s ~ %s" % (lpdx, left_idx - 1))
    quick2(data, compare_rule_function, lpdx=lpdx, rpdx=left_idx - 1)

    # 右邊
    if DEBUG:
        print(" 子任務右 %s ~ %s" % (right_idx + 1, rpdx))
    quick2(data, compare_rule_function, lpdx=right_idx + 1, rpdx=rpdx)


#
# 第三種
#
def quick3(data, compare_rule_function, lpdx=None, rpdx=None):
    """
    快速排序
    一樣是減少空間使用的版本，永遠都在同一個 list 上操作 (in-place list)

    Code 流程參考: https://www.runoob.com/w3cnote/quick-sort-2.html

    :param data: List, 要排序的 list
    :param compare_rule_function: Func, 自訂的比較規則
        根據 func(a,b) 行為來決定 ASC, DESC
         - 當 a>b 時如果回傳 True -> DESC
         - 反之，當 a<b 時 True -> ASC
    """
    if DEBUG:
        print("now data: %s" % str(data))

    # python arg list 不會被動 (mutuable)，所以不用 return data
    if not isinstance(data, list):
        raise Exception("僅限處理 list")

    length = len(data)
    lpdx = lpdx if lpdx is not None else 0
    rpdx = rpdx if rpdx is not None else length - 1

    if lpdx > rpdx:
        # 左邊 index 已經超過右邊邊際，不必再做事， return
        return
    elif lpdx == rpdx:
        # 左 index 碰到右 index， 也就是只有一個 Elem -> 不用排， return
        return

    # 比較的基準點
    pivot_value = data[lpdx]

    # 檢查的起點 index - 基準點向右 1 位
    check_idx_start = lpdx + 1

    # 用於跟右邊交換的 elem 的 index - 從基準點右邊 1 位開始
    exchange_idx = check_idx_start

    # 從 lpdx + 1 檢查到 rpdx
    for check_idx in range(check_idx_start, rpdx + 1):
        # 當 value 小於 pivot，就跟 exchange_idx 所在的 elem 交換 (此註解以 ASC 為例，而 DESC 其實就是 condition 相反)
        if compare_rule_function(data[check_idx], pivot_value):
            data[check_idx], data[exchange_idx] = data[exchange_idx], data[check_idx]
            # 交換後，exchange_idx 向右推進 1 ，等待下次若有符合的 elem 時，要將其交換回來
            exchange_idx += 1

    # 假設從 lpdx + 1 ~ rpdx 共有 N 個數字 <= pivot  (此註解以 ASC 為例，而 DESC 其實就是 condition 相反)
    # 經過 for loop 後，這 N 個數字都已堆在 從 lpdx + 1 開始的 N 個 elem (lpdx + 1 ~ lpdx + N)
    #
    # 範例 [8, 6, 1, 10, 5, 3, 9, 2, 7, 4] => 經過 for loop => [8, 6, 1, 5, 3, 2, 7, 4, 9, 10] ，結束時 exchange_idx 在 8 的位置
    #    從 index 1 ~ index 9 ，比 8 小的數 elem 共計 7 個，已經堆在 index 1 ~ 7

    # 因為上面的 loop 採用『 交換完之後做 exchange_idx += 1』，因此結束時，會多 1，要扣回來，扣回來的那個 index 才是準備要跟 pivot 交換的 index
    splitting_partition_index = exchange_idx - 1

    # 跟 pivot 交換
    data[lpdx], data[splitting_partition_index] = data[splitting_partition_index], data[lpdx]

    # 把 pivot (index lpdx) 跟 N 個 elem 的最右邊那個交換 (以上面註解的範例，就是 index 7 的那個 4)
    # 交換之後，就完成了目標 - N 個  <= pivot 的數字，都跑到 pivot 左邊了
    #
    # 範例  [8, 6, 1, 5, 3, 2, 7, 4, 9, 10] => 把 pivot 8 跟 index 7 的 elem 4 交換 => [4, 6, 1, 5, 3, 2, 7, 8, 9, 10]
    #    此時，可以看到已經完成這個階段的工作， 8 左邊的 elem <= 8， 右邊的 > 8

    # 接下來做 D&C ，以中間的值為切分點，切成左右兩個子任務
    # 範例 [4, 6, 1, 5, 3, 2, 7 ], [8],  [9, 10]

    # 左邊: 從 lpdx 到 spliting_partition_index - 1
    if DEBUG:
        print(" 子任務左 %s ~ %s" % (lpdx, splitting_partition_index - 1))
    quick3(data, compare_rule_function, lpdx=lpdx, rpdx=splitting_partition_index - 1)

    # 右邊: 從 splitting_partition_index + 1 到 rpdx
    if DEBUG:
        print(" 子任務右 %s ~ %s" % (splitting_partition_index + 1, rpdx))
    quick3(data, compare_rule_function, lpdx=splitting_partition_index + 1, rpdx=rpdx)


class TestMethod(unittest.TestCase):
    samples = []

    def TestUtil_for23(self, data, test_func, compare_func, order):
        """ 測試 in-place 類型的 快速排序 function """
        print("  原本: %s" % str(data))
        test_func(data, compare_func)
        if order == "asc":
            self.assertEqual(data, sorted(data, reverse=False))
            print("  ASC 結果 %s\n" % (data,))
        else:
            self.assertEqual(data, sorted(data, reverse=True))
            print("  DESC 結果 %s\n" % (data,))

    def TestUtil_for1(self, data, test_func, compare_func, order):
        """ 測試非 in-place 類型的 快速排序 function """
        print("  原本: %s" % str(data))
        result = test_func(data, compare_func)
        if order == "asc":
            self.assertEqual(result, sorted(data, reverse=False))
            print("  ASC 結果 %s\n" % (result,))
        else:
            self.assertEqual(result, sorted(data, reverse=True))
            print("  DESC 結果 %s\n" % (result,))

    def setUp(self):
        """ 產生測試案例 list """
        N1 = random.randint(6, 16)
        data_rd1 = [i for i in range(0, N1)]
        random.shuffle(data_rd1)
        N2 = random.randint(11, 27)
        data_rd2 = [i for i in range(random.randint(1, 5), N2)]
        data_rd2 += data_rd2
        random.shuffle(data_rd2)
        data_rd3 = [i for i in range(0, random.randint(7, 20))] + [6, 6, 6] + [7, 7, 7]
        random.shuffle(data_rd3)
        data_rd4_tmp1 = [i for i in range(random.randint(0, 10), random.randint(15, 20))]
        data_rd4_tmp2 = [i for i in range(random.randint(0, 7), random.randint(12, 26))]
        data_rd4 = data_rd4_tmp1 + data_rd4_tmp2
        random.shuffle(data_rd4)
        data_rd4.pop(random.randint(0, len(data_rd4) - 2))
        self.samples = [
            {"name": "iTHome Case", "list": [8, 6, 1, 10, 5, 3, 9, 2, 7, 4]},
            {"name": "簡單序列 (奇數個)", "list": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]},
            {"name": "簡單序列 (偶數個)", "list": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]},
            {"name": "零", "list": []},
            {"name": "一個", "list": [4, ]},
            {"name": "兩個", "list": [4, 5]},
            {"name": "三個", "list": [4, 7, 9]},
            {"name": "四個", "list": [4, 9, 8, 3]},
            {"name": "交錯 (奇數個)", "list": [11, 1, 10, 2, 9, 3, 8, 4, 7, 5, 6]},
            {"name": "交錯 (偶數個)", "list": [1, 10, 2, 9, 3, 8, 4, 7, 5, 6]},
            {"name": "隨機不重複 0 ~ %s 打亂" % N1, "list": data_rd1},
            {"name": "固定帶重複 elem", "list": [6, 3, 8, 6, 4, 6, 9, 3, 3, 3, 6, 9, 7, 5]},
            {"name": "隨機 兩倍重複  0~ %s" % N2, "list": data_rd2},
            {"name": "隨機 加一些相同 elem 進去", "list": data_rd3},
            {"name": "隨機 多加一些 elem 進去", "list": data_rd4},
        ]

    def test_quick3(self):
        """ 測試 quick3 """
        num = 1
        for sample in self.samples:
            print("--- quick 3 第 %s 組測試案例 --- START" % num)
            print(sample["name"])
            print(" 測 < ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick3, compare_func=be_less, order="asc")
            print(" 測 <= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick3, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick3, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick3, compare_func=be_bigger_equal, order="desc")

            print("%s 反轉" % (sample["name"],))
            print(" 測 < ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick3, compare_func=be_less, order="asc")
            print(" 測 <= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick3, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick3, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick3, compare_func=be_bigger_equal, order="desc")
            print("--- quick 3 第 %s 組測試案例 --- END" % num)
            num += 1

    def test_quick2(self):
        """ 測試 quick2 """
        num = 1
        for sample in self.samples:
            print("--- quick 2 第 %s 組測試案例 ---" % num)
            print(sample["name"])
            print(" 測 < ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick2, compare_func=be_less, order="asc")
            print(" 測 <= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick2, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick2, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for23(data=data, test_func=quick2, compare_func=be_bigger_equal, order="desc")

            print("%s 反轉" % (sample["name"],))
            print(" 測 < ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick2, compare_func=be_less, order="asc")
            print(" 測 <= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick2, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick2, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for23(data=reversed_data, test_func=quick2, compare_func=be_bigger_equal, order="desc")
            num += 1

    def test_quick1(self):
        """ 測試 quick1 """
        num = 1
        for sample in self.samples:
            # 每一個案例都測過以下八種情境排列組合
            print("--- quick 1 第 %s 組測試案例 ---" % num)
            print(sample["name"])
            print(" 測 < ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1(data=data, test_func=quick1, compare_func=be_less, order="asc")
            print(" 測 <= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1(data=data, test_func=quick1, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1(data=data, test_func=quick1, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1(data=data, test_func=quick1, compare_func=be_bigger_equal, order="desc")

            print("%s 反轉" % (sample["name"],))
            print(" 測 < ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1(data=reversed_data, test_func=quick1, compare_func=be_less, order="asc")
            print(" 測 <= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1(data=reversed_data, test_func=quick1, compare_func=be_less_equal, order="asc")
            print(" 測 > ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1(data=reversed_data, test_func=quick1, compare_func=be_bigger, order="desc")
            print(" 測 >= ")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1(data=reversed_data, test_func=quick1, compare_func=be_bigger_equal, order="desc")
            num += 1

if __name__ == "__main__":
    unittest.main()

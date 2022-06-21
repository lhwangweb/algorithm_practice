"""
Merge Sort 練習
"""
import copy
import math
import random
import unittest


def merge1(data, reverse, compare_field=None):
    """
    合併排序
    每次 D&C 的子任務都使用新的記憶體空間，比較費空間，但是 Code 相對比較單純

    :param data List, 要排序的 list，目前可以處理 int list 及 dict list
    :param reverse Boolean,
        True: DESC
        False: ASC
    :param field_name str, 當 data 是 dict 時，指定要用來排序的 key field na,e

    :return: list, 本階段完成的排序結果
    """
    if not isinstance(reverse, bool):
        raise Exception("arg 'reverse' should be boolean")

    if not isinstance(data, list):
        raise Exception("arg 'data' should be list")

    length = len(data)
    if length == 0:
        return data
    elif length == 1:
        return data

    # 走到這裡時，data 應該至少有 2 個 elements

    # Divide - 二分切割
    middle_idx = length//2

    # 左半部
    left_part = data[0:middle_idx]
    # 右半部
    right_part = data[middle_idx:]

    # 1.1 Conquer - 繼續送進子任務
    left_part = merge1(left_part, reverse, compare_field)
    right_part = merge1(right_part, reverse, compare_field)

    # 2. 拿到結果，此時左右兩個部分應該已經是個別排序好的結果，現在準備合併
    left_length = len(left_part)
    right_length = len(right_part)

    # 新 list (合併用)
    merged_result_list = []

    # 用於遍歷 left_part 的 index
    left_idx = 0
    # 用於遍歷 right_part 的 index
    right_idx = 0

    while True:
        # 不停 loop 遍歷 left_part 與 right_part，每一輪工作如下：
        #   (1) 提取 Element 做比較： left_part 在 left_idx 的 Elem vs. 、右兩邊的 Elem
        #   (2) 符合條件(ASC 時，看誰小，反之亦然)者，送入 list
        #   (3) 之後那一邊的 index +=1 (例如： 如果是 left_part[left_idx] 送入 list，則 left_idx +=1)

        # 結束條件
        if left_idx == left_length or right_idx == right_length:
            # 代表其中一個  已經走完了，所以先結束
            break

        # 提取初要比較的兩個 element ，依 data type 不同，取法可能不同，目前有 int, dict
        left_elem = left_part[left_idx]
        right_elem = right_part[right_idx]
        if isinstance(left_elem, int):
            left_elem_value = left_elem
            right_elem_value = right_elem
        elif isinstance(left_elem, dict):
            left_elem_value = left_elem.get(compare_field)
            right_elem_value = right_elem.get(compare_field)
        else:
            raise Exception("!?")

        if reverse:
            # DESC
            if left_elem_value > right_elem_value:
                # 左邊較大，左邊 append 進去
                merged_result_list.append(left_part[left_idx])
                left_idx += 1
            elif left_elem_value == right_elem_value:
                # 相等的情況 - 只 append left_part 左半部
                #   因為要保持 sort stability，不能讓右半部的 element 有機會比左半部的 element 早 append 進去 list。等左半部的全部 append 後，自然會輪到右半部的，這樣就會 stable
                merged_result_list.append(left_part[left_idx])
                left_idx += 1
            elif left_elem_value < right_elem_value:
                # 右邊較大，右邊 append 進去
                merged_result_list.append(right_part[right_idx])
                right_idx += 1
            else:
                # 無此情境
                raise Exception("!?")
        else:
            # ASC
            if left_elem_value < right_elem_value:
                # 左邊較小，左邊 append 進去
                merged_result_list.append(left_part[left_idx])
                left_idx += 1
            elif left_elem_value == right_elem_value:
                # 相等的情況 - 只 append left_part 左半部
                #   因為要保持 sort stability，不能讓右半部的 element 有機會比左半部的 element 早 append 進去 list。等左半部的全部 append 後，自然會輪到右半部的，這樣就會 stable
                merged_result_list.append(left_part[left_idx])
                left_idx += 1
            elif left_elem_value > right_elem_value:
                # 右邊較小，右邊 append 進去
                merged_result_list.append(right_part[right_idx])
                right_idx += 1
            else:
                # 無此情境
                raise Exception("!?")

    # 走到這裡時， left_part right_part 至少其中一個已全部 append 到 merged_result_list
    # left_part right_part 長度、分佈都可能不同，有可能其中一個排完了，另一個還剩一截。
    # 範例 (假設排 ASC)
    #   left_part  [1, 3]
    #   right_part [2, 6, 10]
    #  以這兩個範例， 結束 while loop 時，狀態如下
    #    merged_result_list 為 [1, 2, 3]
    #    left_idx 2
    #    right_idx 1  -> 所以 right_part 還剩 [6, 10] 還沒安排
    #
    # 此時也很簡單，就是把剩下的 [6, 10] 附加到 merged_result_list 右邊即可
    # 以排 ASC 為例，因為已經排好的 merged_result_list 一定是相對比較小的 Elements，而剩下的一定是相對比較大的，而且本身就是排序好的，因此直接 append 即可

    # 由於上面的 loop 最後有 += 1, 因此結束時會比最後一個 index 多 1，剛好等於 length 的長度，所以此處 condition 這樣寫
    if left_idx < left_length:
        # right_part 已經全部合併到新的list 而 left_part 還有剩，還沒結束的那部分，直接附加到 list 後面
        merged_result_list += left_part[left_idx:]
    if right_idx < right_length:
        # left_part 已經全部合併到新的 list 而 right_part 還有剩， 還沒結束的那部分，直接附加到 list 後面
        merged_result_list += right_part[right_idx:]

    return merged_result_list


def bubble_sort(data, reverse=False, field_name=None):
    """ 氣泡排序 (檢驗 stable 用) """
    result = copy.deepcopy(data)
    length = len(result)
    if length == 0:
        return []
    elif length == 1:
        return result

    # 到這邊時， length 至少為 2

    if isinstance(result[0], int):
        if reverse:
            # DESC
            for j in range(0, length - 1):
                for i in range(0, length - j - 1):
                    if result[i] < result[i + 1]:
                        result[i], result[i + 1] = result[i + 1], result[i]
        else:
            # ASC
            for j in range(0, length - 1):
                for i in range(0, length - j - 1):
                    if result[i] > result[i + 1]:
                        result[i], result[i + 1] = result[i + 1], result[i]
    elif isinstance(result[0], dict):
        if not field_name:
            raise Exception("field_name is required for dict item")
        if reverse:
            # DESC
            for j in range(0, length - 1):
                for i in range(0, length - j - 1):
                    if result[i].get(field_name) < result[i + 1].get(field_name):
                        result[i], result[i + 1] = result[i + 1], result[i]
        else:
            # ASC
            for j in range(0, length - 1):
                for i in range(0, length - j - 1):
                    if result[i].get(field_name) > result[i + 1].get(field_name):
                        result[i], result[i + 1] = result[i + 1], result[i]

    return result



class TestMethod(unittest.TestCase):
    int_samples = []
    dict_samples = []

    # def TestUtil_for2(self, data, test_func, compare_func, order):
    #     """ 測試 in-place 類型的 排序 function """
    #     print("  原本: %s" % str(data))
    #     test_func(data, compare_func)
    #     if order == "asc":
    #         self.assertEqual(data, sorted(data, reverse=False))
    #         print("  ASC 結果 %s\n" % (data,))
    #     else:
    #         self.assertEqual(data, sorted(data, reverse=True))
    #         print("  DESC 結果 %s\n" % (data,))

    def TestUtil_for1_int(self, data, test_func, reverse):
        """ 測試非 in-place 類型的 排序 function - int list"""
        print("  原本: %s" % str(data))
        result = test_func(data, reverse)
        sorted_data = sorted(data, reverse=reverse)
        self.assertEqual(result, sorted_data)
        print("  %s 結果 %s\n" % ("DESC" if reverse else "ASC", result))

    def TestUtil_for1_dict(self, data, test_func, reverse, field_name=None):
        """
        測試非 in-place 類型的 排序 function - dict list
        """
        print("  原本: %s" % str(data))
        result = test_func(data, reverse, field_name)
        if reverse:
            sorted_data = bubble_sort(data, reverse=True, field_name=field_name)
        else:
            sorted_data = bubble_sort(data, reverse=False, field_name=field_name)
        self.assertEqual(result, sorted_data)
        print("  %s 結果 %s\n" % ("DESC" if reverse else "ASC", result))

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
        data_rd5 = [i for i in range(-3, random.randint(7, 20))]
        random.shuffle(data_rd5)
        data_rd6 = [i for i in range(-5, random.randint(8, 16))] + [-3, -1, -1, 7, 7, 7, 7, 7]
        random.shuffle(data_rd6)
        self.int_samples = [
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
            {"name": "隨機不重複 0 ~ %s 打亂(含負數)" % N1, "list": data_rd5},
            {"name": "固定帶重複 elem", "list": [6, 3, 8, 6, 4, 6, 9, 3, 3, 3, 6, 9, 7, 5]},
            {"name": "隨機 兩倍重複  0~ %s" % N2, "list": data_rd2},
            {"name": "隨機 加一些相同 elem 進去", "list": data_rd3},
            {"name": "隨機 加一些相同 elem 進去 (含負數)", "list": data_rd6},
            {"name": "隨機 多加一些 elem 進去", "list": data_rd4},
            {"name": "全同 短", "list": [6 for i in range(0, random.randint(5, 10))]},
            {"name": "全同 長", "list": [11 for i in range(0, random.randint(24, 46))]},
        ]

        # 用 int 的 test case 產生一份 dict 版本的
        for int_sample in self.int_samples:
            int_list = int_sample["list"]
            dict_list = []
            sn = 1
            for int_item in int_list:
                dict_item = {
                    "id": int_item,
                    "sn": sn  # 確認 Stable Sort 用
                }
                dict_list.append(dict_item)
                sn += 1
            self.dict_samples.append({
                "name": int_sample["name"],
                "list": dict_list
            })


    def test_merge1_int(self):
        """ 測試 merge1 int """
        num = 1
        for sample in self.int_samples:
            # 每一個案例都測過以下八種情境排列組合
            print("--- merge1 int 第 %s 組測試案例 --- START " % num)
            print(sample["name"])
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_int(data=data, test_func=merge1, reverse=False)
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_int(data=data, test_func=merge1, reverse=True)
            print("%s 反轉" % (sample["name"],))
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_int(data=reversed_data, test_func=merge1, reverse=False)
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_int(data=reversed_data, test_func=merge1, reverse=True)
            print("--- merge1 int 第 %s 組測試案例 --- END\n" % num)
            num += 1

    def test_bubble_sort_int(self):
        """ 測試 bubble_sort int """
        num = 1
        for sample in self.int_samples:
            # 每一個案例都測過以下八種情境排列組合
            print("--- bubble_sort int 第 %s 組測試案例 START ---" % num)
            print(sample["name"])
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_int(data=data, test_func=bubble_sort, reverse=False)
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_int(data=data, test_func=bubble_sort, reverse=True)
            print("%s 反轉" % (sample["name"],))
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_int(data=reversed_data, test_func=bubble_sort, reverse=False)
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_int(data=reversed_data, test_func=bubble_sort, reverse=True)
            print("--- bubble_sort int 第 %s 組測試案例 END ---\n" % num)
            num += 1

    def test_merge1_dict(self):
        num = 1
        for sample in self.dict_samples:
            # 每一個案例都測過以下八種情境排列組合
            print("--- merge1 dict 第 %s 組測試案例 --- START " % num)
            print(sample["name"])
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_dict(data=data, test_func=merge1, reverse=False, field_name="id")
            data = list(copy.deepcopy(sample["list"]))
            self.TestUtil_for1_dict(data=data, test_func=merge1, reverse=True, field_name="id")
            print("%s 反轉" % (sample["name"],))
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_dict(data=reversed_data, test_func=merge1, reverse=False, field_name="id")
            reversed_data = list(reversed(copy.deepcopy(sample["list"])))
            self.TestUtil_for1_dict(data=reversed_data, test_func=merge1, reverse=True, field_name="id")
            print("--- merge1 dict 第 %s 組測試案例 --- END\n" % num)
            num += 1

if __name__ == "__main__":
    unittest.main()
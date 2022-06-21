"""
最長共用子字串 Longest Common Substring
"""
import unittest

def print_2D_list(data):
    """ print 2D list 用 """
    for col_item in data:
        print(col_item)

def get_longest_common_substring(a, b):
    """
    找出 the longest common substring
    """
    if not a or not b:
        return 0

    length_a = len(a)
    length_b = len(b)

    # 產生一張 length_a x length_b 的空 2D-Array
    comparison_table = [[0 for aa in a] for bb in b]
    # 這邊 a 字串為 row 向(橫)、b 字串為 column 向(直)

    # 紀錄 the longest common substring 相關資訊
    max_value = None  # the longest common substring 字串長度，所有符合項目內的最大值
    max_coordinate = (None, None)  # 上述最大值所在座標

    # dynamic programing 計算兩個字串共同點
    for j in range(0, length_b):
        for i in range(0, length_a):

            # a[i] vs b[j] 資訊 紀錄在 comparison_table[j][i]  (因為 comparison_table 第一個 index 是 column ，所以紀錄 b 字串的 index j)
            if a[i] == b[j]:
                # 當兩字母相等時
                if i == 0 or j == 0:
                    # 最左一排 col 或 最上一排 row, 他們沒有左上一格，所以直接 assign 1
                    comparison_table[j][i] = 1
                else:
                    # 不是最左一排 col 也不是 最上一排 row -> 取左上格的值 + 1 = 這格值
                    comparison_table[j][i] = comparison_table[j - 1][i - 1] + 1

                # 紀錄最大值，以及最大值所在座標
                if max_value is None:
                    max_value = comparison_table[j][i]
                    max_coordinate = (j, i)
                elif max_value is not None and max_value < comparison_table[j][i]:
                    max_value = comparison_table[j][i]
                    max_coordinate = (j, i)
            else:
                # 兩字母不相等，為 0
                comparison_table[j][i] = 0

    print(" +++ comparison table +++")
    print_2D_list(comparison_table)

    # 計算 the longest common substring 在 a 跟 b 字串的起迄 index 位置
    a_start = max_coordinate[1] - max_value + 1
    a_end = max_coordinate[1]
    b_start = max_coordinate[0] - max_value + 1
    b_end = max_coordinate[0]

    # 座標 (i, j) -> a 字串從 i 倒回 max_length 的長度，即為 the longest common substring
    a_common_str = a[a_start:a_end + 1]
    b_common_str = b[b_start:b_end + 1]

    return {
        "a": {
            "index": {"start": a_start, "end": a_end},  # the longest common substring 在 a 字串的起迄
            "length": max_value,        # the longest common substring 的長度
            "string": a_common_str      # the longest common substring
        },
        "b": {
            "index": {"start": b_start, "end": b_end},  # the longest common substring 在 b 字串的起迄
            "length": max_value,        # the longest common substring 的長度
            "string": b_common_str      # the longest common substring
        }
    }


def get_all_common_substring(a, b):
    """
    找出所有的 common substring (不只最長)
    """
    if not a or not b:
        return 0

    length_a = len(a)
    length_b = len(b)

    # 產生一張 length_a x length_b 的空 2D-Array
    comparison_table = [[0 for aa in a] for bb in b]
    # 這邊 a 字串為 row 向(橫)、b 字串為 column 向(直)

    # 紀錄 所有 common substring 相關資訊 (dynamic programing 算出的值只要 >= 1 的 (j, i) 關係，都記錄下來)
    commom_substrings = {}

    # dynamic programing 計算兩個字串共同點
    for j in range(0, length_b):
        for i in range(0, length_a):

            # a[i] vs b[j] 資訊 紀錄在 comparison_table[j][i]  (因為 comparison_table 第一個 index 是 column ，所以紀錄 b 字串的 index j)
            if a[i] == b[j]:
                # 兩字母相等時
                if i == 0 or j == 0:
                    # 最左一排 col 或 最上一排 row, 他們沒有左上一格，所以直接 assign 1
                    comparison_table[j][i] = 1
                    # 因為沒有左上一排，所以可以直接紀錄這個共用字串長度為 1 (僅目前，也許等到他的左下一格 +1 時，此共用字串長度就會加 1)
                    rec_idx = "%s-%s" % (i, j)  # key, 以起點座標組成字串 (不會有兩個字串共用起點)
                    commom_substrings[rec_idx] = {
                        "index_in_a": {"start": i, "end": i},  # a 字串的 起迄 index，因為目前所在座標是起點，所以起點跟終點都先設定為目前座標
                        "index_in_b": {"start": j, "end": j},  # b 字串的 起迄 index，因為目前所在座標是起點，所以起點跟終點都先設定為目前座標
                        "length": comparison_table[j][i],
                        "string": a[i]
                    }
                else:
                    # 不是最左一排 col 也不是 最上一排 row -> 取左上格的值 + 1 = 這格值
                    comparison_table[j][i] = comparison_table[j - 1][i - 1] + 1
                    if comparison_table[j - 1][i - 1] > 0:
                        # 左上一格 >= 1，所以這是某個共用字串的延續，也就是某個共用字串的第 2~N 個字元。

                        # 回減以計算出起點的座標，組成 index ，以快速找到要更新的那筆資料
                        rec_idx = "%s-%s" % (
                            i - comparison_table[j - 1][i - 1],
                            j - comparison_table[j - 1][i - 1]
                        )

                        if rec_idx in commom_substrings:
                            # update a 字串終點 index
                            commom_substrings[rec_idx]["index_in_a"]["end"] = i
                            # update b 字串終點 index
                            commom_substrings[rec_idx]["index_in_b"]["end"] = j
                            # 更新長度
                            commom_substrings[rec_idx]["length"] = comparison_table[j][i]
                            # 更新字串
                            commom_substrings[rec_idx]["string"] += a[i]
                        else:
                            raise Exception("不該發生的情境")
                    else:
                        # 左上一格 = 0，所以這是某個共用字串的起點
                        rec_idx = "%s-%s" % (i, j)  # key, 以起點座標組成字串 (不會有兩個字串共用起點)
                        # 儲存共用字串，起點就是這個座標，終點暫時也是這個座標，但之後有可能會更新
                        commom_substrings[rec_idx] = {
                            "index_in_a": {"start": i, "end": i},  # a 字串的 起迄 index，因為目前所在座標是起點，所以起點跟終點都先設定為目前座標
                            "index_in_b": {"start": j, "end": j},  # b 字串的 起迄 index，因為目前所在座標是起點，所以起點跟終點都先設定為目前座標
                            "length": comparison_table[j][i],
                            "string": a[i]
                        }
            else:
                # 兩字母不相等，為 0
                comparison_table[j][i] = 0

    print_2D_list(comparison_table)

    return list(commom_substrings.values())


class TestMethod(unittest.TestCase):

    cases = []

    def setUp(self):
        self.cases.append({
            "name": "書本範例 1",
            "a": "HISH",
            "b": "FISH",
            # 最長共用子字串的資料(包含該字串分別在 a 與 b 的起迄)
            "substring": {
                'a': {
                    'index': {"start": 1, "end": 3},  # a 字串的起迄 index
                    'length': 3,      # 字串長度
                    'string': 'ISH'   # 字串內容
                },
                'b': {
                    'index': {"start": 1, "end":3},  # b 字串的起迄 index
                    'length': 3,      # 字串長度
                    'string': 'ISH'   # 字串內容
                }
            },
            # 所有共用子字串的資料
            "all_substring": [
                # 第一組共用字串
                {
                    'index_in_a': {"start": 1, "end": 3},  # 共用字串在 a 字串的起迄 index
                    'index_in_b': {"start": 1, "end": 3},  # 共用字串在 b 字串的起迄 index
                    'length': 3,           # 共用字串長度
                    'string': 'ISH'        # 共用字串
                },
                # 第二組共用字串
                {
                    'index_in_a': {"start": 0, "end": 0},  # 共用字串在 a 字串的起迄 index
                    'index_in_b': {"start": 3, "end": 3},  # 共用字串在 b 字串的起迄 index
                    'length': 1,           # 共用字串長度
                    'string': 'H'          # 共用字串
                }
            ]
        })

        self.cases.append({
            "name": "改寫範例",
            "a": "ISHABK",
            "b": "FABISHK",
            "substring": {'a': {'index': {'start': 0, 'end': 2}, 'length': 3, 'string': 'ISH'}, 'b': {'index': {'start': 3, 'end': 5}, 'length': 3, 'string': 'ISH'}},
            "all_substring": [
                {'index_in_a': {'start': 3, 'end': 4}, 'index_in_b': {'start': 1, 'end': 2}, 'length': 2, 'string': 'AB'},
                {'index_in_a': {'start': 0, 'end': 2}, 'index_in_b': {'start': 3, 'end': 5}, 'length': 3, 'string': 'ISH'},
                {'index_in_a': {'start': 5, 'end': 5}, 'index_in_b': {'start': 6, 'end': 6}, 'length': 1, 'string': 'K'}
            ],
        })

        self.cases.append({
            "name": "範例 2",
            "a": "IEOUEOOEW",
            "b": "EOOEWIEOU",
            "substring":  {
                'a': {'index': {'start': 4, 'end': 8}, 'length': 5, 'string': 'EOOEW'},
                'b': {'index': {'start': 0, 'end': 4}, 'length': 5, 'string': 'EOOEW'}
            },
            "all_substring": [
                {'index_in_a': {'start': 1, 'end': 2}, 'index_in_b': {'start': 0, 'end': 1}, 'length': 2, 'string': 'EO'},
                {'index_in_a': {'start': 4, 'end': 8}, 'index_in_b': {'start': 0, 'end': 4}, 'length': 5, 'string': 'EOOEW'},
                {'index_in_a': {'start': 7, 'end': 7}, 'index_in_b': {'start': 0, 'end': 0}, 'length': 1, 'string': 'E'},
                {'index_in_a': {'start': 6, 'end': 6}, 'index_in_b': {'start': 1, 'end': 1}, 'length': 1, 'string': 'O'},
                {'index_in_a': {'start': 2, 'end': 2}, 'index_in_b': {'start': 2, 'end': 2}, 'length': 1, 'string': 'O'},
                {'index_in_a': {'start': 5, 'end': 5}, 'index_in_b': {'start': 2, 'end': 2}, 'length': 1, 'string': 'O'},
                {'index_in_a': {'start': 1, 'end': 1}, 'index_in_b': {'start': 3, 'end': 3}, 'length': 1, 'string': 'E'},
                {'index_in_a': {'start': 4, 'end': 4}, 'index_in_b': {'start': 3, 'end': 3}, 'length': 1, 'string': 'E'},
                {'index_in_a': {'start': 0, 'end': 3}, 'index_in_b': {'start': 5, 'end': 8}, 'length': 4, 'string': 'IEOU'},
                {'index_in_a': {'start': 4, 'end': 5}, 'index_in_b': {'start': 6, 'end': 7}, 'length': 2, 'string': 'EO'},
                {'index_in_a': {'start': 7, 'end': 7}, 'index_in_b': {'start': 6, 'end': 6}, 'length': 1, 'string': 'E'},
                {'index_in_a': {'start': 6, 'end': 6}, 'index_in_b': {'start': 7, 'end': 7}, 'length': 1, 'string': 'O'}
            ],
        })

        self.cases.append({
            "name": "範例 3 把某個字串前半與後半對調，使出現兩組一樣長的共用字串",
            "comment": "兩層 for loop 特性， b 字串比較小 index 位置的公用字串先被搜到",
            "a": "IEILBWQD",
            "b": "BWQDIEIL",
            "substring": {
                'a': {'index': {'start': 4, 'end': 7}, 'length': 4, 'string': 'BWQD'},
                'b': {'index': {'start': 0, 'end': 3}, 'length': 4, 'string': 'BWQD'}
            },
            "all_substring": [
                {'index_in_a': {'start': 4, 'end': 7}, 'index_in_b': {'start': 0, 'end': 3}, 'length': 4, 'string': 'BWQD'},
                {'index_in_a': {'start': 0, 'end': 3}, 'index_in_b': {'start': 4, 'end': 7}, 'length': 4, 'string': 'IEIL'},
                {'index_in_a': {'start': 2, 'end': 2}, 'index_in_b': {'start': 4, 'end': 4}, 'length': 1, 'string': 'I'},
                {'index_in_a': {'start': 0, 'end': 0}, 'index_in_b': {'start': 6, 'end': 6}, 'length': 1, 'string': 'I'}
            ]
,
        })
        self.cases.append({
            "name": "範例 4 https://www.geeksforgeeks.org/longest-common-substring-dp-29/",
            "comment": "",
            "a": "abcdxyz",
            "b": "xyzabcd",
            "substring": {
                'a': {'index': {'start': 0, 'end': 3}, 'length': 4, 'string': 'abcd'},
                'b': {'index': {'start': 3, 'end': 6}, 'length': 4, 'string': 'abcd'}
            },
            "all_substring": [
                {'index_in_a': {'start': 4, 'end': 6}, 'index_in_b': {'start': 0, 'end': 2}, 'length': 3, 'string': 'xyz'},
                {'index_in_a': {'start': 0, 'end': 3}, 'index_in_b': {'start': 3, 'end': 6}, 'length': 4, 'string': 'abcd'}
            ],
        })
        self.cases.append({
            "name": "範例 5 https://www.geeksforgeeks.org/longest-common-substring-dp-29/",
            "comment": "a, b 字串比較小 index 位置的 Geeks 先被搜到",
            "a": "GeeksforGeeks",
            "b": "GeeksQuiz",
            "substring": {
                'a': {'index': {'start': 0, 'end': 4}, 'length': 5, 'string': 'Geeks'},
                'b': {'index': {'start': 0, 'end': 4}, 'length': 5, 'string': 'Geeks'}
            },
            "all_substring": [
                {'index_in_a': {'start': 0, 'end': 4}, 'index_in_b': {'start': 0, 'end': 4}, 'length': 5, 'string': 'Geeks'},
                {'index_in_a': {'start': 8, 'end': 12}, 'index_in_b': {'start': 0, 'end': 4}, 'length': 5, 'string': 'Geeks'},
                {'index_in_a': {'start': 2, 'end': 2}, 'index_in_b': {'start': 1, 'end': 1}, 'length': 1, 'string': 'e'},
                {'index_in_a': {'start': 10, 'end': 10}, 'index_in_b': {'start': 1, 'end': 1}, 'length': 1, 'string': 'e'},
                {'index_in_a': {'start': 1, 'end': 1}, 'index_in_b': {'start': 2, 'end': 2}, 'length': 1, 'string': 'e'},
                {'index_in_a': {'start': 9, 'end': 9}, 'index_in_b': {'start': 2, 'end': 2}, 'length': 1, 'string': 'e'}
            ]
,
        })

    def test_substring(self):
        for case_data in self.cases:
            print(" 案例 %s" % case_data["name"])

            # 最長共用子字串
            substring_result = get_longest_common_substring(a=case_data["a"], b=case_data["b"])
            self.assertEqual(substring_result, case_data["substring"])
            print("result ok %s" % (substring_result,))

            # 所有共用子字串
            substring_result = get_all_common_substring(a=case_data["a"], b=case_data["b"])
            self.assertEqual(substring_result, case_data["all_substring"])

            print("result ok %s" % (substring_result,))


if __name__ == "__main__":
    unittest.main()

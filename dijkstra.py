"""
Dijkstra
"""
import unittest, random, copy, pprint
from collections import OrderedDict

# print 較詳細 log 用
DEBUG = False

def get_next_node_has_lowest_cost(process_table):
    """
    在目前所有節點內，找尋 Cost 最低者
    若找到後，會將該節點標記為『已確認』
    """
    min_cost = None
    next_node = None
    for p_node, p_record in process_table.items():
        if process_table[p_node]["checked"] is True:
            # 如果該『鄰近節點』已經確認了，就不納入下一個
            continue

        if min_cost is None:
            # 還沒有 Cost
            min_cost = p_record["cost"]
            next_node = p_node
        elif min_cost is not None and min_cost > p_record["cost"]:
            # 找到一個更小的 Cost
            min_cost = p_record["cost"]
            next_node = p_node

    if next_node is not None:
        # 找到 minimum cost 的 下一點，並將下一點標記為『已確認』
        process_table[next_node]["checked"] = True  # dict mutable 所以直接改就好
        if DEBUG:
            print("找到下一最小 Cost 節點 %s (Cost: %s) ，標記為『已確認』" % (next_node, min_cost))

    return next_node, min_cost


def dijkstra1(graph_data, start_node, end_node):
    """
    參考教學： https://www.youtube.com/watch?v=JLARzu7coEs

    return
    (
        res,     # 最後結果, OrderedDict 形式
        res_str  # 最後結果, str 形式
    )
    """
    pp = pprint.PrettyPrinter(indent=4)

    # 產生所有節點的 歷程紀錄表 (dict of node) ，格式類似下面這樣
    #  Node     Cost      Parent     Checked(是否已確認)
    # --------------------------------------
    #    0        0        None        True
    #    1       inf        0          False
    #    2        5         3          True
    process_table = {}
    for node in graph_data:
        process_table[node] = {
            "cost": float("inf"),
            "parent": None,
            "checked": False
        }

    # 標記起點
    this_node = start_node
    process_table[this_node]["cost"] = 0  # 起點 Cost 為 0
    print("起點 %s" % (this_node,))
    print("  +++ process_table 初始狀態 +++")
    pp.pprint(process_table)

    # 尋找下一節點 ＆ 標記『已確認』
    this_node, _ = get_next_node_has_lowest_cost(process_table)

    while True:
        # this_node 代表『目前節點』
        if DEBUG:
            print("\n『目前節點』： %s" % (this_node,))

        # 目前節點可以前往的『鄰近節點』 Ｎ 個
        neighbors = graph_data[this_node]
        if DEBUG:
            print(" -> 找到 %s 的鄰近節點群: %s" % (
                this_node, str([neighbor for neighbor in neighbors])
            ))

        # for loop 處理這 Ｎ 個『鄰近節點』
        for neighbor_node, edge_weight in neighbors.items():
            if process_table[neighbor_node]["checked"] is True:
                # 如果該『鄰近節點』已經確認了，就不處理
                if DEBUG:
                    print("   -> %s 的 鄰近節點 %s 已確認. skip" % (this_node, neighbor_node))
                continue

            # 該『鄰近節點』成本： 『目前節點』成本 ＋ 前往該節點的 Edge 的 Weight
            cost_go_to_neighbor = edge_weight + process_table[this_node]["cost"]
            if DEBUG:
                print(
                    "   -> %s 的 鄰近節點 %s 的 Cost 為 %s + %s = %s" % (
                    this_node, neighbor_node, edge_weight, process_table[this_node]["cost"], cost_go_to_neighbor
                ))

            if process_table[neighbor_node]["cost"] > cost_go_to_neighbor:
                # 如果該『鄰近節點』紀錄中的 Cost 比 計算出來的 Cost 還大，就更新該 Cost
                if DEBUG:
                    print("     -> Cost %s 比 table 內 %s 節點的 Cost %s 小，所以節點 %s 的 Cost 更新為 %s" % (
                        cost_go_to_neighbor, neighbor_node, process_table[neighbor_node]["cost"], neighbor_node, cost_go_to_neighbor
                    ))
                process_table[neighbor_node]["cost"] = cost_go_to_neighbor
                process_table[neighbor_node]["parent"] = this_node
            else:
                if DEBUG:
                    print("     -> Cost %s 沒有比 table 內的 Cost %s 小, skip" % (
                        cost_go_to_neighbor, process_table[neighbor_node]["cost"]
                    ))
        if DEBUG:
            print("  +++ current process table +++")
            pp.pprint(process_table)
            print("  +++ +++ +++ +++ +++ +++ +++")

        next_node, next_node_cost = get_next_node_has_lowest_cost(process_table)
        if next_node is not None:
            this_node = next_node
            if this_node == end_node:
                # 如果 this_node 就是終點，那就可以停了
                if DEBUG:
                    print("   -> 抵達終點 %s" % this_node)
                break
        else:
            # 無下一點，代表全部節點都『已確認』，可以停了
            if DEBUG:
                print(" -> 無下一點，代表全部節點都『已確認』，上次最後處理的 Node 為 %s" % this_node)
            break

    print("  +++ process_table 最終結果 +++")
    pp.pprint(process_table)

    # 如果有輸入的終點，就用輸入的終點，否則就用 最後處理的那個節點，也就是 this_node
    #   -> 根據上面 while loop，如果下一個節點 next_node 為 None，那 this_node 就不會被覆寫，會保持為之前最後一個處理的節點
    end_node = end_node if end_node else this_node

    # 準備表示結果
    result = OrderedDict()
    result_node = end_node
    while True:
        result[result_node] = process_table[result_node]["cost"]
        result_node = process_table[result_node]["parent"]
        if result_node is None:
            # 追到最頂節點為止
            break

    return result, process_table



class TestMethod(unittest.TestCase):
    cases = []

    def setUp(self):
        # 參考教學： https://www.youtube.com/watch?v=JLARzu7coEs
        # 教學案例為一無向圖
        case1 = {
            "name": "案例 1 原本教學案例",
            "graph": {
                0: {1: 4, 7: 8},
                1: {0: 4, 7: 11, 2: 8},
                2: {1: 8, 8: 2, 5: 4, 3: 7},
                3: {2: 7, 5: 14, 4: 9},
                4: {3: 9, 5: 10},
                5: {6: 2, 2: 4, 3: 14, 4: 10},
                6: {7: 1, 8: 6, 5: 2},
                7: {0: 8, 1: 11, 8: 7, 6: 1},
                8: {7: 7, 2: 2, 6: 7},
            },  # 類似 adjacency list 的記法
            "start": 0,
            "end": 4,
            "answer": OrderedDict([(4, 21), (5, 11), (6, 9), (7, 8), (0, 0)])
        }
        self.cases.append(case1)

        case2 = copy.deepcopy(case1)
        case2["name"] = "案例 2 - 教學案例，但沒標記終點"
        case2["end"] = None  # 同一份圖形，但不指定終點，把整張圖都算過
        self.cases.append(case2)

        case3 = copy.deepcopy(case1)
        case3["name"] = "案例 3 - 教學案例，標記其他點為終點"
        case3["end"] = 3  # 同一份圖形，但標記其他點為終點
        case3["answer"] = OrderedDict([(3, 19), (2, 12), (1, 4), (0, 0)])
        self.cases.append(case3)

        case4 = copy.deepcopy(case1)
        case4["name"] = "案例 4 - 教學案例，標記其他點為終點"
        case4["end"] = 5  # 同一份圖形，但標記其他點為終點
        case4["answer"] = OrderedDict([(5, 11), (6, 9), (7, 8), (0, 0)])
        self.cases.append(case4)

        case4 = copy.deepcopy(case1)
        case4["name"] = "案例 5 - 教學案例，標記其他點為終點"
        case4["end"] = 8  # 同一份圖形，但標記其他點為終點
        case4["answer"] =OrderedDict([(8, 14), (2, 12), (1, 4), (0, 0)])
        self.cases.append(case4)

    def test_dijkstra1(self):
        for case_data in self.cases:
            print(" 案例 %s" % case_data["name"])
            result, process_table = dijkstra1(
                graph_data=case_data["graph"],
                start_node=case_data["start"],
                end_node=case_data["end"]
            )

            self.assertEqual(result, case_data["answer"])
            print("最短路徑 by Dijkstra: %s" % str(result))
        pass

if __name__ == "__main__":
    unittest.main()

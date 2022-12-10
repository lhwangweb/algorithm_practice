"""
Power Set 練習
"""
import copy

def backtracking(all_subsets, one_subset, index, population):
  if not population:
     return 

  if index > len(population):
     # 走到 population 樣本使用完畢後的下一個遞迴將滿足此條件
     return

  # print(one_subset)
  all_subsets.append(copy.deepcopy(one_subset))

  for i in range(index, len(population)):
    # 加入這個 index 的 element
    one_subset.append(population[i])

    # 走進下一層 子集合，也就是範圍改成『 i + 1 ~ 結尾 所有的子集合』
    backtracking(all_subsets, one_subset, i+1, population)

    # 除去最後一個 element，要讓這個位置的 element 依 for loop 不斷替換，例如，這個 for loop 可能是在 子集合結果的第 0 位置，這個位置依序可以是 a", "b", "c", "d"
    one_subset.pop(-1)
  return


if __name__ == "__main__":
    # 求此 population 所有子集合
    population = ['a', 'b', 'c']
    one_subset = []
    all_subsets = []
    backtracking(all_subsets, one_subset, 0, population)
    print(all_subsets)


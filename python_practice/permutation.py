"""
Permutation 練習
"""
import copy

def backtracking(all, one, population, used):
    if len(one) == len(population):
        # 每次 one 填滿時，就是完成了其中一種排列組合， append 到結果去
        all.append(copy.deepcopy(one))  # python 特性，直接 append 變數只會 append reference ，所以用 deepcopy 複製一份副本
        return

    for i in range(0, len(population)):
        # if used[i] is True:
            # continue

        # 加入
        one.append(population[i])
        used[i] = True

        # 遞迴
        backtracking(all, one, population, used)

        # 移除
        one.pop()
        used[i] = False

    return


if __name__ == "__main__":
    # 求此 population 所有排列組合
    population = ['1', '2', '3']
    used = [False for p in population]
    one = []
    all = []
    backtracking(all, one, population, used)
    print(len(all))


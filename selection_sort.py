"""
選擇排序 練習
"""
import sys, random, copy


def generate_random_list(N):
    if not isinstance(N, int):
        return []
    data = [i for i in range(0, N)]
    random.shuffle(data)
    return data


def take_max_element_index(data):
    if not isinstance(data, list):
        return None

    if len(data) == 0:
        return None

    if len(data) == 1:
        # 只有一個，當然是 0
        return 0

    max_elem = data[0]
    max_elem_idx = 0
    for idx in range(1, len(data)):
        if max_elem < data[idx]:
            max_elem = data[idx]
            max_elem_idx = idx

    return max_elem_idx


def take_min_element_index(data):
    if not isinstance(data, list):
        return None

    if len(data) == 0:
        return None

    if len(data) == 1:
        # 只有一個，當然是 0
        return 0

    min_elem = data[0]
    min_elem_idx = 0
    for idx in range(1, len(data)):
        if min_elem > data[idx]:
            min_elem = data[idx]
            min_elem_idx = idx

    return min_elem_idx


if __name__ == "__main__":
    # 設定 N
    try:
        N = int(sys.argv[1])
    except:
        print("輸入長度錯誤，這邊將展示長度 30 int list 排序")
        N = 30

    # N = 30
    random_int_list = generate_random_list(N=N)
    print("ORIGINAL: %s" % str(random_int_list))

    random_int_list1 = copy.deepcopy(random_int_list)
    random_int_list2 = copy.deepcopy(random_int_list)

    sorted_asc_list = []
    for j in range(0, N):
        idx = take_min_element_index(data=random_int_list1)
        elem = random_int_list1.pop(idx)
        sorted_asc_list.append(elem)

    print("ASC: %s" % str(sorted_asc_list))

    sorted_desc_list = []
    for j in range(0, N):
        idx = take_max_element_index(data=random_int_list2)
        elem = random_int_list2.pop(idx)
        sorted_desc_list.append(elem)

    print("DESC: %s" % str(sorted_desc_list))

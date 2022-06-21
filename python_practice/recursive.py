def get_len_by_recursive(data, idx):
    if not data:
        return idx
    return get_len_by_recursive(data[1:], idx + 1)


def sum_by_recursive(data):
    if not data:
        return 0
    return data[0] + sum_by_recursive(data[1:])


def sum_by_loop(data):
    sum = 0
    for i in data:
        sum += i
    return sum


def find_max_by_recursive(data):
    if not data:
        return None
    res = find_max_by_recursive(data[1:])
    if res is None:
        return data[0]
    else:
        return data[0] if data[0] > res else res


if __name__ == "__main__":
    data = [1, 9, 3, 5, 6, 37, 7, 6, 8, 2, 16, 8, 18, 4, 2, 7, 4]
    # sum(data)
    length = get_len_by_recursive(data, 0)
    print(length)

    sum = sum_by_recursive(data)
    print(sum)

    sum2 = sum_by_loop(data)
    print(sum2)

    max_num = find_max_by_recursive(data)
    print(max_num)

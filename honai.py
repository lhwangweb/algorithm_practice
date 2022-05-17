"""
河內塔練息
"""

import copy, sys, math


class HonaiPillar(object):
    """
    河內塔柱子
        以 Stack 設計柱子, 以 int 為 Element
        int 數字大小，代表盤子的寬度
    """
    # 盤子堆疊
    #   以 int list 代表 盤子疊起來的狀態
    #   list index 0 為柱子底部
    #   例如 [3,2,1] 代表柱子上目前疊了三個盤子，盤子寬度分別為 3,2,1
    data = []

    # 柱子的名稱(與柱子的角色無關，用以辨識目前盤子套到哪支柱子上)
    name = ""

    def __init__(self, name=""):
        """ 柱子初始化 """
        self.name = name
        self.data = []

    def pop(self, index=None):
        """ 彈出指定 index 盤子，預設是最上方(index -1) """
        if len(self.data) <= 0:
            return None
        # 若 >= 0 就用，否則為 -1
        index = index if isinstance(index, int) and index >= 0 else -1
        return self.data.pop(int(index))

    def push(self, elem):
        """ 放入盤子， 由於河內塔規則，因此這裡會判斷： 放入的盤子一定要小於柱子現存最上方的盤子 """
        if not isinstance(elem, int):
            raise Exception('! 格式不符，請傳入 int 以代表盤子，int 數字即盤子寬度')

        if self.size() > 0:
            # 如果此柱已有盤子，就比較『放入的盤子』與『目前最上方的盤子』大小是否符合河內塔規則
            this_max_plate = self.peak()
            if isinstance(this_max_plate, int) and this_max_plate < elem:
                msg = '! 發生『放入的盤子比已有的大』 - 寬度 %s 不可疊在寬度 %s 上' % (elem, self.peak())
                self.print()
                raise Exception(msg)
            elif isinstance(this_max_plate, int) and this_max_plate == elem:
                msg = '! 發生『兩個盤子寬度相等』 放入盤子寬度: %s' % (elem,)
                self.print()
                raise Exception(msg)
        return self.data.append(elem)

    def print(self):
        """ 印出目前柱子狀況 """
        print("Pillar: %s, Data:%s" % (self.name, self.data))
        return None

    def size(self):
        """ 目前盤子數 """
        return len(self.data)

    def isEmpty(self):
        """ 是否為空 """
        return len(self.data) <= 0

    def clear(self):
        """ 清空盤子"""
        self.data = []
        return None

    def peak(self):
        """ 取得此柱子目前最上方盤子 """
        if len(self.data) < 1:
            return None
        else:
            return self.data[len(self.data) - 1]


def honai_hardcode(A_pillar, B_pillar, C_pillar):
    """ 硬寫 1,2,3 層的 cases，這是用於分析與拆解 """
    # 空
    if A_pillar.isEmpty():
        return None

    # 一個盤子
    if A_pillar.size() == 1:
        # 第一步 - 從第Ａ柱(起始柱)提出 1 -> 放到第Ｃ柱(終點柱)
        plate = A_pillar.pop()
        C_pillar.puhs(plate)
    # 兩個盤子
    elif A_pillar.size() == 2:
        # 目標： 把兩個盤子從第Ａ柱(起始柱)移到第Ｃ柱，第Ｂ柱為暫存站
        # 第一步 - 從第Ａ柱(起始柱)提出 1 -> 放到第Ｂ柱(暫存柱)
        plate1 = A_pillar.pop()
        B_pillar.push(plate1)
        # 第二步 - 從第Ａ柱(起始柱)提出 2 -> 放到第Ｃ柱(終點柱)
        plate2 = A_pillar.pop()
        C_pillar.push(plate2)
        # 第三步 - 從第Ｂ柱(暫存柱)提出 1 -> 放到第Ｃ柱(終點柱)
        plate1 = B_pillar.pop()
        C_pillar.push(plate1)
    # 三個盤子
    elif A_pillar.size() == 3:
        # 可以發現這七步驟，其實前面『三步』就是在解兩層河內塔，目標是把兩個盤從第Ａ柱一到第Ｂ柱(此時第Ｃ柱會作為暫存柱)，也就是通常提到的河內塔遞迴精神: 『base case 為兩層河內塔』
        # 1.
        plate1 = A_pillar.pop()
        C_pillar.push(plate1)
        # 2.
        plate2 = A_pillar.pop()
        B_pillar.push(plate2)
        # 3.
        plate1 = C_pillar.pop()
        B_pillar.push(plate1)

        # 4.
        plate3 = A_pillar.pop()
        C_pillar.push(plate3)

        # 最後『三步』，也是在解兩層河內塔，目標是把兩個盤從第Ｂ柱移到第Ｃ柱(此時第Ａ柱會作為暫存柱)
        # 5.
        plate1 = B_pillar.pop()
        A_pillar.push(plate1)
        # 6.
        plate2 = B_pillar.pop()
        C_pillar.push(plate2)
        # 7.
        plate1 = A_pillar.pop()
        C_pillar.push(plate1)

        # 討論一： 步數
        #   所以河內塔 N 層的步驟數，就是 N-1 層的步數 * 2 加 1 (以解法的遞迴步驟來直接計算)
        # N = 1 時，需要 1 步
        # N = 2 時，需要 1*2 + 1 = 2^1 + 1 = 3
        # N = 3 時，需要 (1*2+1)*2 + 1 = 2^2 + 2^1 + 1 = 7
        # N = 4 時，需要 ((1*2+1)*2+1)*2 + 1 = 2^3 + 2^2 + 2^1 + 1 = 15
        #
        # 想把上面的規則推導為一般看到的河內塔公式，則使用 二項式定理的逆推 (先拿一張紙來把 2^N - 1 做二項式展開就懂了)
        # 所以就是 2^N-1 + .... 2^1 + 1 = (2-1) * (2^N-1 + .... 2^1 + 1) = 2^N - 1

        # 討論二： 解 N 層河內塔，永遠可以拆成三個子任務 (以下稱三根柱子為 起始柱、暫存柱、終點柱)：
        #  子任務一： 解 N-1 層河內塔一輪
        #    - 將 N-1 層盤子，從起始柱移到暫存柱 (需下向遞迴運算)
        #    - 對這個子任務來講，起始柱還是起始柱，但那支暫存柱的角色，暫時要變為終點柱，而終點柱則暫時變更為暫存柱，簡化的說，暫存柱與終點柱角色會交換一下
        #    - 沒錯 ... 若遞迴再往下一層繼續拆子任務時，對該子任務來說，柱子角色就會再交換一下
        #
        #  子任務二： 把該層最大盤子 N 從起點柱 移到 終點柱
        #    - 不需要遞迴多次，只有一動
        #
        #  子任務三： 解 N-1 層河內塔一輪
        #    - 將 N-1 層盤子，從暫存柱移到終點柱(需下向遞迴運算)
        #    - 對這個子任務來講，暫存柱與起始柱角色交換
        #
        # 討論三： base case 可以拆到最低是『一層河內塔』
        #   兩層河內塔的三步，一樣可以對應上面的三子任務拆解
        #     (1) 盤子1 A -> B 是個一層河內塔，此時 A 為起始柱，B 為終點柱，暫存柱 C 沒有工作。
        #     (2) 盤子2 A -> C 以二層河內塔觀點，這是上面的第二步『把該層最大盤子移到終點柱』，但同樣，這也是個一層河內塔： A 為起始柱，C 為終點柱，暫存柱 B 沒有工作
        #     (3) 盤子1 B -> C 是個一層河內塔，此時 B 為起始柱，C 為終點柱，暫存柱 A 沒有工作
        #
        #   所以
        #     - 最終可以拆到以 1 層河內塔為 Base Case
        #     - 從 N 層 到 2 層，都可以拆成上面的標準三個子任務，其中子任務一跟三要遞迴，子任務二不用
        return None


def honai_base(layer, A_pillar, B_pillar, C_pillar, accum_count):
    """
    河內塔遞迴
    Base Case 為一層河內塔解
    :param layer:
    :param A_pillar:
    :param B_pillar:
    :param C_pillar:
    :param accum_count:
    :return:
    """
    if layer == 1:
        # Base Case 動作: 起始柱 -> 終點柱 (對，暫存柱這時候沒事做)
        plate1 = A_pillar.pop()
        C_pillar.push(plate1)

        # 步數累計
        accum_count += 1

        # print 移動訊息
        print("No.%s Step: 盤子 %s 由 %s -> %s" % (accum_count, plate1, A_pillar.name, C_pillar.name,))

        # === print 此刻 A,B,C 三隻柱子 的盤子狀態 ===
        # 這邊寫這麼多只是為了固定 print A,B,C 三隻柱子的順序（由於每往下遞迴一層時，柱子會透過 argument 交換，以達成角色交換的目的，因此這時候 A_pillar 這個變數內的柱子不一定是 A 柱，其他 B,C 同理）
        if A_pillar.name == "A":
            Adata = A_pillar.data
        elif B_pillar.name == "A":
            Adata = B_pillar.data
        elif C_pillar.name == "A":
            Adata = C_pillar.data
        if A_pillar.name == "B":
            Bdata = A_pillar.data
        elif B_pillar.name == "B":
            Bdata = B_pillar.data
        elif C_pillar.name == "B":
            Bdata = C_pillar.data
        if A_pillar.name == "C":
            Cdata = A_pillar.data
        elif B_pillar.name == "C":
            Cdata = B_pillar.data
        elif C_pillar.name == "C":
            Cdata = C_pillar.data
        print("  Pillar A - Data: %s" % (str(Adata),))
        print("  Pillar B - Data: %s" % (str(Bdata),))
        print("  Pillar C - Data: %s" % (str(Cdata),))
        # === print 此刻 A,B,C 三隻柱子 的盤子狀態 ===

        return A_pillar, B_pillar, C_pillar, accum_count

    # 子任務一: 把 N-1 個盤子，從起始柱 A 移到 暫存柱 B。 在這個子任務範圍內，暫存柱就是這個子任務的終點柱，所以 pass 的 arguments 順序是 A,C,B，B 跟 C 交換角色。
    A_pillar, C_pillar, B_pillar, accum_count = honai_base(
        layer - 1, A_pillar, C_pillar, B_pillar, accum_count
    )

    # 到這裡時，盤子堆疊狀態應該是這樣：
    #   起點柱 [這層河內塔最大的盤子(即 N)]
    #   暫存柱 [N-1 個盤子]
    #   終點柱 [空]

    # 子任務二: 把這一層最大的盤子 N ，從 起點柱 A 移到 終點柱 C。 由於僅在這一層執行，僅有一動，沒有要往下遞迴，所以 layer=1，並且 A,B,C 順序不變，柱子的角色沒有任何交換。
    A_pillar, B_pillar, C_pillar, accum_count = honai_base(
        1, A_pillar, B_pillar, C_pillar, accum_count
    )

    # 到這裡時，盤子堆疊狀態應該是這樣：
    #   起點柱 [空]
    #   暫存柱 [N-1 個盤子]
    #   終點柱 [這層河內塔最大的盤子(即 N)]

    # 子任務三: 把 N-1 個盤子，從 暫存柱 B 移到 終點柱 C。 在這個子任務範圍內，暫存柱就是這個子任務的起始柱，所以 pass 的 arguments 順序是 B,A,C，A 跟 B 交換角色。
    B_pillar, A_pillar, C_pillar, accum_count = honai_base(
        layer - 1, B_pillar, A_pillar, C_pillar, accum_count
    )

    return A_pillar, B_pillar, C_pillar, accum_count


def honai_main(total_layer_num=1):
    """ Main """
    total_layer_num = int(total_layer_num)

    if total_layer_num <= 0:
        print("輸入層數至少為 1")
        return

    # 建立三根柱子
    A_pillar = HonaiPillar(name="A")
    B_pillar = HonaiPillar(name="B")
    C_pillar = HonaiPillar(name="C")

    # 塞入 Ｎ 個盤子
    for plate in range(total_layer_num, 0, -1):
        A_pillar.push(plate)

    # # Hard Code 版(只解前三層)
    # honai_hardcode(A_pillar, B_pillar, C_pillar)

    # 初始層數、步數
    layer = A_pillar.size()
    accum_count = 0

    print("解 %s 層河內塔\n" % (layer,))
    print("初始盤子堆疊狀態:")
    A_pillar.print()
    B_pillar.print()
    C_pillar.print()

    print('\n - - - start - - -\n')
    A_pillar, B_pillar, C_pillar, accum_count = honai_base(layer, A_pillar, B_pillar, C_pillar, accum_count)
    print('\n - - -  end  - - -\n')

    # 驗算步數
    correct_step_number = int(math.pow(2, layer) - 1)
    result_msg = "步數正確，符合 2^%s - 1 = %s" % (layer, correct_step_number) \
        if accum_count == correct_step_number \
        else "步數錯誤，應為 2^%s - 1 = %s" % (layer, correct_step_number)

    # Final Result Print
    print("完成！ 共花費 %s 步 (%s) " % (accum_count, result_msg))
    print("完成的盤子堆疊狀態:")
    A_pillar.print()
    B_pillar.print()
    C_pillar.print()


if __name__ == "__main__":
    # 設定 N 層
    try:
        total_layer_num = int(sys.argv[1])
    except:
        print("輸入層數錯誤，這邊將展示 3 層河內塔")
        total_layer_num = 3

    honai_main(total_layer_num=total_layer_num)

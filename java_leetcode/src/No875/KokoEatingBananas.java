package No875;
import java.util.Arrays;
import java.util.Collections;

/**
 * 875. Koko Eating Bananas
 *
 * 第一版 暴力解法 從最小速度每次猜 +=1
 *  -> Leetcode 不過 Time Limit Exceeded  (https://leetcode.com/submissions/detail/805311556/)
 *  檢討： 每次 +=1 太慢，改 binary search
 *
 * 第二版 binary search 猜
 *    Runtime: 108 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.7 MB, less than 67.00% of Java online submissions for Koko Eating Bananas.
 *  檢討： binary search 還是太慢？ 有更好的演算法？ 還是 code 太冗？
 *
 * 第三版 減少除法版
 *     Runtime: 83 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 54.3 MB, less than 20.78% of Java online submissions for Koko Eating Bananas.
 *  檢討： 更快(可惜進步的百分比沒多少)，但減少迴圈中的除法還是非常必要的優化 但可能是因為需要不斷的轉型別，記憶體耗用更大了！
 *
 *  第四版 簡化初始的速度上限值猜法，改成固定值，不去找 piles 內的最大值
 *     Runtime: 84 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.6 MB, less than 71.80% of Java online submissions for Koko Eating Bananas.
 *  檢討： 節省不少記憶體！
 *
 * 第五版 嘗試一開始 init 就把上下限都猜
 *     Runtime: 99 ms, faster than 5.06% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.8 MB, less than 54.13% of Java online submissions for Koko Eating Bananas.
 *  檢討： 看起來沒有幫助，反而平均起來還比較慢
 *
 *  第六版 把一些不必要的 long 去掉
 *    Runtime: 20 ms, faster than 83.20% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.2 MB, less than 79.83% of Java online submissions for Koko Eating Bananas.
 *
 *  檢討： 原本只是想看能否改善記憶體耗用，卻意外增進了速度
 *
 *  第七次 試試看如果還是用除法
 *
 *    Runtime: 24 ms, faster than 72.64% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.3 MB, less than 79.83% of Java online submissions for Koko Eating Bananas.
 *
 *  檢討： 除法 vs 乘法＋int轉型 比較下，除法還是比較慢
 *
 *  第八次 參考了他人的寫法，比較漂亮一點的 binary search 寫法
 *
 *     Runtime: 27 ms, faster than 66.54% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 54.5 MB, less than 15.43% of Java online submissions for Koko Eating Bananas.
 *
 *  檢討： 誒？ 竟然沒有比較快
 *
 *  第九次 移除 init 的部分，看看是否會改善
 *     Runtime: 28 ms, faster than 65.07% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.9 MB, less than 47.51% of Java online submissions for Koko Eating Bananas.
 *
 *  檢討：還是有稍微變慢
 *
 */
public class KokoEatingBananas {
    public static void Main(String[] args) {
    }
}


class Solution {
    /*
    * 參考 while loop binary search 的邊界問題
    *   - https://www.geeksforgeeks.org/binary-search
    *   - https://kkc.github.io/2019/03/28/learn-loop-invariant-from-binary-search/
    * */
    public int minEatingSpeed(int[] piles, int h) {
        // Constraints: 1 <= piles.length <= 10000, 所以不擔心 empty array
        // Constraints: piles.length <= h <= 10^9, 所以不用擔心 piles.length > h 這種違反題目宗旨的情境
        // 給定條件 length <= h

        int max_banana_pile = -1;  // 最多香蕉數
        long sum_banana_piles = 0; // 香蕉總和
        for (int pile: piles) {
            sum_banana_piles += pile;
            if (max_banana_pile < pile) {
                max_banana_pile = pile;
            }
        }

        // 總時數
        long total_cost_hour = 0; // Constraints: 每個 piles 最大可能到 10^9 ，相關 hour 也可能到 10^9 ，所以要用 long 才足夠計算，否則會破表

        int speedK_upper_limit = max_banana_pile; // 速度最大值（上限)
        int speedK_lower_limit = (int)(sum_banana_piles/h); // 速度最小值（下限）
        if (speedK_lower_limit<1) {speedK_lower_limit=1;}

        // 迭代用的當下速度
        int speedK = 1;

        // 迴圈去猜總時數
        while (speedK_upper_limit > speedK_lower_limit) {

            speedK = (int)(speedK_lower_limit + (speedK_upper_limit - speedK_lower_limit) * 0.5); // 避免溢位的寫法

            // 迴圈，累計吃光每一個 pile 所需時間
            total_cost_hour = 0;
            for (int pile : piles) {
                total_cost_hour += pile / speedK; // 吃完此 pile 的 hour 數
                if (pile % speedK != 0) {
                    total_cost_hour++; // 是否需要補 1
                }
            }

            if (total_cost_hour <= h) {
                speedK_upper_limit = speedK;
            } else {
                speedK_lower_limit = speedK + 1;
            }

        }

        System.out.println(String.format("上:%s 下:%s Speed:%s", speedK_upper_limit, speedK_lower_limit, speedK));

        return speedK_lower_limit;
    }

}


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
 */
public class KokoEatingBananas {
    public static void Main(String[] args) {
    }
}


class Solution {
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

        long speedK_upper_limit = max_banana_pile; // 速度最大值（上限）（直接給題目的最上限，而不要動態決定 piles 內的最大值）
        long speedK_lower_limit = sum_banana_piles/h; // 速度最小值（下限）
        if (speedK_lower_limit<1) {speedK_lower_limit=1;}

        // 迭代用的當下速度
        long speedK;

        boolean the_same_hour_check = false; // 在相同時數下，是否要繼續 loop 找尋最小值
        long the_same_hour_previous_speedK = -1; // 在相同時數下，紀錄前一次的 speedK

        // 迴圈去猜總時數
        while (true) {

            speedK = (long)((speedK_upper_limit + speedK_lower_limit) * 0.5); // 只取整數，也就是說如果中央點是兩個，就取小的那個

            // 上下限相等 -> 收斂停止; 上限<下限 -> 超過，停止
            if (speedK_upper_limit <= speedK_lower_limit) {
                break;
            }

            total_cost_hour = get_total_cost_hour(piles, speedK);

            // 是否滿足條件?
            if (total_cost_hour > h) {
                // 所費時間 超過 預計 -> 速度太慢要提升

                // 進行增速
                if (speedK_upper_limit - speedK_lower_limit == 1) {
                    // 增速特殊案例修正
                    speedK_lower_limit++; // 如果剛好只差一，原本的加速方式會剛好無法加速，改成直接讓
                } else {
                    // 正常增速
                    speedK_lower_limit = speedK; // 把下限改成目前速度，下一個 loop 就會取 上限～目前速度 的中間值
                }
            } else if (total_cost_hour == h) {
                // 符合時數 -> 但還不知道是不是同時數下的最低速度
                if (the_same_hour_check == false) {
                    // 初次遇到 total_cost_hour == h

                    // 紀錄當下速度
                    the_same_hour_check = true;
                    the_same_hour_previous_speedK = speedK;

                    // 進行減速
                    speedK_upper_limit = speedK;

                } else {
                    // 不是初次遇到 total_cost_hour == h

                    // 跟前一次的速度比，看看誰大誰小
                    if (speedK < the_same_hour_previous_speedK) {
                        // 新的速度更小 -> 正確方向！ 可以繼續減速

                        // 把目前更小的速度存下來
                        the_same_hour_previous_speedK = speedK;

                        // 進行減速
                        if (speedK_upper_limit - speedK_lower_limit == 1) {
                            // 減速特殊案例修正
                            speedK_upper_limit --; // 如果剛好只差一，原本的加速方式會剛好無法加速，改成直接讓
                        } else {
                            // 正常減速
                            speedK_upper_limit = speedK;
                        }
                    } else if (speedK == the_same_hour_previous_speedK) {
                        // 新速度與之前速度相同
                        // 只可能是曾經跑過下方狀況一，又加回來，此時這個 speedK 是答案之一
                        break;
                    } else {
                        // 新的速度變大了！ 所以是錯誤的方向，有可能要嘗試增速

                        // 進行增速
                        if (speedK_upper_limit - speedK_lower_limit == 1) {
                            // 增速特殊案例修正
                            speedK_lower_limit ++; // 如果剛好只差一，原本的加速方式會剛好無法加速，改成直接讓
                        }  else {
                            // 正常增速
                            speedK_lower_limit = speedK;
                        }

                        // 變大的速度就不記錄到 the_same_hour_previous_speedK
                    }
                }
            } else {
                // 所費時間短於預計 -> 速度太快，不符合盡量吃慢的目標，要減速

                // 進行減速
                if (speedK_upper_limit - speedK_lower_limit == 1) {
                    // 減速特殊案例修正
                    speedK_upper_limit --; // 如果剛好只差一，原本的加速方式會剛好無法加速，改成直接讓
                } else {
                    // 正常減速
                    speedK_upper_limit = speedK; // 把上限改成目前速度，下一個 loop 就會取 目前速度～下限 的中間值
                }
            }
        }

        return (int)speedK;
    }

    /**
     * 計算花費總時數
     *
     * @param piles piles
     * @param speedK 吃香蕉速度
     * @return
     */
    public long get_total_cost_hour(int[] piles, long speedK)
    {
        long total_cost_hour = 0;

        // 迴圈，累計吃光每一個 pile 所需時間
        for (int pile : piles) {
            total_cost_hour += pile / speedK; // 吃完此 pile 的 hour 數
            if (pile % speedK != 0) {
                total_cost_hour++; // 是否需要補 1
            }
        }

        return total_cost_hour;
    }
}


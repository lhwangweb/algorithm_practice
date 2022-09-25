package No875;
import java.util.Arrays;
import java.util.Collections;

/**
 * 875. Koko Eating Bananas
 *
 * 第一版 暴力解法
 *  -> Leetcode 不過 Time Limit Exceeded  (https://leetcode.com/submissions/detail/805311556/)
 *
 *
 */
public class KokoEatingBananas {
    public static void Main(String[] args) {
    }
}


class Solution {
    public int minEatingSpeed(int[] piles, int h) {

        int length = piles.length;

        // Constraints: 1 <= piles.length <= 10000, 所以不擔心 empty array
        // Constraints: piles.length <= h <= 10^9, 所以不用擔心 piles.length > h 這種違反題目宗旨的情境

        Arrays.sort(piles);
        int speed = 1; // 吃香蕉速度初始值，設定為 1

        // 給定條件 length <= h

        if (length == h) {
            // piles length == h -> speed 就是最大 pile
            return piles[piles.length-1];
        }

        // 到這裡一定 length < h

        long total_hour = 0; // Constraints: 每個 piles 最大可能到 10^9 ，相關 hour 也可能到 10^9 ，所以要用 long 才足夠計算，否則會破表

        while (true) {
            total_hour = 0;

            // 迴圈，累計吃光每一個 pile 所需時間
            for (int pile : piles) {
                total_hour += pile / speed; // 吃完此 pile 的 hour 數
                if (pile % speed != 0) {
                    total_hour++; // 是否需要補 1
                }
            }

            // System.out.println(String.format("total_hour: %s", total_hour));
            // System.out.println(String.format("speed: %s", speed));

            // 是否滿足條件?
            if (total_hour > h) {
                speed++;
            } else {
                // total_hour <= h
                // 因為 speed 是從小往上猜，所以 total_hour 會從大慢慢減小，當首次 total_hour <= h 時就是答案
                break;
            }
        }

        return speed;
    }
}


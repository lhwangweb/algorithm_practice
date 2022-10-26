package No875;
import java.util.Arrays;
import java.util.Collections;

/**
 * 875. Koko Eating Bananas
 *
 * 第一版 暴力解法 從最小速度每次猜 +=1
 *  -> Leetcode 不過 Time Limit Exceeded  (https://leetcode.com/submissions/detail/805311556/)
 *
 *  - 檢討： 每次 +=1 太慢，改 binary search
 *
 * 第二版 binary search 猜
 *    Runtime: 108 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.7 MB, less than 67.00% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： binary search 還是太慢？ 有更好的演算法？ 還是 code 太冗？
 *
 * 第三版 減少除法版
 *     Runtime: 83 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 54.3 MB, less than 20.78% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 更快(可惜進步的百分比沒多少)，但減少迴圈中的除法還是非常必要的優化 但可能是因為需要不斷的轉型別，記憶體耗用更大了！
 *
 *  第四版 簡化初始的速度上限值猜法，改成固定值，不去找 piles 內的最大值
 *     Runtime: 84 ms, faster than 5.14% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.6 MB, less than 71.80% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 節省不少記憶體！
 *
 * 第五版 嘗試一開始 init 就把上下限都猜
 *     Runtime: 99 ms, faster than 5.06% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.8 MB, less than 54.13% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 看起來沒有幫助，反而平均起來還比較慢
 *
 *  第六版 把一些不必要的 long 去掉
 *    Runtime: 20 ms, faster than 83.20% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.2 MB, less than 79.83% of Java online submissions for Koko Eating Bananas.
 *
 * -  檢討： 原本只是想看能否改善記憶體耗用，卻意外增進了速度
 *
 *  第七次 試試看如果還是用除法
 *
 *    Runtime: 24 ms, faster than 72.64% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.3 MB, less than 79.83% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 除法 vs 乘法＋int轉型 比較下，除法還是比較慢
 *
 *  第八版 參考了他人的寫法，比較漂亮一點的 binary search 寫法
 *
 *     Runtime: 27 ms, faster than 66.54% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 54.5 MB, less than 15.43% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 誒？ 竟然沒有比較快
 *
 *  第九版 移除 init 的部分，看看是否會改善
 *     Runtime: 28 ms, faster than 65.07% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 53.9 MB, less than 47.51% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討：還是有稍微變慢
 *
 *  第十版 把 total hour 的 long 也移除 (也把猜 init 加回來)
 *     Runtime: 18 ms, faster than 88.85% of Java online submissions for Koko Eating Bananas.
 *     Memory Usage: 43.3 MB, less than 86.09% of Java online submissions for Koko Eating
 *  - 檢討： 顯示出 while loop 裡面，能不要用 long 就不要用，妨礙速度會很明顯 (但！！ 這件事得小心是 JAVA 特性，還是所有語言都這樣)
 *
 *  第十一版 思考 speed 為 int，每次複寫時可能因為 immutable 而慢，把它變成 Integer 看看
 *    Runtime: 30 ms, faster than 61.89% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 54.1 MB, less than 32.43% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 哈哈哈哈～ 顯然不行，操作 mutable 的 Integer 可能還比較耗費資源
 *
 *  第十二版 維持 Integer 但不要型態轉換，為此需改用除法才能維持 int。
 *    Runtime: 18 ms, faster than 88.85% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 43.2 MB, less than 91.89% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 沒想到這樣竟然變快，也許 Integer mutable 雖然有省時間，但 speed 的計算結果 double，如果轉型態再丟到 Integer，反而多耗時 (純瞎猜，畢竟 Integer 的東西應該被丟在 Heap 的地方，這個瞎猜必須驗證才能確認是否為真)
 *
 *  第十三版 按照前面測試，疑似之前寫的不漂亮版本更快，因此找回來 並修改： (1) 不要 long (2) 改不會溢位寫法
 *
 *  推論理由：
 *     -> 根據『第六版』，原本方法最快可以達到20ms (此時舊方法的 total hour 變數仍為 long)
 *     -> 根據『 第十版』，total hour 變數不要 long，似乎速度可以大進步
 *
 *     -> 所以想以第六版為基礎，完全移除 long 看看
 *
 *    Runtime: 21 ms, faster than 80.28% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 53.9 MB, less than 54.34% of Java online submissions for Koko Eating Bananas.
 *
 *  - 檢討： 好像... 差不多差不多...
 *
 *  第十四版 最後一次，減少 function call ，把計算吃香蕉時數的 for 直接寫入 while loop 內
 *    Runtime: 19 ms, faster than 85.98% of Java online submissions for Koko Eating Bananas.
 *    Memory Usage: 43.4 MB, less than 86.11% of Java online submissions for Koko Eating Bananas.
 *
 *  附註： 這是隔天重新測試的，在上面測到第十三次之後， LeetCode 的相關速度、記憶體偵測好像有點不對勁ＸＤＤ 不知道是不是用太多次，相同的Code平均時間多了十幾ms
 *
 *  - 檢討： 減少 function call，速度有一點進步，記憶體耗用也大進步！
 *
 */
public class KokoEatingBananas {
    public static void Main(String[] args) {
    }
}


class Solution {
    /**
     *  參考 while loop binary search 的邊界問題
     *   - https://www.geeksforgeeks.org/binary-search
     *   - https://kkc.github.io/2019/03/28/learn-loop-invariant-from-binary-search/
     *
     * @param piles
     * @param h
     * @return
     */
    public int minEatingSpeed_pretty_binary_search(int[] piles, int h) {
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
        int total_cost_hour = 0; // Constraints: 每個 piles 最大可能到 10^9 ，相關 hour 也可能到 10^9 ，所以要用 long 才足夠計算，否則會破表

        int speedK_upper_limit = max_banana_pile; // 速度最大值（上限)
        int speedK_lower_limit = (int)(sum_banana_piles/h); // 速度最小值（下限）
        if (speedK_lower_limit<1) {speedK_lower_limit=1;}

        // 迭代用的當下速度
        Integer speedK = 1;

        // 迴圈去猜總時數 (此 binary search 寫法參考： (1) LeetCode 比較快的人的寫法 (2) 用 Loop 寫 Binary Search 的相關討論)
        while (speedK_upper_limit > speedK_lower_limit) {

            speedK = (speedK_lower_limit + (speedK_upper_limit - speedK_lower_limit) / 2 ); // 避免溢位的寫法

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


    /**
     * 找回先前版本，雖然不漂亮，但疑似可以比較快，所以找回來試試看，但經過以下調整：
     *   (1) 不要 long
     *   (2) 改不會溢位寫法
     *   (3) 計算 total hour 不使用 function call
     *
     *   最快大約做到 19ms ，還是比漂亮的 binary search 略慢一點，結果參考上面的『第十四次』
     *
     * @param piles
     * @param h
     * @return
     */
    public int minEatingSpeed(int[] piles, int h) {

        // 計算初始值
        int max_banana_pile = -1; // 最多香蕉數目
        long sum_banana_piles = 0; // 所有香蕉總和
        for (int pile: piles) {
            sum_banana_piles += pile;
            if (max_banana_pile < pile) {
                max_banana_pile = pile;
            }
        }

        int total_cost_hour = 0;
        int speedK_upper_limit = max_banana_pile; // 初始速度上限 最多香蕉數目
        int speedK_lower_limit = (int)(sum_banana_piles/h); // 初始速度下限  總香蕉數/時數 (這是理論上的最慢的速度，當有了 pile 的限制之後，Koko 只能吃得比這個速度快才行)
        if (speedK_lower_limit<1) {speedK_lower_limit=1;} // 萬一算出來是 0 ，要改 1
        int speedK;

        boolean the_same_hour_check = false;
        int the_same_hour_previous_speedK = -1;
        while (true) {
            // 速度
            speedK = (int)(speedK_lower_limit + (speedK_upper_limit - speedK_lower_limit) * 0.5);

            // 結束條件
            if (speedK_upper_limit <= speedK_lower_limit) {
                break;
            }

            // 計算此速度下，吃香蕉耗費時數
            total_cost_hour = 0;

            for (int pile : piles) {
                total_cost_hour += pile / speedK;
                if (pile % speedK != 0) {
                    total_cost_hour++;
                }
            }

            if (total_cost_hour > h) {
                // 吃香蕉時間超過可用時間 -> 太慢 -> 增速
                if (speedK_upper_limit - speedK_lower_limit == 1) {
                    // 如果上下界剛好相鄰，就只加一，把下界頂上去 (之後上下界就會重合，然後下個 loop 會結束)
                    speedK_lower_limit++;
                } else {
                    // 上下界不相鄰，此時把下界設定為目前速度
                    speedK_lower_limit = speedK;
                }
            } else if (total_cost_hour == h) {
                // 耗費時數符合預期，但還不知道是不是相同時數下，最慢的速度 (題目要求Koko 想盡可能吃慢)

                if (the_same_hour_check == false) {
                    // 第一次觸碰到符合時數的速度

                    the_same_hour_check = true; // 標記遇到符合時數的速度
                    the_same_hour_previous_speedK = speedK; // 記下目前速度，之後持續比較是否有比這個速度更慢

                    speedK_upper_limit = speedK; // 希望找到比這個速度更慢的，所以嘗試減速 (上界設定為目前速度)

                } else {
                    // 不是第一次遇到 符合時數的速度，這時候把這次的速度跟之前的比較，看誰小，如果發現目前速度更小，就採用
                    if (speedK < the_same_hour_previous_speedK) {
                        // 新的速度 比 上次速度小，是我們想找的！

                        // 記下新速度！
                        the_same_hour_previous_speedK = speedK;

                        // 繼續嘗試減速
                        if (speedK_upper_limit - speedK_lower_limit == 1) {
                            // 如果上下界已經相鄰，就只減一
                            speedK_upper_limit --;
                        } else {
                            // 上下界不相鄰，此時把上界設定為目前速度
                            speedK_upper_limit = speedK;
                        }
                    } else if (speedK == the_same_hour_previous_speedK) {
                        // 新速度 跟 上次速度一樣，表示已經收斂 (走到這裡，代表 上次 跟 這次 是『以相同速度算出相同耗費時數』，那就是已經在重工了，表示搜尋應該已經收斂在此數值)
                        break;
                    } else {
                        /* 不必做事，當 total_cost_hour == h 時，速度一直都在嘗試減速，沒有會加速的情境 */
                    }
                }
            } else {
                // 吃香蕉時間低於可用時間 -> 太快 -> 減速
                if (speedK_upper_limit - speedK_lower_limit == 1) {
                    // 上下界相臨 只減一
                    speedK_upper_limit --;
                } else {
                    // 上下界不相鄰，把上界設定為目前速度
                    speedK_upper_limit = speedK;
                }
            }
        }

        return speedK;
    }


}


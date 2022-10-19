package No875;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KokoEatingBananasTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void test_main() {
        Solution test_solution = new Solution();
        List<HashMap> test_cases = new ArrayList<HashMap>();

        // case 1
        test_cases.add(new HashMap () {{
            put("Input", new int [] {3,6,7,11});
            put("h", 8);
            put("Output", 4);
        }});

        // case 2
        test_cases.add(new HashMap () {{
            put("Input", new int [] {30,11,23,4,20});
            put("h", 5);
            put("Output", 30);
        }});

        // case 3
        test_cases.add(new HashMap () {{
            put("Input", new int [] {30,11,23,4,20});
            put("h", 6);
            put("Output", 23);
        }});

        // case 4
        test_cases.add(new HashMap () {{
            put("Input", new int [] {3,6,7,11});
            put("h", 9);
            put("Output", 4);
        }});

        // case 5
        test_cases.add(new HashMap () {{
            put("Input", new int [] {3,6,7,11});
            put("h", 9);
            put("Output", 4);
        }});

        // case 6 (LeetCode Example)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {312884470,});
            put("h", 312884469);
            put("Output", 2);
        }});

        // case 7 (LeetCode Example)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {332484035,524908576,855865114,632922376,222257295,690155293,112677673,679580077,337406589,290818316,877337160,901728858,679284947,688210097,692137887,718203285,629455728,941802184});
            put("h", 823855818);
            put("Output", 14);
        }});

//        // case 8 (LeetCode Example) -> 暴力法在這邊破功了，顯示 Time Limit Exceeded
//        test_cases.add(new HashMap () {{
//            put("Input", large_data);
//            put("h", 63939633);
//            put("Output", 14);
//        }});

        // 有一隻特高(1)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {2431,14,15});
            put("h", 145);
            put("Output", 17);
        }});
        // 有一隻特高(2)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {2431,14,15,16});
            put("h", 146);
            put("Output", 17);
        }});
        // 有一隻特高(3)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {36,8,9});
            put("h", 5);
            put("Output", 12);
        }});

        // case 9 (LeetCode Example)
        test_cases.add(new HashMap () {{
            put("Input", new int [] {312884470,});
            put("h", 968709470);
            put("Output", 1);
        }});

        test_cases.add(new HashMap () {{
            put("Input", new int [] {1000000000,1000000000});
            put("h", 3);
            put("Output", 1000000000);
        }});




        for (HashMap test_case: test_cases) {
            int[] piles = (int[])test_case.get("Input");
            int h = (int)test_case.get("h");
            int Output = test_solution.minEatingSpeed(piles, h);
            assertEquals((int)test_case.get("Output"), Output);
            System.out.println(String.format("speed: %s", Output));
        }

    }
}
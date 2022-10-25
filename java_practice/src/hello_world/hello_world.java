package hello_world; /**
 * Hello World
 */

import java.util.Arrays;
import java.awt.Point;

public class hello_world {
    public static void main(String[] args) {
        // 主題: hello world
        System.out.println("Hello world!");

        // 主題: 指派運算不會複製陣列資料
        int[] a = new int[]{1, 2, 3, 4, 5};
        int[] b;
        b = a;
        b[2] = 10;

        System.out.println(a);
        System.out.println(Arrays.toString(a));
        System.out.println(b);
        System.out.println(Arrays.toString(b));

        int[] c = new int[]{1, 2, 7, 92, 16, 53, 31, 24, 78, 17, 82, 5, 16};
        int[] d;
        d = c;
        Arrays.sort(d);
        System.out.println(c);
        System.out.println(Arrays.toString(c));
        System.out.println(d);
        System.out.println(Arrays.toString(d));

        // 主題: primitive arrays 想要 DESC
        // Arrays.sort() cannot be used directly to sort primitive arrays in descending order
        // That will work fine with 'Array of Objects' such as Integer array but will not work with a primitive array such as int array.
        // https://stackoverflow.com/questions/1694751/java-array-sort-descending

        System.out.println("");

        // 主題: 賦值的底層觀念
        //    ref:
        //      https://openhome.cc/Gossip/JavaEssence/EqualOperator.html
        //      https://yubin551.gitbook.io/java-note/basic_java_programming/datatype/primitivedatatypes
        //      https://yubin551.gitbook.io/java-note/basic_java_programming/datatype/primitive_reference_difference

        // 1. 基本資料型態 primitive: char, byte, short, int, long, float, double, boolean
        int x = 10; // Stack 建立一個 x，裡面 value 是 10
        int y = x;  // Stack 建立一個 y，裡面 value 從 x Copy 過來，所以是 10
        System.out.println(x);
        System.out.println(y);
        // y 改變 與 x 無關

        // 2. new object (參考資料型態 Reference Types)
        Object o = new Object(); // 這時候， Stack 區建立一個 o (Object o)，然後在 heap 區建立 Object (new Object())，相關的記憶體位址存到 Stack 內的 o 裡面 (=)
        System.out.println(o);

        // 3.  object assign
        Object o1 = new Object(); // Stack 建立 o1， Heap 建立 Object，記憶體位址存在 o1
        Object o2 = o1; // Stack 建立一個 o2，裡面 value 從 o1 Copy 過來，所以是相同的記憶體位址
        System.out.println(o1);
        System.out.println(o2);

        // 4. == 運算子 - 比較 value 是否相同，所以
        // o1 == o2 這是 true
        System.out.println(o1 == o2);
        // x == y 這是 true
        System.out.println(x == y);

        // 5. String, Integer 是 primitive wrapper classes

        // 主題: mutable 與 immutable
        // 1. mutable 與 immutable
        //      https://books.trinket.io/thinkjava2/chapter9.html
        //      https://books.trinket.io/thinkjava2/chapter10.html
        // 2. Are Java primitives immutable?
        // https://stackoverflow.com/questions/18037082/are-java-primitives-immutable
        // 3. Are primitive data types mutable or immutable in java? [duplicate]
        // https://stackoverflow.com/questions/37990836/are-primitive-data-types-mutable-or-immutable-in-java
        // 重要摘錄: you only set the value (20) on the primitive variable st.id. There is no connection between st.id and id.

        Integer aaa = 20;
        System.out.println(aaa); // aaa 內存有一個位址，指向 heap 內 的某個位址的 Integer(20) 物件
        Integer bbb = aaa; // bbb 存了一個相同的位址，指向同一個 Integer(20) 物件
        System.out.println(aaa);
        System.out.println(bbb);
        aaa = 21; // aaa 位址換了，指向 heap 內某個 Integer(20) 物件
        System.out.println(aaa); // 指向 heap 內 的某個位址的 Integer(21) 物件
        System.out.println(bbb); // 指向 heap 內 的某個位址的 Integer(20) 物件

        // 4. 測試 mutable 物件作為 argument 傳入 function 內，並在 function 內被修改
        Point ccc = new Point(3, 4);
        System.out.println("before");
        System.out.println(ccc);
        System.out.println(ccc.x);
        System.out.println(ccc.y);
        test_ccc_change(ccc);
        System.out.println("after");
        System.out.println(ccc);
        System.out.println(ccc.x); /* 因為 mutable，所以修改被保留了 */
        System.out.println(ccc.y);

        // 5. immutable 物件作為 argument 傳入
        Integer ddd = 1;
        System.out.println("before");
        System.out.println(ddd);
        test_ddd_change(ddd);
        System.out.println("after");
        System.out.println(ddd);

        // 6. two strings that contain the same characters can be stored in memory only once
        // https://books.trinket.io/thinkjava2/chapter10.html#sec130
        String test_s1 = "Hi, Mom!";
        String test_s2 = "Hi, " + "Mom!";
        System.out.println(test_s1 == test_s2); // 可以發現， test_s1 與 test_s2 內的參考是相同的值 (指向 heap 內相同位址)


        // 測試溢位
        int test_overflow_result_1 = (int) (2137483647 + ( 2137483647 - 7) / 2);
        System.out.println(String.format("result 1: %s", test_overflow_result_1));

        int test_overflow_result_2 = (2137483647 + 2137483647) / 2;
        System.out.println(String.format("result 2: %s", test_overflow_result_2));

    }

    /**
     *  測試引數修改
     * @param abc mutable 物件
     */
    public static void test_ccc_change(Point abc) {
        abc.x = 10;
    }

    /**
     *  測試引數修改
     * @param abc immutable 物件
     */
    public static void test_ddd_change(Integer abc) {
        /* abc 在這一行時，接收 argument 傳入，其參考指向 heap 裡面一個 Integer(1) 的物件*/
        abc = 10; /* abc 在這一行時，因 immutable，所以參考改成指向 heap 內另一個  Integer(10) 的物件，與此同時， function 外部的變數 ddd 仍維持著指向 Integer(1) */
    }
}
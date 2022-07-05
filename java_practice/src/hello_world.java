/**
 * Hello World
 */

import java.util.Arrays;

public class hello_world {
    public static void main(String[] args) {
        // hello world
        System.out.println("Hello world!");

        // 指派運算不會複製陣列資料
        int[] a = new int [] {1,2,3,4,5};
        int[] b;
        b = a;
        b[2] = 10;

        System.out.println(a);
        System.out.println(Arrays.toString(a));
        System.out.println(b);
        System.out.println(Arrays.toString(b));

        int[] c = new int [] {1,2,7,92,16,53,31,24,78,17,82,5,16};
        int[] d;
        d = c;
        Arrays.sort(d);
        System.out.println(c);
        System.out.println(Arrays.toString(c));
        System.out.println(d);
        System.out.println(Arrays.toString(d));

        // primitive arrays 想要 DESC
        // Arrays.sort() cannot be used directly to sort primitive arrays in descending order
        // That will work fine with 'Array of Objects' such as Integer array but will not work with a primitive array such as int array.
        // https://stackoverflow.com/questions/1694751/java-array-sort-descending


    }
}
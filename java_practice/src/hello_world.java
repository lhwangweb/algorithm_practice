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



    }
}
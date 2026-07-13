import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int ans = x / y;
            System.out.println(ans);
        } catch (ArithmeticException e) {
            System.out.println("java.lang.ArithmeticException: / by zero");
        } catch (InputMismatchException e) {
            System.out.println("java.util.InputMismatchException");
        } finally {
            sc.close();
        }
    }
}

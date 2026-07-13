import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String pattern = "^[a-zA-Z][a-zA-Z0-9_]{7,29}$";
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            if (Pattern.matches(pattern, s)) {
                System.out.println("Valid");
            } else {
                System.out.println("Invalid");
            }
        }
        sc.close();
    }
}

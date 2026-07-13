import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        String B=sc.next();
        int len_A=A.length();
        int len_B=B.length(); 
        System.out.println(len_A+len_B);
        if(A.compareTo(B)>0){
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }
        A = Character.toUpperCase(A.charAt(0)) + A.substring(1);
        B = Character.toUpperCase(B.charAt(0)) + B.substring(1);
        System.out.printf("%s %s",A,B);
        sc.close();
        
    }
}




import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        boolean is_palindrome=true;
        for(int i=0;i<A.length()/2;i++){
            if(A.charAt(i)!=A.charAt(A.length()-1-i)){
                is_palindrome=false;
                break;
            }
        }
        if(is_palindrome){
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }
    }
}




import java.util.*;
import java.text.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double payment = scanner.nextDouble();
        scanner.close();
        
        NumberFormat us=NumberFormat.getCurrencyInstance(Locale.US);
        String US= us.format(payment);
        NumberFormat india=NumberFormat.getCurrencyInstance(new Locale("en","in"));
        String IND=india.format(payment);
        NumberFormat china=NumberFormat.getCurrencyInstance(Locale.CHINA);
        String CHINA=china.format(payment);
        NumberFormat france=NumberFormat.getCurrencyInstance(Locale.FRANCE);
        String FRA=france.format(payment);
        
  
        System.out.println("US: " + US);
        System.out.println("India: " + IND);
        System.out.println("China: " + CHINA);
        System.out.println("France: " + FRA);
    }
}
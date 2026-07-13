import java.util.*;

class Solution{
    public static void main(String []argh)
    {
        Scanner sc = new Scanner(System.in);
        int t=sc.nextInt();

        for(int i=0;i<t;i++)
        {
            try
            {
                long x=sc.nextLong();
                System.out.println(x+" can be fitted in:");
                if(x>=-128 && x<=127) System.out.println("* byte");
                if(x>=-32768 && 32767>=x) System.out.println("* short");
                if (x>= Integer.MIN_VALUE && Integer.MAX_VALUE>=x) System.out.println("* int");
                if (x >= Long.MIN_VALUE && Long.MAX_VALUE>=x) System.out.println("* long");            
            }
            catch(Exception e)
            {
                System.out.println(sc.next()+" can't be fitted anywhere.");
            }

        }
        sc.close();
    }
}




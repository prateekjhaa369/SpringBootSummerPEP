class PatternPrinting {

    static void pattern(int n) {
        for (int i = 0; i < n; i++) {

            // Print leading spaces
            for (int j = n; j > i; j--) {
                System.out.print(" ");
            }

            // Print stars
            for (int k = 0; k <= i; k++) {
                System.out.print("* ");
            }

            // Move to the next line
            System.out.println();
        }
    }

    static void reverse_pattern(int n) {
        for (int i = n - 1; i >= 0; i--) {

            // Print leading spaces
            for (int j = n; j > i; j--) {
                System.out.print(" ");
            }

            // Print stars
            for (int k = 0; k <= i; k++) {
                System.out.print("* ");
            }

            // Move to the next line
            System.out.println();
        }
    }

    static void number_pattern(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
        }
    }

    static void reversed_number_pattern(int n) {
        for (int i = n; i >= 1; i--) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        reversed_number_pattern(10);
    }
}
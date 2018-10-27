
public class RangeChecker {
    // Helper function to check if x is in range (a,b] --> // It is a half closed
    // interval.
    public static boolean checkOpenCloseRange(int x, int a, int b) {
        if (a < b) {
            return x > a && x <= b;
        } else {
            return !(x <= a && x > b);
        }
    }

    // Helper function to check if x is in range [a,b) --> // It is a half closed
    // interval.
    public static boolean checkCloseOpenRange(int x, int a, int b) {
        if (a < b) {
            return x >= a && x < b;
        } else {
            return !(x < a && x >= b);
        }
    }

    // Helper function to check if x is in range (a,b) --> // It is open interval
    // interval.
    public static boolean checkOpenRange(int x, int a, int b) {
        if (a < b) {
            return x > a && x < b;
        } else {
            return !(x <= a && x >= b);
        }
    }

}
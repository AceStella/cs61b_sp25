/**
 * Skeleton file for HW0A.
 * Exercises sourced from Practice-It by the University of Washington.
 * Original problems available at: https://practiceit.cs.washington.edu/
 *
 * @author Erik Kizior
 */
public class JavaExercises {

    /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on.
     */
    public static void starTriangle() {
        String stars = "";
        for (int i = 1; i <= 5; i++) {
            stars += "*";
            System.out.println(stars);
        }
    }

    /**
     * Prints each character of a given string followed by its reverse index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
    public static void printIndexed(String s) {
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i));
            System.out.print(s.length() - 1 - i);
        }
        System.out.println();
    }

    /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: stutter("hello") -> "hheelllloo"
     */
    public static String stutter(String s) {
        String output = "";
        for (int i = 0; i < s.length(); i++) {
            output += s.charAt(i);
            output += s.charAt(i);
        }
        return output;
    }

    /**
     * Determines the quadrant of a Cartesian coordinate (x, y).
     * Returns:
     *   1 for the first quadrant (x > 0, y > 0),
     *   2 for the second quadrant (x < 0, y > 0),
     *   3 for the third quadrant (x < 0, y < 0),
     *   4 for the fourth quadrant (x > 0, y < 0),
     *   0 if the point lies on an axis.
     */
    public static int quadrant(int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        } else if (x > 0) {
            return y > 0 ? 1 : 4;
        } else {
            return y > 0 ? 2 : 3;
        }
    }

    public static void main(String[] args) {
        starTriangle();
        printIndexed("hello");
        System.out.println(stutter("hello"));
        System.out.println(quadrant(3, 4));   // Output: 1
        System.out.println(quadrant(-3, 4));  // Output: 2
        System.out.println(quadrant(-3, -4)); // Output: 3
        System.out.println(quadrant(3, -4));  // Output: 4
        System.out.println(quadrant(0, 5));   // Output: 0
    }
}

// you can import things up here if you want

public class UserSolution {
    public static void main(String[] args) {
      System.out.println(ascii_deletion_distance("String1", "String2"));
    }
    public static int ascii_deletion_distance(String str1, String str2) {




        for (int i = 0; i < )

        return getDiff("a", "b");
    }

    // Get the ascii diff of 2 string of the same size
    public static int getDiff(String str1, String str2) {
        int diff = 0;

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                diff += (int)str1.charAt(i) + (int)str2.charAt(i);
            }
        }

        return diff;
    }
}

import java.util.*;

public class CoinDP {

    // run tests
    static int[] denom = {1, 3, 7, 27};

    public static void main(String args[]) {
      List<int[]> tests = new ArrayList<int[]>();

      tests.add(new int[]{1, 1});
      tests.add(new int[]{10, 1});
      tests.add(new int[]{25, 1});
      tests.add(new int[]{50, 2});
      tests.add(new int[]{73, 7});
      tests.add(new int[]{125, 5});
       tests.add(new int[]{100, 4});

      for (int[] test : tests) {
        int solution = solve(test[0], denom);

        if (solution != test[1]) {
          System.out.println("Failed:" + test[0]);
        }
        System.out.println(test[0] + " : " + solution);
      }

      int num = 100;
      int[] ns = {1232, 346, 1291, 1232, 1201};
      for (int i : ns) {
        System.out.println("n is: " + i + "SOlution is: " + solve(i, denom));
      }
      for (int i = 0; i < 5; i++) {
        int n = num + 3 * num++;
        int solution = solve(n, denom);
        System.out.println("N is: " + n);
        System.out.println("solution is: " + solution);
      }
      // System.out.println("min way to make" + num + "is" +  + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");
      // System.out.println(num);
      // System.out.println("min way to make" + num + "is" + solve(num + 3 * num++, denom) + "coins");


    }

    public static int solve(int amount, int[] denom) {
        Map<Integer, Integer> memo = new HashMap<>();
        return solve(amount, denom, memo);
    }

    public static int solve(int amount, int[] denom, Map<Integer, Integer> memo) {
      int minCoins = Integer.MAX_VALUE;
      if (memo.containsKey(amount)) {
        return memo.get(amount);
      }
      // System.out.println("solve");
      for (int coin : denom) {
        int min = minCoins;

        if (amount == coin) {
          return 1;
        } else if (amount > coin) {
          min = solve(amount - coin, denom, memo) + 1;
        } else {
          min = Integer.MAX_VALUE;
        }

        if (min < minCoins) {
          minCoins = min;
        }
      }

      memo.put(amount, minCoins);
      return minCoins;
    }

}
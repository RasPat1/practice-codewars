import java.util.*;

public class CoinFree {

    static Map<Integer, Integer> memo = new HashMap<>();

    public static int solve(int amount, int[] denom) {
        int[] denomSorted = new int[denom.length];

        Arrays.sort(denom);
        for (int i = 0; i < denom.length; i++) {
          denomSorted[i] = denom[denom.length - 1 - i];
        }

        return solve(amount, denomSorted, 1);
    }

    public static int solve(int amount, int[] denom, int count) {
      int minCoins = Integer.MAX_VALUE;

      if (memo.containsKey(amount)) {
        return memo.get(amount);
      }
      if (amount <= 0) {
        return 0;
      }

      for (int coin: denom) {
        if (amount == 12010000) {
          System.out.println(coin);
        }
        int min = minCoins;

        if (amount == coin) {
          return count;
        } else if (amount > coin) {
          return solve(amount - coin, denom, count + 1);
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
import java.util.*;

public class CoinFree {

    public static int solve(int amount, int[] denom) {
        Map<Integer, Integer> memo = new HashMap<>();
        int[] denomSorted = new int[denom.length];

        Arrays.sort(denom);
        for (int i = 0; i < denom.length; i++) {
          denomSorted[i] = denom[denom.length - 1 - i];
        }

        return solve(amount, denomSorted, memo);
    }

    public static int solve(int amount, int[] denom, Map<Integer, Integer> memo) {
      int minCoins = Integer.MAX_VALUE;

      if (memo.containsKey(amount)) {
        return memo.get(amount);
      }
      if (amount <= 0) {
        return 0;
      }

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
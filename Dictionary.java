import java.util.*;

public class Dictionary {
  String[] words;

  public Dictionary(String[] words) {
    this.words = words;
  }

  /**
   * Finds the word in the dictionary most Similar
   * to the one provided
  */
  public String findMostSimilar(String from) {
    int minDistance = Integer.MAX_VALUE;
    String mostSimilarWord = "";

    for (String to : words) {
      int distance = editDistanceBU(from, to);
      if (distance < minDistance) {
        mostSimilarWord = to;
        minDistance = distance;
      }
    }

    return mostSimilarWord;
  }

  public static int editDistance(String from, String to) {
    HashMap<String, Integer> memo = new HashMap<String, Integer>();
    return editDistance(from, to, memo);
  }

  /**
   * Finds the minimum edit Distance between
   * 2 strings
   * Operations allowed are add, delete and replace
   */
  public static int editDistance(String from, String to, HashMap<String, Integer> memo) {
    int minCost = Integer.MAX_VALUE;
    String key = from.compareTo(to) > 0 ? from + "," + to : to + "," + from;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }

    int i = from.length();
    int j = to.length();

    if (i == 0) {
      minCost = j;
    } else if (j == 0) {
      minCost = i;
    } else {
      int matchCost = editDistance(from.substring(0, i - 1), to.substring(0, j - 1), memo) +
                       (from.charAt(i - 1) == to.charAt(j - 1) ? 0 : 1);
      int deleteCost = editDistance(from.substring(0, i), to.substring(0, j - 1), memo) + 1;
      int addCost = editDistance(from.substring(0, i-1), to.substring(0, j), memo) + 1;

      // get min of all 3 costs;
      minCost = Math.min(matchCost, Math.min(deleteCost, addCost));
    }

    memo.put(key, minCost);
    return minCost;
  }

  /*
   * Bottom up edit distance
   */
  public static int editDistanceBU(String from, String to) {
    // make an array to hold all the distances
    int m = from.length();
    int n = to.length();
    int[][] d = new int[m + 1][n + 1];

    // intialize array 0 values
    for (int i = 0; i <= m; i++) {
      d[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
      d[0][j] = j;
    }

    // the distance at any square is the min of the three possibilities before it?
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        int matchCost = d[i-1][j-1] + (from.charAt(i-1) == to.charAt(j-1) ? 0 : 1);
        int deleteCost = d[i-1][j] + 1;
        int addCost = d[i][j-1] + 1;
        d[i][j] = Math.min(matchCost, Math.min(deleteCost, addCost));
      }
    }

    return d[m][n];
  }

}
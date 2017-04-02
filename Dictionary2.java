import java.util.*;
/*
* https://www.codewars.com/kata/5259510fc76e59579e0009d4/train/java
*/
public class Dictionary2 {

  private final String[] words;

  public Dictionary(String[] words) {
    this.words = words;
  }

  public String findMostSimilar(String to) {
    // TODO: this is your task ;)
    String closestWord = "";
    int minDistance = Integer.MAX_VALUE;
    for (int i = 0; i < words.length; i++) {
      String from = words[i];
      // int distance = naiveDistance(from, to);
      int distance = deletionDistance(from, to);
      // System.out.println("Distance from " + from + " to " + to + " is: " + distance);
      // System.out.println("minDistance: " + minDistance);
      if (distance < minDistance) {
        closestWord = from;
        minDistance = distance;
        // System.out.println("updated");
      }
    }
    return closestWord;
  }

  public static int deletionDistance(String s1, String s2) {
    HashMap<String, Integer> memo = new HashMap<String, Integer>();
    int bestKnownSolution = s1.length() + s2.length();
    return deletionDistance(s1, s2, memo, 0, bestKnownSolution);
  }

  public static int deletionDistance(String s1, String s2, HashMap<String, Integer> memo, int count, int bestKnownSolution) {
    if (count > bestKnownSolution) {
      return count;
    }
    if (s1.equals(s2)) {
      return count;
    }
    if (s2.length() > s1.length() || (s1.length() == s2.length() && s1.charAt(0) < s2.charAt(0))) {
      String temp = s1;
      s1 = s2;
      s2 = temp;
    }

    String key = s1 + "," + s2;
    if (memo.containsKey(key)) {
      // System.out.println("used memo for: " + key);
      return memo.get(key);
    }

    int minDistance = s1.length() + s2.length() + count;
    for (int i = 0; i < s1.length(); i++) { // O(m)
      String afterDeletion = removeChar(s1, i); // O(1)
      int d = deletionDistance(afterDeletion, s2, memo, count + 1, bestKnownSolution);
      // memo.put(afterDeletion + "," + s2, d - 1);
      minDistance = d < minDistance ? d : minDistance; // O(1)
      if (d < bestKnownSolution) {
        bestKnownSolution = d;
      }
    }

    memo.put(key, minDistance); // O(1)
    return minDistance;
  }


  // Get LCS (multiple LCs of same length?)
  // try to extend LCS
    // add char to line up
    // replace char to line up
    // remove char to line up
// want LCS to be size of string


// Use DP!



// take naive version
  // instead of trying







// XXXCCCCXCCC
//    CCCCXCCCXXX


// 12345667890
// 61234567890
// 6

//  12345667890
// 61234567890
// 6



// abcd
//  bczz

  // Edit distance
  public int editDistance(String s1, String s2) {
		// scoring -- 1 point for add, remove, or replace
    int score = 0;
		// I dont konw this alg so let's start form scratch
		// Do we ahve to use longest common subsequence?
    // s1 does not change, try to match s2 to s1
    // alg
    // 1) Find LCS
    // 2) ?Line them up?
    // 3) Make each possible change
    // 4) Repeat with each changed word

    // goign to be recursive
    // BASECASE
    // if they are the same return 0
    // SUBPROBLEM
    // LCS?
    return 0;
  }


  /**
   * Return longest common subsequence
   * return index on s1 where s2 starts
   * Should this return the actual LCS, the index on s1 or s2 where it starts?
   * Let's return teh actual LCS
   * We can get the index on each later by doing a find on the string
   */
  public int LCS(String s1, String s2) {

    return 0;
  }

  public static int naiveDistance(String s1, String s2) {
    HashMap<String, Integer> memo = new HashMap<String, Integer>();
    return naiveDistance(s1, s2, memo);
  }

  /**
   * Naive brute force edit Distance?
   * Just try every operation on every char
   * and see if we can get to 0
   */
    public static int naiveDistance(String s1, String s2, HashMap<String, Integer> memo) {
      int maxScore = Math.max(s1.length(), s2.length());
      int score = maxScore;

      if (s2.equals(s1)) {
        return 0;
      }
      if (memo.containsKey(s2)) {
        return memo.get(s2);
      }

      if (s2.length() < s1.length()) {
        int localAddMin = maxScore;
        for (int i = 0; i < s1.length(); i++) {
          char charToAdd = s1.charAt(i);
          for (int j = 0; j <= s2.length(); j++) {
            String addTemp = addChar(s2, j, charToAdd);
            int addScore = naiveDistance(s1, addTemp, memo) + 1;
            localAddMin = addScore < localAddMin ? addScore : localAddMin;
          }
        }

        score = localAddMin;

      } else if (s2.length() == s1.length()) {
        int distance = 0;

        for (int i = 0; i < s1.length(); i++) {
          if (s1.charAt(i) != s2.charAt(i)) {
            distance++;
          }
        }

        score = distance;
      } else if (s2.length() > s1.length()) {
        for (int i = 0; i < s2.length(); i++) {
          String removeTemp = removeChar(s2, i);
          int removeScore = naiveDistance(s1, removeTemp, memo) + 1;
          score = removeScore < score ? removeScore : score;
        }
      }

      memo.put(s2, score);
      return score;
   }

  public static String replaceChar(String s, int i, char c) {
    return s.substring(0, i) + c + s.substring(i+1);
  }
  public static String addChar(String s, int i, char c) {
    return s.substring(0, i) + c + s.substring(i);
  }
  public static String removeChar(String s, int i) {
    return s.substring(0, i) + s.substring(i+1);
  }

}
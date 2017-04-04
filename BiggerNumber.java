import java.util.*;

public class BiggerNumber {

  public static long nextBiggerNumber(long n) {

    // look at last 2 digits of n
    // if they're in descending order sort them into ascendign order
    // if already in ascending order go to next 3 digits..
    // and continue to sort the numbers digits until you reach the front of the number
    // If we're at the frot of the string don't allow a 0 to go there
    // If the number is already in sorted order then return -1

    // get sub nuber and try to sort that
    String nStr = String.valueOf(n);

    if (isDescending(nStr)) {
      System.out.println(n);
      return -1;
    }


    for (int i = 0; i < nStr.length(); i++) {
      long power = (long)Math.pow(10, i+2);
      System.out.println(n);
      System.out.println(power);
      long subNum = n % power;
      String subStr = String.valueOf(subNum);
      System.out.println(n);
      System.out.println(power);
      System.out.println(subNum);
      System.out.println(subStr);
      if (isDescending(subStr)) {
        continue;
      } else {
        // switch the subnum to string
        // sort the digits // WRONG
        // swap the new digit with the next one
        //
        // switch n to a string
        // replace its tail?
        // or divide n by power
        // then add sorted SubNum back

        long sortedSubNum = nextBiggerNumberSlow(subNum);

        /*
        char[] subStrChars = subStr.toCharArray();

        Arrays.sort(subStrChars);
        String sortedSubNumStr = "";
        for (char c: subStrChars) {
          sortedSubNumStr = c + sortedSubNumStr;
        }

        System.out.println(sortedSubNumStr);
        long sortedSubNum = Long.valueOf(sortedSubNumStr);
        */
        n /= power;
        n *= power;
        n += sortedSubNum;

        return n;
      }
    }


    return 100;
  }



  public static Boolean isDescending(String n) {
    char lastChar = n.charAt(0);
    Boolean isDescending = true;
    for (char c : n.toCharArray()) {
      if (lastChar < c) {
        isDescending = false;
        break;
      }
      lastChar = c;
    }
    return isDescending;
  }



/******************************************************************************
 The slow but obvious version
 ******************************************************************************/
  public static long nextBiggerNumberSlow(long n){
    Map<String, List<String>> memo = new HashMap<>();
    List<String> allPerms = getAllPermutations(String.valueOf(n), memo, true);
    long min = Long.MAX_VALUE;
    for (String s : allPerms) {
      // find the min larger than n
      long sVal = Long.valueOf(s);
      if (sVal > n && sVal < min) {
        min = sVal;
      }
    }

    return min == Long.MAX_VALUE ? -1 : min;
  }

  public static List<String> getAllPermutations(String n, Map<String, List<String>> memo, Boolean start) {
    List<String> results = new ArrayList<String>();
    if (n.length() == 1) {
      results.add(n);
      memo.put(n, results);
      return results;
    }

    for (int i = 0; i < n.length(); i++) {
      char c = n.charAt(i);
      if (start && c == '0') {
        continue;
      }
      String shorterN = n.substring(0, i) + n.substring(i+1, n.length());
      List<String> subResults = getAllPermutations(shorterN, memo, false);
      for (int j = 0; j < subResults.size(); j++) {
        results.add(c + subResults.get(j));
      }
    }

    memo.put(n, results);
    return results;
  }

}
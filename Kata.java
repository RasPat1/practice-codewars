import java.util.*;

public class Kata {
  public static long nextSmaller(long n) {
    // convert to array of ints
    char[] chars = Long.toString(n).toCharArray();

    int[] ints = new int[chars.length];
    for (int i = 0; i < chars.length; i++) {
      ints[i] =   Character.digit(chars[i], 10);
    }

    // iterate through the digits from right to left
    // at each iteration try to find the largest digit to the right
    // that is smaller than the current digit

    int startingIndex = ints.length - 1;
    for (int i = ints.length - 2; i >= 0; i--) {
      // Don't allow 0 to be a max when comparing for the first digit
      // This stops leading zero issues
      int max = i == 0 ? 0 : Integer.MIN_VALUE;
      int maxIndex = -1;
      for (int j = i + 1; j < ints.length; j++) {
        // find and save max less than ints[i]
        if (ints[j] > max && ints[j] < ints[i]) {
          max = ints[j];
          maxIndex = j;
        }
      }

      // If we found a digit swap and break out of the loop.
      if (maxIndex >= 0) {
        // Swap
        int temp = ints[maxIndex];
        ints[maxIndex] = ints[i];
        ints[i] = temp;

        // Update startingIndex
        startingIndex = i;
        break;
      }
    }

    // Now sort the remainign digits descending to get the largest
    // number that is less than n
    for (int i = startingIndex + 1; i < ints.length - 1; i++) {
      int max = ints[i];
      int sortIndex = i;
      for (int j = i; j < ints.length; j++) {
        if (ints[j] > max) {
          max = ints[j];
          sortIndex = j;
        }
      }

      int temp = ints[i];
      ints[i] = max;
      ints[sortIndex] = temp;
    }
    // Convert back to a long and return
    String newNum = "";
    for (int i = 0; i < ints.length; i++) {
      newNum += ints[i];
    }
    long newValue = Long.valueOf(newNum);
    return newValue == n ? -1 : newValue;

  }
}
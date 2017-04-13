import java.util.*;

public class Conversion {
  public String solution(int n) {
    String nStr = String.valueOf(n);
    String result = "";
    int i = 0;
    while (i < nStr.length()) {
      char c = nStr.charAt(i);
      if (c != '0') {
        result += getNumeral(c, nStr.length() - i - 1);
      }
      i++;
    }
    return result;
  }

  public String getNumeral(char c, int pos) {
    long powerOfTen = (long)Math.pow(10, pos);
    int digit = Character.getNumericValue(c);
    String result = "";

    char[] digs = getDigitSet(pos);

    while (digit > 0) {
      if (digit == 9) {
        result += digs[0] +""+ digs[2];
        break;
      } else if (digit > 5) {
        result += digs[1];
        digit -= 5;
      } else if (digit == 4) {
        result += digs[0] +""+ digs[1];
        break;
      } else {
        result += digs[0];
        digit--;
      }
    }

    return result;
  }

  public char[] getDigitSet(int pos) {
    char[] digs = new char[3];

    switch (pos) {
      case 0: digs = new char[]{'I', 'V', 'X'}; break;
      case 1: digs = new char[]{'X', 'L', 'C'}; break;
      case 2: digs = new char[]{'C', 'D', 'M'}; break;
      case 3: digs = new char[]{'M', 'M', 'M'}; break;
      default: digs = new char[]{'I', 'I', 'I'}; break;
    }

    return digs;
  }
}
import java.util.*;

public class Smallfuck {
  public static String interpreter(String code, String tape) {
    char[] codeArray = code.toCharArray();
    char[] tapeArray = tape.toCharArray();
    int codeIndex = 0;
    int tapeIndex = 0;
    int count = 0;

    while (tapeIndex >= 0 && tapeIndex < tapeArray.length && codeIndex < codeArray.length) {
      char c = codeArray[codeIndex];

      switch(c) {
        case '>': tapeIndex++; break;
        case '<': tapeIndex--; break;
        case '*': flip(tapeArray, tapeIndex); break;
        case '[': codeIndex = jumpPast(codeArray, codeIndex, tapeArray[tapeIndex]); break;
        case ']': codeIndex = jumpBefore(codeArray, codeIndex, tapeArray[tapeIndex]); break;
        default: break;
      }

      count++;
      codeIndex++;
    }

    return new String(tapeArray);
  }

  public static void flip(char[] tapeArray, int i) {
    if (tapeArray[i] == '0') {
      tapeArray[i] = '1';
    } else if (tapeArray[i] == '1') {
      tapeArray[i] = '0';
    }
  }

  public static int jumpPast(char[] codeArray, int codeIndex, char currentCell) {
    if (currentCell == '0') {
      int openCount = 1;
      while (codeIndex < codeArray.length && (codeArray[codeIndex] != ']' || openCount != 0)) {
        codeIndex++;
        if (codeArray[codeIndex] == '[') {
          openCount++;
        } else if (codeArray[codeIndex] == ']') {
          openCount--;
        }
      }
    }
    return codeIndex;
  }
  public static int jumpBefore(char[] codeArray, int codeIndex, char currentCell) {
    if (currentCell == '1') {
      int closeCount = 1;
      while (codeIndex >= 0 && (codeArray[codeIndex] != '[' || closeCount != 0)) {
        codeIndex--;
        if (codeArray[codeIndex] == '[') {
          closeCount--;
        } else if (codeArray[codeIndex] == ']') {
          closeCount++;
        }
      }
    }
    return codeIndex;
  }

}
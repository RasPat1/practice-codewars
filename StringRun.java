import java.util.*;

public class StringRun {
	public static String rangeExtraction(int[] arr) {
    List<String> solution = new ArrayList<String>();
    int lowIndex = 0;
    Boolean onARun = false;

    for (int i = 0; i < arr.length; i++) {
      if (onARun) {
        if (arr.length - 1 == i) {
          // we're on a run and at the end
          if (i - lowIndex >= 2) {
            String run = arr[lowIndex] + "-" + arr[i];
            solution.add(run);
          } else {
            for (int start = lowIndex; start <= i; start++) {
              solution.add("" + arr[start]);
            }
          }
        } else if (arr[i+1] - arr[i] == 1) {
            // we're on a run so lets keep it going
            continue;
        } else {
          onARun = false;
          if (i - lowIndex >= 2) {
            String run = arr[lowIndex] + "-" + arr[i];
            solution.add(run);
          } else {
            for (int start = lowIndex; start <= i; start++) {
              solution.add("" + arr[start]);
            }
          }

        }
      } else {
        if (arr.length - 1 == i) {
          solution.add("" + arr[i]);
        } else if (arr[i+1] - arr[i] == 1) {
          lowIndex = i;
          onARun = true;
        } else {
          solution.add("" + arr[i]);
        }
      }
    }

    String stringResult = "";
    for (int i = 0; i < solution.size(); i++) {
      stringResult += solution.get(i);
      if (i != solution.size() -1) {
        stringResult += ",";
      }
    }

    return stringResult;
  }
}
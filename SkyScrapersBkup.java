import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class SkyScrapers {

  public static int[][] solvePuzzle (int[] clues) {
    int sideLength = (clues.length + 1) / 4;

    // solve based on clues.
    // Lock in squares determined by clues
    int[][] solution = new int[sideLength][sideLength];

    solveByClues(solution, clues);

    // benchmarking
    int[] count = new int[1];
    count[0] = 0;


    List<int[]> possibleRows = getAllPossibleRows(sideLength);
    // update to get all possible rows with locking...
    List<List<int[]>> possibleRowsWithLocking = getPossibleRowsWithLocking(solution);
    printArr(solution);

    solution = fillInGrid(solution, possibleRowsWithLocking, 0, clues, count);
    System.out.println("iterations:" + count[0]);

    return solution;

  }

  public static void solveByClues(int[][] solution, int[] clues) {
    for (int i = 0; i < clues.length; i++) {
      int clueVal = clues[i];

      if (clueVal == 1 || clueVal == solution.length) {
        setRowCol(solution, i, clueVal);
      }
    }
  }

  public static List<List<int[]>> getPossibleRowsWithLocking(int[][] solution) {
    List<int[]> allPossibleRows = getAllPossibleRows(solution.length);
    List<List<int[]>> allPossibleRowsWithLocking = new ArrayList<List<int[]>>();

    // for a given row filter out only those with the nums in the right place

    for (int i = 0; i < solution.length; i++) {
      // loop through each row and create the list of matches
      List<int[]> matches = new ArrayList<int[]>();
      allPossibleRowsWithLocking.add(matches);

      for (int[] row : allPossibleRows) {
        Boolean isAMatch = true;
        for (int j = 0; j < row.length; j++) {
          int lockedVal = solution[i][j];
          if (lockedVal != 0 && lockedVal != row[j]) {
            isAMatch = false;
            break;
          }
        }
        if (isAMatch) {
          matches.add(row);
        }
      }
    }

    return allPossibleRowsWithLocking;
  }

  public static int[][] fillInGrid(int[][] solution, List<List<int[]>> possibleRowsWithLocking, int rowNum, int[] clues, int[] count) {
    Boolean isLastRow = rowNum == (solution.length - 1);

    List<int[]> possibleRows = possibleRowsWithLocking.get(rowNum);
    for (int i = 0; i < possibleRows.size(); i++) {
      count[0]++;
      // int[] row = new int[solution.length];
      solution[rowNum] = Arrays.copyOf(possibleRows.get(i), solution.length);
      clearLaterRows(solution, rowNum);
      Boolean isValid = isValid(solution);

      if (isValid && isLastRow && isClueSafe(solution, clues, true)) {
        return solution;
      } else if (!isLastRow && isValid && isClueSafe(solution, clues, false)) {
        int[][] result = fillInGrid(solution, possibleRowsWithLocking, rowNum+1, clues, count);
        if (result[solution.length - 1][solution.length -1] != 0) {
          return result;
        }
      }
    }

    return new int[solution.length][solution.length];
  }

  public static void clearLaterRows(int[][] arr, int rowNum) {
    for (int x = rowNum + 1; x < arr.length; x++) {
      zeroRow(arr, x);
    }
  }

  public static void zeroRow(int[][] arr, int row) {
    for (int j = 0; j < arr[row].length; j++) {
      arr[row][j] = 0;
    }
  }

  public static Boolean isValid(int[][] solution) {
    // rows will always be valid right...
    for (int j = 0; j < solution[0].length; j++) {
      int[] heightCounts = new int[solution.length];

      for (int i = 0; i < solution.length; i++) {
        int height = solution[i][j];
        if (height > 0) {
          heightCounts[height - 1]++;
        }
      }
      for (int i = 0; i < heightCounts.length; i++) {
        if (heightCounts[i] > 1) {
          return false;
        }
      }
    }

    return true;
  }

  public static Boolean isClueSafe(int[][] solution, int[] clues, Boolean isStrict) {
    for (int i = 0; i < clues.length; i++) {
      if (clues[i] != 0) {
        int[] rowCol = getRowCol(solution, i);
        if (isStrict || isFullyIntialized(rowCol)) {
          // System.out.println(Arrays.toString(rowCol));
          int visibleBuildings = 1;
          int maxSoFar = rowCol[0];
          for (int j = 1; j < rowCol.length; j++) {
            if (rowCol[j] > maxSoFar) {
              visibleBuildings++;
              maxSoFar = rowCol[j];
            }
          }
          if (clues[i] != visibleBuildings) {
            return false;
          }
        }

      }
    }
    return true;
  }

  public static Boolean isFullyIntialized(int[] row) {
    for (int i = 0; i < row.length; i++) {
      if (row[i] == 0) {
        return false;
      }
    }
    return true;
  }

  public static int[] getRowCol(int[][] solution, int clueNum) {
    List<Integer> list = new ArrayList<Integer>();
    int section = clueNum / solution.length;
    Boolean backwards = section == 1 || section == 2;
    Boolean isCol = section == 0 || section == 2;
    Boolean isCountBack = section == 2 || section == 3;
    int index = clueNum % solution.length;

    if (isCountBack) {
      index = solution.length - index - 1;
    }
    for (int i = 0; i < solution.length; i++) {

      if (isCol) {
        list.add(solution[i][index]);
      } else {
        list.add(solution[index][i]);
      }
    }

    if (backwards) {
      Collections.reverse(list);
    }
    int[] result = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  public static void setRowCol(int[][] solution, int clueNum, int clueVal) {
    List<Integer> list = new ArrayList<Integer>();
    int section = clueNum / solution.length;
    Boolean backwards = section == 1 || section == 2;
    Boolean isCol = section == 0 || section == 2;
    Boolean isCountBack = section == 2 || section == 3;
    int index = clueNum % solution.length;

    if (isCountBack) {
      index = solution.length - index - 1;
    }

    int clueMinVal = 1;
    int clueMaxVal = solution.length;
    for (int i = 0; i < solution.length; i++) {
      int realI = i;

      if (backwards) {
        realI = solution.length - i - 1;
      }

      if (isCol) {
        if (clueVal == clueMinVal) {
          solution[realI][index] = clueMaxVal;
          break;
        } else if (clueVal == clueMaxVal) {
          solution[realI][index] = i + 1;
        }
      } else {
        if (clueVal == clueMinVal) {
          solution[index][realI] = clueMaxVal;
          break;
        } else if (clueVal == clueMaxVal) {
          solution[index][realI] = i + 1;
        }
      }
    }
  }

  public static List<int[]> getAllPossibleRows(int size) {
    List<List<Integer>> allPerms = new ArrayList<>();
    List<int[]> allPermsFormatted = new ArrayList<>();

    int[] nums = new int[size];
    for (int i = 0; i < nums.length; i++) {
      nums[i] = i+1;
    }

    allPerms = permute(nums);

    for (List<Integer> list : allPerms) {
      int[] arr = new int[list.size()];
      for (int i =0; i < list.size(); i++) {
        arr[i] = list.get(i);
      }
      allPermsFormatted.add(arr);
    }

    return allPermsFormatted;
  }

  public static List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> results = new ArrayList<List<Integer>>();
      if (nums == null || nums.length == 0) {
          return results;
      }
      List<Integer> result = new ArrayList<>();
      dfs(nums, results, result);
      return results;
  }

  public static void dfs(int[] nums, List<List<Integer>> results, List<Integer> result) {
      if (nums.length == result.size()) {
          List<Integer> temp = new ArrayList<>(result);
          results.add(temp);
      }
      for (int i=0; i<nums.length; i++) {
          if (!result.contains(nums[i])) {
              result.add(nums[i]);
              dfs(nums, results, result);
              result.remove(result.size() - 1);
          }
      }
  }

  public static void printArr(int[][] arr) {
    System.out.println();
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        System.out.print(arr[i][j]);
      }
      System.out.println();
    }
  }

}

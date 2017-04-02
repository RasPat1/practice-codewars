import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;

public class UploadedSkyScraper {

  static List<int[]> allRowPermutations;

  public static int[][] solvePuzzle (int[] clues) {
    int sideLength = (clues.length + 1) / 4;

    // solve based on clues.
    // Lock in squares determined by clues
    int[][] solution = new int[sideLength][sideLength];

    allRowPermutations = getAllPossibleRows(sideLength);
    solveByCluesStart(solution, clues);
    int[][] lockGrid = new int[sideLength][sideLength];

    for (int i = 0; i < sideLength; i++) {
      for (int j = 0; j < sideLength; j++) {
        lockGrid[i][j] = solution[i][j];
      }
    }

    // benchmarking
    int[] count = new int[1];
    count[0] = 0;

    solution = fillInGrid(solution, getPossibleRowsWithLocking(solution), lockGrid, 0,  clues, count);

    return solution;
  }

  public static void solveByCluesStart(int[][] solution, int[] clues) {
    for (int i = 0; i < clues.length; i++) {
      int clueVal = clues[i];

      if (clueVal == 1 || clueVal == solution.length) {
        setRowCol(solution, i, clueVal);
      }
    }
  }

  // given a solution return all possible values for the specifed row
  public static List<int[]> getPossibleRowsWithPartialCompletion(int[][] solution, List<int[]> possibleWithLocking, int rowNum) {
    List<int[]> possibleForRow = new ArrayList<int[]>();
    List<Set<Integer>> buildingsInColumn = new ArrayList<Set<Integer>>();

    for (int i = 0; i < solution.length; i++) {
      Set<Integer> set = new HashSet<Integer>();
      buildingsInColumn.add(set);
    }

    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution.length; j++) {
        int val = solution[i][j];
        if (val != 0 && i != rowNum) {
          buildingsInColumn.get(j).add(val);
        }
      }
    }

    for (int[] row : possibleWithLocking) {
      Boolean isAMatch = true;
      for (int i = 0; i < row.length; i++) {
        int valToCheck = row[i];
        if (buildingsInColumn.get(i).contains(row[i])) {
          isAMatch = false;
          break;
        }
      }

      if (isAMatch) {
        possibleForRow.add(row);
      }
    }

    return possibleForRow;
  }

  public static List<List<int[]>> getPossibleRowsWithLocking(int[][] solution) {
    List<List<int[]>> allPossibleRowsWithLocking = new ArrayList<List<int[]>>();

    // for a given row filter out only those with the nums in the right place

    for (int i = 0; i < solution.length; i++) {
      // loop through each row and create the list of matches
      List<int[]> matches = new ArrayList<int[]>();
      allPossibleRowsWithLocking.add(matches);

      for (int[] row : allRowPermutations) {
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

  public static int getVisibleBuildings(int[] row) {
    int visibleBuildings = 0;
    int maxHeight = -1;

    for (int height : row) {

      if (height > maxHeight) {
        visibleBuildings++;
        maxHeight = height;
      }
    }
    return visibleBuildings;
  }

  public static List<int[]> maxFilteredRows(int[][] solution, List<int[]> rows, int[] clues, int rowNum) {
    List<int[]> maxFilteredRows = new ArrayList<int[]>(rows.size());
    for (int[] row : rows) {
      // Start by using row clues to filter

      int clueNum1 = solution.length + rowNum;
      int clueNum2 = clues.length - 1 - rowNum;
      int clueVal1 = clues[clueNum1];
      int clueVal2 = clues[clueNum2];

      if (clueVal2 != 0 && getVisibleBuildings(row) != clueVal2) {
        continue;
      }

      int[] reverseArray = new int[row.length];

      for (int i = 0; i < row.length; i++) {
        reverseArray[i] = row[row.length - 1 - i];
      }

      if (clueVal1 != 0 && getVisibleBuildings(reverseArray) != clueVal1) {
        continue;
      }


      int maxPos = -1;
      Boolean hasZero = false;
      for (int i = 0; i < row.length; i++) {
        if (row[i] == solution.length) {
          maxPos = i;
        }
      }
      if (maxPos != -1 && clues[maxPos] != 0) {
        int[] col = new int[rowNum + 1];
        for (int j = 0; j < col.length - 1; j++) {
          col[j] = solution[j][maxPos];
          if (solution[j][maxPos] == 0) {
            hasZero = true;
            break;
          }
        }
        col[rowNum] = solution.length;
        if (!hasZero) {
          col[rowNum] = solution.length;
          if (getVisibleBuildings(col) != clues[maxPos]) {
            continue;
          }
        }
      }
    maxFilteredRows.add(row);
    }

    return maxFilteredRows;
  }

  public static int[][] fillInGrid(int[][] solution,
     List<List<int[]>> possibleRowsWithLocking, int[][] lockGrid,
      int rowNum, int[] clues, int[] count) {

    Boolean isLastRow = rowNum == (solution.length - 1);

    // try to fill in grid a bit before getting rows and what not.

    List<int[]> possibleRows = possibleRowsWithLocking.get(rowNum);

    List<int[]> possibleRowsFromPartialCompleteionAndLocking = getPossibleRowsWithPartialCompletion(solution, possibleRows, rowNum);
    List<int[]> maxFilteredRows = maxFilteredRows(solution, possibleRowsFromPartialCompleteionAndLocking, clues, rowNum);
    for (int i = 0; i < maxFilteredRows.size(); i++) {
      count[0]++;
      solution[rowNum] = Arrays.copyOf(maxFilteredRows.get(i), solution.length);
      clearLaterRows(solution, rowNum, lockGrid);

      if (isLastRow) {
        if (isValidCol(solution) && isClueSafe(solution, clues, false)) {
          return solution;
        }
      } else {
        if (isValidCol(solution) && isClueSafe(solution, clues, true)) {
          int[][] result = fillInGrid(solution, possibleRowsWithLocking, lockGrid, rowNum+1, clues, count);
          if (result[solution.length - 1][solution.length -1] != 0) {
            return result;
          }
        }
      }
    }

    return new int[solution.length][solution.length];
  }

  public static void clearLaterRows(int[][] arr, int rowNum, int[][] lockGrid) {
    for (int i = rowNum + 1; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = lockGrid[i][j];
      }
    }
  }

  public static Boolean isComplete(int[][] solution) {
    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution[1].length; j++) {
        if (solution[i][j] < 1 || solution[i][j] > solution.length) {
          return false;
        }
      }
    }

    return true;
  }

  public static Boolean isValidCol(int[][] solution) {
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

  public static Boolean isClueSafe(int[][] solution, int[] clues, Boolean tryFillIn) {
    for (int i = 0; i < clues.length; i++) {
      if (clues[i] != 0) {
        int[] rowCol = getRowCol(solution, i);
        int firstZeroIndex = -1;
        int zeroCount = 0;
        int maxBulidingIndex = -1;
        int maxSoFar = -1;
        int visibleBuildings = 0;

        for (int j = 0; j < rowCol.length; j++) {
          if (rowCol[j] == 0) {
            if (firstZeroIndex == -1) {
              firstZeroIndex = j;
            }
            zeroCount++;
          } else if (rowCol[j] == rowCol.length) {
            maxBulidingIndex = j;
          }

          if (rowCol[j] > maxSoFar) {
            visibleBuildings++;
            maxSoFar = rowCol[j];
          }
        }

        if (firstZeroIndex != -1) {
          if (maxBulidingIndex != -1 && maxBulidingIndex < firstZeroIndex) {
            if (clues[i] != visibleBuildings) {
              return false;
            }
          } else if (tryFillIn && zeroCount == 1) {
            Set<Integer> usedValues = new HashSet<Integer>();
            for (int n = 0; n < rowCol.length; n++) {
              usedValues.add(rowCol[n]);
            }
            Set<Integer> remainingValues = new HashSet<Integer>();
            for (int n = 0; n < solution.length; n++) {
              if (!usedValues.contains(n + 1)) {
                remainingValues.add(n + 1);
              }
            }
            if (remainingValues.size() == 1 && zeroCount == 1) {
              setValueFromClueAndIndex(solution, i, firstZeroIndex, remainingValues.iterator().next());
            }
          }
        } else if (clues[i] != visibleBuildings) {
          return false;
        }

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

  public static void setValueFromClueAndIndex(int[][] solution, int clueNum, int rowIndex, int value) {
    int section = clueNum / solution.length;
    int iResult;
    int jResult;
    if (section == 0) {
      iResult = rowIndex;
      jResult = clueNum;
    } else if (section == 1) {
      iResult = clueNum % solution.length;
      jResult = solution.length - rowIndex - 1;
    } else if (section == 2) {
      iResult = solution.length - 1 - rowIndex;
      jResult = solution.length - 1 - (clueNum % solution.length);
    } else {
      iResult = solution.length -1 - (clueNum % solution.length);
      jResult = rowIndex;
    }

    solution[iResult][jResult] = value;
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

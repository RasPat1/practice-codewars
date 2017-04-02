import java.util.*;

public class SkyScrapers {

  static int[][] solvePuzzle (int[] clues) {
    int sideLength = (clues.length + 1) / 4;
    // don't think, just fucking brute force it
    long start = System.currentTimeMillis();
    List<int[]> possibleRows = getAllPossibleRows(sideLength);
    System.out.println(possibleRows.size());
    long time = System.currentTimeMillis() - start;

    int[][] solution = new int[sideLength][sideLength];
    for (int i = 0; i < possibleRows.size(); i++) {
      int rowNum = 0;
      solution[rowNum] = possibleRows.get(i).clone();
      clearLaterRows(solution, rowNum);

      for (int j = 0; j < possibleRows.size(); j++) {
        rowNum = 1;
        solution[rowNum] = possibleRows.get(j).clone();
        clearLaterRows(solution, rowNum);

        if (isValid(solution)) {
          for (int k = 0; k < possibleRows.size(); k++) {
            rowNum = 2;
            solution[rowNum] = possibleRows.get(k).clone();
            clearLaterRows(solution, rowNum);

            if (isValid(solution)) {
              for (int l = 0; l < possibleRows.size(); l++) {
                rowNum = 3;
                solution[rowNum] = possibleRows.get(l).clone();
                clearLaterRows(solution, rowNum);

                if (isValid(solution)) {
                  for (int m = 0; m < possibleRows.size(); m++) {
                    rowNum = 4;
                    solution[rowNum] = possibleRows.get(m).clone();
                    clearLaterRows(solution, rowNum);

                    if (isValid(solution)) {
                      for (int n = 0; n < possibleRows.size(); n++) {
                        rowNum = 5;
                        solution[rowNum] = possibleRows.get(n).clone();
                        // clearLaterRows(solution, rowNum);

                        if (isValid(solution) && isClueSafe(solution, clues)) {
                          return solution;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return new int[sideLength][sideLength];
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

  public static void printArr(int[][] arr) {
    System.out.println();
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        System.out.print(arr[i][j]);
      }
      System.out.println();
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

  public static Boolean isClueSafe(int[][] solution, int[] clues) {
    for (int i = 0; i < clues.length; i++) {
      if (clues[i] != 0) {
        int[] rowCol = getRowCol(solution, i);
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

}

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Arrays;

public class SkyScrapers {

  static List<int[]> allRowPermutations;
  static List<Set<Integer>> buildingsInColumn = new ArrayList<Set<Integer>>();

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
        // if (solution[i][j] != 0) {
        //   lockGrid[i][j] = true;
        // } else {
        //   lockGrid[i][j] = false;
        // }
      }
    }

    // benchmarking
    int[] count = new int[1];
    count[0] = 0;

    printArr(solution);

    for (int j = 0; j < solution.length; j++) {
      Set<Integer> colSet = new HashSet<Integer>();
      for (int i = 0; i < solution.length; i++) {
        if (solution[i][j] != 0) {
          // colSet.add(solution[i][j]);
        }
      }
      buildingsInColumn.add(colSet);
    }
    debugColSet();
    // solveByClues(solution, clues);
    // solution = guess(solution, clues, count);
    solution = fillInGrid(solution, getPossibleRowsWithLocking(solution), lockGrid, 0,  clues, count);
    printArr(solution);
    System.out.println("iterations:" + count[0]);
    return solution;
  }

  public static void debugColSet() {
    for (int j = 0; j < buildingsInColumn.size(); j++) {
      System.out.println();
      System.out.print("[");
      for (int i : buildingsInColumn.get(j)) {
        System.out.print(i + ",");
      }
      System.out.print("]");
    }
  }

  public static int[] getNextEmptySquare(int[][] solution) {
    //find empty square
    int[] result = new int[2];
    result[0] = -1;
    result[1] = -1;

    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution[i].length; j++) {
        if (solution[i][j] == 0) {
          result[0] = i;
          result[1] = j;
          return result;
        }
      }
    }

    return result;
  }

  public static int[][] guess(int[][] solution, int[] clues, int[] count) {

    int[][] origSolution = copyArr(solution);

    int[] cell = getNextEmptySquare(solution);
    int i = cell[0];
    int j = cell[1];

    if (i >= 0 || j >= 0) {
      Set<Integer> validGuesses = getValidGuesses(solution, i, j);

      for (int validGuess : validGuesses) {
        count[0]++;
        int[][] guessedSolution;
        solution = copyArr(origSolution);

        solution[i][j] = validGuess;
        // solveByClues(solution, clues); // Pruning step
        if (isSolved(solution, clues)) {
          printArr(solution);
          return solution;
        } else {
          guessedSolution = guess(solution, clues, count);
        }

        if (isSolved(guessedSolution, clues)) {
          return guessedSolution;
        }
      }
    }

    // At this point we've went through all the valid guesses and they dont seem to work
    // rollback the solution array and try another guess one level up
    return origSolution;
  }

  public static Set<Integer> getValidGuesses(int[][] solution,int xPos,int yPos) {
    // List<Integer> result = new ArrayList<Integer>();
    Set<Integer> result = new HashSet<Integer>();
    for (int i = 0; i < solution.length; i++) {
      result.add(i+1);
    }

    for (int i = 0; i < solution.length; i++) {
      result.remove(solution[i][yPos]);
    }
    for (int j = 0; j < solution.length; j++) {
      result.remove(solution[xPos][j]);
    }

    return result;
  }



  /*
  * Does most of the solve logic!
  */
  public static Boolean solveByClues(int[][] solution, int[] clues) {
    // potential determinstic conditions
    // n-1 out of n buildings placed

    return false;
  }



  public static void solveByCluesStart(int[][] solution, int[] clues) {
    for (int i = 0; i < clues.length; i++) {
      int clueVal = clues[i];

      if (clueVal == 1 || clueVal == solution.length) {
        setRowCol(solution, i, clueVal);
      }
    }
  }

  public static void printSet(Set<Integer> set) {
    for (Integer i : set) {
      System.out.println(i);
    }
  }

  // given a solution return all possible values for the specifed row
  public static List<int[]> getPossibleRowsWithPartialCompletion(int[][] solution, List<int[]> possibleWithLocking, int rowNum) {
    List<int[]> possibleForRow = new ArrayList<int[]>();
    // debugColSet();
    for (int[] row : possibleWithLocking) {
      Boolean addIt = true;
      for (int j = 0; j < solution.length; j++) {
        // System.out.println(row[j]);
        // System.out.println(Arrays.toString(buildingsInColumn.get(j).toArray()));
        if (addIt && buildingsInColumn.get(j).contains(row[j])) {
          addIt = false;
          break;
        }
      }
      if (addIt) {
        possibleForRow.add(row);
      }
    }


    // List<Set<Integer>> buildingsInColumn = new ArrayList<Set<Integer>>();

    // for (int i = 0; i < solution.length; i++) {
    //   Set<Integer> set = new HashSet<Integer>();
    //   buildingsInColumn.add(set);
    // }

    // for (int i = 0; i < solution.length; i++) {
    //   for (int j = 0; j < solution.length; j++) {
    //     int val = solution[i][j];
    //     if (val != 0 && i != rowNum) {
    //       buildingsInColumn.get(j).add(val);
    //     }
    //   }
    // }

    // // System.out.println("These are teh buildigns already in teh column");
    // // for (int i = 0; i < buildingsInColumn.size(); i++) {
    // //   System.out.println("COlumn: " + i);
    // //   printSet(buildingsInColumn.get(i));
    // // }

    // for (int[] row : possibleWithLocking) {
    //   Boolean isAMatch = true;
    //   for (int i = 0; i < row.length; i++) {
    //     int valToCheck = row[i];
    //     if (buildingsInColumn.get(i).contains(row[i])) {
    //       isAMatch = false;
    //       break;
    //     }
    //   }

    //   if (isAMatch) {
    //     possibleForRow.add(row);
    //   }
    // }



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

  public static Boolean getVisibleBuildings(int[] row, int clueVal) {
    int visibleBuildings = 0;
    int maxHeight = -1;

    for (int height : row) {

      if (height > maxHeight) {
        visibleBuildings++;
        maxHeight = height;
      }
      if (visibleBuildings > clueVal) {
        return false;
      }
    }
    return visibleBuildings == clueVal;
  }

  public static List<int[]> maxFilteredRows(int[][] solution, List<int[]> rows, int[] clues, int rowNum) {
    List<int[]> maxFilteredRows = new ArrayList<int[]>(rows.size());
    for (int[] row : rows) {
      // Start by using row clues to filter

      int clueNum1 = solution.length + rowNum;
      int clueNum2 = clues.length - 1 - rowNum;
      int clueVal1 = clues[clueNum1];
      int clueVal2 = clues[clueNum2];

      if (clueVal2 != 0 && !getVisibleBuildings(row, clueVal2)) {
        continue;
      }

      int[] reverseArray = new int[row.length];

      for (int i = 0; i < row.length; i++) {
        reverseArray[i] = row[row.length - 1 - i];
      }

      if (clueVal1 != 0 && !getVisibleBuildings(reverseArray, clueVal1)) {
        continue;
      }

      // maybe just do tha basics and avoid columns with double vals!


      if (rowNum > -1) { // only do this intensive thing at the earliest moments
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
            // int count = getVisibleBuildings(col);
            // if (count != clues[maxPos]) {
            //   continue;
            // }
            if (!getVisibleBuildings(col, clues[maxPos])) {
              continue;
            }
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
    List<int[]> maxFilteredRows = possibleRowsFromPartialCompleteionAndLocking.size() > 1 ?
      maxFilteredRows(solution, possibleRowsFromPartialCompleteionAndLocking, clues, rowNum) :
      possibleRowsFromPartialCompleteionAndLocking;
    // if (count[0] % 1000 == 0) {
    //   System.out.println("rowNum: " + rowNum + ":" + possibleRows.size() + "->" + possibleRowsFromPartialCompleteionAndLocking.size() + "->" + maxFilteredRows.size());
    // }
    for (int i = 0; i < maxFilteredRows.size(); i++) {
      count[0]++;
      int[] row = maxFilteredRows.get(i);
      // solution[rowNum] = Arrays.copyOf(maxFilteredRows.get(i), solution.length);
      for (int j = 0; j < solution.length; j++) {
        solution[rowNum][j] = row[j];
        buildingsInColumn.get(j).add(row[j]);
      }
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
      for (int j = 0; j < solution.length; j++) {
        // if (lockGrid[rowNum][j] == 0) {
          buildingsInColumn.get(j).remove(row[j]);
        // }
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

  public static Boolean isSolved(int[][] solution, int[] clues) {
    return isComplete(solution) && isValidGrid(solution) && isClueSafe(solution, clues, true);
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

  public static Boolean isValidGrid(int[][] solution) {
    int[][] heights = new int[solution.length * 2][solution.length];

    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution.length; j++) {
        int val = solution[i][j];

        if (val == 0) {
          continue;
        }

        int key1 = i;
        int key2 = solution.length + j;

        heights[key1][val-1]++;
        if (heights[key1][val-1] == 2) {
          return false;
        }

        heights[key2][val-1]++;
        if (heights[key2][val-1] == 2) {
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
  // modify clueSafe to fill in values if it can
  public static Boolean isClueSafe(int[][] solution, int[] clues, Boolean tryFillIn) {
    for (int i = 0; i < clues.length; i++) {
      if (clues[i] != 0) {
        int[] rowCol = getRowCol(solution, i);
        // if (isStrict || isFullyIntialized(rowCol)) {
          // System.out.println(Arrays.toString(rowCol));
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

        // check if there are values remaining for squares
        if (tryFillIn) { // simplest version of tryfillin

        }

        if (firstZeroIndex != -1) {
          if (maxBulidingIndex != -1 && maxBulidingIndex < firstZeroIndex) {
            if (clues[i] != visibleBuildings) {
              return false;
            }
          } else if (tryFillIn && zeroCount == 1) {
            // try to fill anything in

            // there is one zero
            // get all the possible values that are clue safe
            // we ahve the clue
            // we have the vlueRow
            // get all teh numbers it can't be from other stuff..
            // oof yikes we actualyl don't know what else is in the column...
            // but at least try on teh row
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
              // set that spot.
              setValueFromClueAndIndex(solution, i, firstZeroIndex, remainingValues.iterator().next());
            }
          }
        } else if (clues[i] != visibleBuildings) {
          return false;
        }
        // }

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
    int[] result = new int[solution.length];
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
        result[i] = solution[i][index];
      } else {
        result[i] = solution[index][i];
      }
    }

    if (backwards) {
      for (int i = 0; i < solution.length / 2; i++) {
        int tmp = result[i];
        result[i] = result[solution.length - 1 - i];
        result[solution.length - 1 - i] = tmp;
      }
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

  public static int[][] copyArr(int[][] arr) {
    int[][] newArr = new int[arr.length][arr.length];

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        newArr[i][j] = arr[i][j];
      }
    }

    return newArr;
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

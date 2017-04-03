import java.util.*;

public class SkyScrapersReal {
  static int dim;
  static Boolean finished = false;
  static int iterations = 0; // for debugging

  static int remainingSquares = 0;
  static List<Set<Integer>> candidates;
  static Set<Integer> defaultSet;

  static int[] nextMin = new int[2];
  static int rowColCalls = 0;

  static Boolean changedSinceLastUpdate = true;
  static final int SIDES_ON_SQUARE = 4;

  static class Move {
    int iPos;
    int jPos;

    int oldVal;
    int newVal;
  }

  public static int[][] solvePuzzle(int[] clues) {
    dim = (clues.length + 1) / SIDES_ON_SQUARE;

    int[][] solution = new int[dim][dim];

    finished = false;
    iterations = 0;
    remainingSquares = dim * dim;
    candidates = new ArrayList<Set<Integer>>(dim * dim);
    defaultSet = new HashSet<Integer>(dim);
    for (int i = 0; i < dim; i++) {
      defaultSet.add(i + 1);
    }
    for (int i = 0; i < dim*dim; i++) {
      candidates.add(new HashSet<Integer>(dim));
    }
    backtrack(solution, clues);
    printArr(solution);
    System.out.println("Iterations: " + iterations);
    System.out.println("rowColCalls: " + rowColCalls);
    System.out.println("SOlved");

    return solution;
  }

  public static void backtrack(int[][] solution, int[] clues) {
    // System.out.println("Just another call to backtrack");
    if (isASolution(solution, clues)) {
      finished = true;
      return;
    } else {
      int[] ij = getNextSquare(solution, clues);
      int i = ij[0];
      int j = ij[1];

      // System.out.println("ij before: " + Arrays.toString(ij));
      // System.out.println("k before: " + k);

      if (i < 0 || j < 0) {
        return;
      }

      int k = getK(ij[0], ij[1]);
      // System.out.println("ij: " + Arrays.toString(ij));
      // System.out.println("k: " + k);
      Set<Integer> candidatesForK = candidates.get(k);
      // System.out.println(Arrays.toString(candidatesForK.toArray()));
      // To avoid concurrentHashMapModifications
      int[] iterate = new int[candidatesForK.size()];
      int index = 0;
      for(int kCandidate: candidatesForK) {
        iterate[index] = kCandidate;
        index++;
      }

      List<Move> moves = new ArrayList<Move>();
      for (int newVal : iterate) {
        iterations++;
        makeMoves(solution, i, j, newVal, moves, clues);

        // debug(solution);
        // try {
        //   Thread.sleep(1000);
        // } catch (Exception e) {}

        backtrack(solution, clues);

        if (finished) {
          return;
        }
        // System.out.println("Backing Out");
        unmakeMoves(solution, moves, clues);
      }
    }
    // System.out.println("Ending Backtrack down this path");
  }

  public static int[] getNextSquare(int[][] solution, int[] clues) {
    return updateAllCandidateLists(solution, clues);
  }

  public static int[] updateAllCandidateLists(int[][] solution, int[] clues) {
    int[] result = new int[2];
    result[0] = -1;
    result[1] = -1;

    int[] failResult = new int[2];
    failResult[0] = -1;
    failResult[1] = -1;

    // if (changedSinceLastUpdate == false) {
    //   return nextMin;
    // }
    changedSinceLastUpdate = false;

    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution.length; j++) {
        int k = getK(i, j);
        // Set<Integer> set = candidates.get(k);
        // set.addAll(defaultSet);
        candidates.set(k, new HashSet<Integer>(defaultSet));
      }
    }

    // remove everyone in the same row or col excluding itself...

    // Also if a column or row has sets both only allow the same value that can't be
    // ie if there are 2 open spaces and both of them can only be 3's
    // or if there are n open spaces and N+1 options for n spaces

    // if the union of all the sets in a row or column is not equal to the default vector
    // back out

    int clueMin = 1;
    int clueMax = dim;

    for (int clueNum = 0; clueNum < clues.length; clueNum++) {
      int clueVal = clues[clueNum];
      if (clueVal == 0) {
        continue;
      } else if (clueVal == clueMin) { // only one possible value for one square
        int[] ij = getIAndJByClueNum(clueNum, 0);
        int k = getK(ij[0], ij[1]);
        Set set = candidates.get(k);

        List<Integer> arr = Arrays.asList(clueMax);

        set.retainAll(arr);
      } else if (clueVal == clueMax) {
        // only one possibile value for dim squares
        for (int m = 0; m < clueMax; m++) {
          Set<Integer> newSet = new HashSet<Integer>();
          newSet.add(m+1);
          int[] ij = getIAndJByClueNum(clueNum, m);
          int k = getK(ij[0], ij[1]);
          candidates.get(k).retainAll(newSet);
        }
      } else {
        //

        // if one zero logic
        int zeroCount = 0;
        int zeroCountBeforeMax = 0;
        int firstZeroIndex = -1;
        int maxHeight = -1;
        int maxHeightIndex = -1;
        int[] rowCol = getRowCol(solution, clueNum);
        int visibleBuildings = 0;
        int buildingsVisibleBeforeZero = 0;
        int buildingsVisibleAfterZeroBeforeMax = 0;



        int maxBeforeFirstZero = -1;
        int maxAfterFirstZeroBeforeMax = -1;

        for (int i = 0; i < rowCol.length; i++) {
          int val = rowCol[i];
          if (val != 0) {
            if (val > maxHeight) {
              maxHeight = val;
              maxHeightIndex = i;
              visibleBuildings++;
            }
          } else if (val == 0) {
            zeroCount++;
            if (firstZeroIndex == -1) {
              firstZeroIndex = i;
            }
          }
          if (firstZeroIndex == -1) {
            buildingsVisibleBeforeZero++;
          }
        }
        for (int i = 0; i < rowCol.length; i++) {
          int val = rowCol[i];
          if (val != 0 && i < firstZeroIndex && firstZeroIndex != -1 && i < maxHeightIndex) {
            if (val > maxBeforeFirstZero) {
              maxBeforeFirstZero = val;
            }
          }

          if (val == 0 && i < maxHeightIndex) {
            zeroCountBeforeMax++;
          }
          if (val != 0 && i > firstZeroIndex && firstZeroIndex != -1 && i < maxHeightIndex) {
            buildingsVisibleAfterZeroBeforeMax++;
          }
        }

        for (int i = 0; i < rowCol.length; i++) {
          int val = rowCol[i];
          if (i > firstZeroIndex && i < maxHeightIndex && val > maxBeforeFirstZero) {
            maxAfterFirstZeroBeforeMax = val;
          }
        }

        // now fuck around with these vaaars yo
        // if (clueVal == visibleBuildings) {

        // }

        // if we know the max before the zero and the max after the zero// and either there's only one zero or a max==dim buildings behind
        // then the zero has to be between the maxBefore and the min larger than if we need one more sisbleCOunt
        // if we need the same visible count it ahs to be shorter than maxBefore

        if (visibleBuildings + zeroCount < clueVal) {
          return failResult;
        }

        // wall in the back
        if (zeroCount == 1 && clueVal - 1 > visibleBuildings) {
          return failResult;
        }
        if (clueVal - zeroCount > visibleBuildings && zeroCountBeforeMax == 0 && maxHeight == dim) {
          return failResult;
        }
        if (firstZeroIndex != -1 && maxHeightIndex != -1 && firstZeroIndex < maxHeightIndex && maxHeight == dim && zeroCountBeforeMax == 1) { // the same applies for
          int[] ij = getIAndJByClueNum(clueNum, firstZeroIndex);
          int k = getK(ij[0], ij[1]);
          Set<Integer> firstZeroCandidates = candidates.get(k);
          // System.out.println("clueVal" + clueVal);
          // System.out.println(Arrays.toString(firstZeroCandidates.toArray()));
          // System.out.println(Arrays.toString(rowCol));

          for (Iterator<Integer> i = firstZeroCandidates.iterator(); i.hasNext();) {
            int iter = i.next();
            if (visibleBuildings == clueVal && iter < maxAfterFirstZeroBeforeMax &&  iter > maxBeforeFirstZero && iter < maxAfterFirstZeroBeforeMax && maxBeforeFirstZero != -1) {
              i.remove();
            } else if (visibleBuildings < clueVal && (iter < maxBeforeFirstZero || iter > maxAfterFirstZeroBeforeMax)) { //  we need to add one in between
              // i.remove();
            } else if (visibleBuildings > clueVal && iter < maxAfterFirstZeroBeforeMax) {
              // i.remove();
            }
          }
          if (firstZeroCandidates.size() == 0) {
            return failResult;
          }

          // if (visibleBuildings > clueVal) {
            // we need the zero to become a new max
            // to cover up some guys behind it
          // }

        }

        // there aren't any zeroes before the max and it doesn't match up
        if (maxHeight == dim && zeroCountBeforeMax == 0 && visibleBuildings != clueVal) {
          return failResult;
        }


        // if you have a really tall tower early on some clues can't work
        // you can have a tower that worsk for you in every space in front so the criteria is less
        // strict if you push it farther abck or the tower isnot as tall
        if (maxHeightIndex != -1 && dim - maxHeight + 1 < clueVal - maxHeightIndex) {
          return failResult;
        }


        // there is one zero before the max
        // if (maxHeight == dim && zeroCountBeforeMax == 1) {
        //   // try each of the candidates at that location
        //   // remove the ones that don't lead to a good visibleCount
        //   // what's the ij of the spot we're looking at
        //   int[] ij = getIAndJByClueNum(clueNum, firstZeroIndex);
        //   int k = getK(ij[0], ij[1]);
        //   for (Iterator<Integer> i = candidates.get(k).iterator(); i.hasNext();) {
        //     rowCol[firstZeroIndex] = i.next();
        //     if (getVisibleBuildings(rowCol) != clueVal) {
        //         i.remove();
        //     }
        //   }
        //   rowCol[firstZeroIndex] = 0;
        // }

        // there is one zero
        if (zeroCount == 1 || (zeroCountBeforeMax == 1 && maxHeight == dim)) { //maxHeight == dim && zeroCountBeforeMax == 1) {
          // try each of the candidates at that location
          // remove the ones that don't lead to a good visibleCount
          // what's the ij of the spot we're looking at
          int[] ij = getIAndJByClueNum(clueNum, firstZeroIndex);
          int k = getK(ij[0], ij[1]);
          for (Iterator<Integer> i = candidates.get(k).iterator(); i.hasNext();) {
            rowCol[firstZeroIndex] = i.next();
            if (getVisibleBuildings(rowCol) != clueVal) {
                i.remove();
            }
          }
          if (candidates.get(k).size() == 0) {
            return failResult;
          }
          rowCol[firstZeroIndex] = 0;
        }

      }
    }

    int minSize = Integer.MAX_VALUE;

    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        int k = getK(i, j);
        Set<Integer> set = candidates.get(k);
        Set<Integer> rowColValues = getRowColValues(solution, i, j);
        set.removeAll(rowColValues);
        int setSize = set.size();
        if (setSize == 0) {
          return failResult;
        } else if (solution[i][j] == 0 && setSize < minSize) {
          // Add a second largest field;
          if (minSize != Integer.MAX_VALUE) {
            nextMin[0] = result[0];
            nextMin[1] = result[1];
          }
          minSize = setSize;
          result[0] = i;
          result[1] = j;
        }
        candidates.set(k, set);
      }
    }

    // update aggregate columns

      // for (int i = 0; i < dim; i++) {
      //   canCols.get(i).clear();
      //   canRows.get(i).clear();
      // }

      // for (int i = 0; i < dim; i++) {
      //   for (int j = 0; j < dim; j++) {
      //     int k = getK(i,j);
      //     Set<Integer> cans = candidates.get(k);
      //     canCols.get(i).addAll(candidates.get(k));
      //     canRows.get(j).addAll(candidates.get(k));
      //   }
      // }

      // for (int i = 0; i < dim; i++) {
      //   Boolean fail = false;
      //   if (!canCols.get(i).equals(defaultSet)) {
      //      return failResult;
      //   }
      //   if (!canRows.get(i).equals(defaultSet)) {
      //      return failResult;
      //   }
      // }

    return result;
  }

  public static Boolean makeMoves(int[][] solution, int i, int j, int newVal, List<Move> moves, int[] clues) {
    Move move = new Move();

    move.iPos = i;
    move.jPos = j;
    move.oldVal = solution[i][j];
    move.newVal = newVal;

    moves.add(move);
    solution[i][j] = newVal;
    changedSinceLastUpdate = true;

    return true;
  }

  public static void unmakeMoves(int[][] solution, List<Move> moves, int[] clues) {
    while (!moves.isEmpty()) {
      Move move = moves.get(moves.size() - 1);
      moves.remove(moves.size() - 1);
      solution[move.iPos][move.jPos] = move.oldVal;
    }
    changedSinceLastUpdate = true;
  }

  public static Boolean isASolution(int[][] solution,int[] clues) {
    // must have no zeros
    // if (remainingSquares > 0) {
    //   return false;
    // }
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (solution[i][j] == 0) {
          return false;
        }
      }
    }

    // must fulfill all clues
    for (int i = 0; i < clues.length; i++) {
      if (clues[i] != 0) {
        Boolean clueSuccess = checkClue(solution, i, clues[i]);
        if (!clueSuccess) {
          return false;
        }
      }
    }

    return true;
  }

  public static Boolean checkClue(int[][] solution, int clueNum, int clueVal) {
    if (clueVal == 0) {
      return true;
    }
    int[] rowCol = getRowCol(solution, clueNum);
    int visibleBuildings = getVisibleBuildings(rowCol);

    return visibleBuildings == clueVal || hasZero(rowCol);
  }

  public static Boolean hasZero(int[] rowCol) {
    for (int val : rowCol) {
      if (val == 0) {
        return true;
      }
    }

    return false;
  }

  public static Set<Integer> getRowColValues(int[][] solution, int iPos, int jPos) {
    rowColCalls++;
    Set<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (i == iPos && j == jPos) {
          continue;
        } else if (i == iPos) {
          set.add(solution[i][j]);
        } else if (j == jPos) {
          set.add(solution[i][j]);
        }
      }
    }
    return set;
  }

  public static int[] getRowCol(int[][] solution, int clueNum) {
    int[] rowCol = new int[dim];

    for (int i = 0; i < dim; i++) {
      rowCol[i] = getRowColByIndex(solution, clueNum, i);
    }

    return rowCol;
  }

  public static int getRowColByIndex(int[][] solution, int clueNum, int rowIndex) {
    int[] index = getIAndJByClueNum(clueNum, rowIndex);
    return solution[index[0]][index[1]];
  }

  public static int[] getIAndJByClueNum(int clueNum, int rowIndex) {
    int[] result = new int[2];
    int section = clueNum / dim;

    int iResult;
    int jResult;

    if (section == 0) {
      iResult = rowIndex;
      jResult = clueNum;
    } else if (section == 1) {
      iResult = clueNum - dim;
      jResult = dim - 1 -  rowIndex;
    } else if (section == 2) {
      iResult = dim - 1 - rowIndex;
      jResult = dim - 1 - (clueNum % dim);
    } else {
      iResult = dim - 1 - (clueNum % dim);
      jResult = rowIndex;
    }

    // System.out.println("[" + clueNum + ", " + rowIndex + "]" + " -> [" + iResult + ", " + jResult + "]");


    result[0] = iResult;
    result[1] = jResult;

    return result;
  }

  public static int getK(int i, int j) {
    return (i * dim) + j;
  }

  public static int getVisibleBuildings(int[] rowCol) {
    int visibleBuildings = 0;
    int maxSoFar = -1;

    for (int i = 0; i < rowCol.length; i++) {
      if (rowCol[i] > maxSoFar) {
        visibleBuildings++;
        maxSoFar = rowCol[i];
      }
    }

    return visibleBuildings;
  }

/******************************************************************************
 Utility and Debug Methods
 ******************************************************************************/

  public static void printArr(int[][] solution) {
    System.out.println();
    for (int i = 0; i < solution.length; i++) {
      for (int j = 0; j < solution[i].length; j++) {
        System.out.print(solution[i][j]);
      }
      System.out.println();
    }
    // System.out.println();
  }

  public static void debugSets() {
    for (int i = 0; i < dim*dim; i++) {
      System.out.println();
      System.out.print(i + ":" + Arrays.toString(candidates.get(i).toArray()));
    }
  }
  public static void debug(int[][] solution) {
    System.out.println("");
    printArr(solution);
    debugSets();
  }
}

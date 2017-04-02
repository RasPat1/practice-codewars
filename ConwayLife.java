public class ConwayLife {

  public static int[][] getGeneration(int[][] cells, int generations) {
    if (generations == 0) {
      return cropArray(cells);
    }
    // Rules
    // 1) if live and neighbours <2 or >> 3 die
    // 2) if dead and neightbours == 3 live.
    // 3) otherwise stay the same


    // Copy the array
    int[][] expandedCells = new int[cells.length + 2][cells[0].length + 2];
    int[][] expandedResult = new int[cells.length + 2][cells[0].length + 2];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[i].length; j++) {
        expandedCells[i+1][j+1] = cells[i][j];
        expandedResult[i+1][j+1] = cells[i][j];
      }
    }

    System.out.println("expandedCells");
    printArray(expandedCells);

    for (int i = 0; i < expandedCells.length; i++) {
      for (int j = 0; j < expandedCells[i].length; j++) {
        int neighborCount = getNeighborCount(expandedCells, i, j);
        if (expandedCells[i][j] == 1 && neighborCount < 2 || neighborCount > 3) {
          expandedResult[i][j] = 0;
        } else if (expandedCells[i][j] == 0 && neighborCount == 3) {
          expandedResult[i][j] = 1;
        }
      }
    }

    System.out.println("expandedResult");
    printArray(expandedResult);

    // have to do this cropping thing!
    // create an array that is one larger in all directions // 2 larger in dimensions
    // copy over into middle of new grid
    // run the rules
    // crop

    int[][] croppedResult = cropArray(expandedResult);


    return getGeneration(croppedResult, generations - 1);
  }

  public static int[][] cropArray(int[][] arr) {
    // get lowestX with live cell
    // get lowestY with live cell
    // same for highest
    int lowX = arr.length;
    int lowY = arr[0].length;
    int highX = 0;
    int highY = 0;

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        if (arr[i][j] == 1) {
          lowX = i < lowX ? i : lowX;
          lowY = j < lowY ? j : lowY;
          highX = i > highX ? i : highX;
          highY = j > highY ? j : highY;
        }
      }
    }

    int[][] croppedArr = new int[highX - lowX + 1][highY - lowY + 1];
    for (int i = 0; i < croppedArr.length; i++) {
      for (int j = 0; j < croppedArr[0].length; j++) {
        croppedArr[i][j] = arr[i + lowX][j + lowY];
      }
    }

    return croppedArr;
  }

  public static void printArray(int[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        System.out.print(arr[i][j]);
      }
      System.out.println();
    }
  }

  public static int getNeighborCount(int[][] cells, int i, int j) {
    int neighborCount = 0;

    for (int m = -1 + i; m <= 1 + i; m++) {
      for (int n = -1 + j; n <= 1 + j; n++) {
        if (m >= 0 && n >= 0 && m < cells.length && n < cells[m].length && !(m == i && n == j) ) {
          neighborCount += cells[m][n];
        }
      }
    }

    return neighborCount;
  }

}
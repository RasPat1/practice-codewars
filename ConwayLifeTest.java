import java.util.*;
import java.io.Console;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConwayLifeTest{

  @Test
  public void testGlider() {
    int[][][] gliders = {
      {{1,0,0},
       {0,1,1},
       {1,1,0}},
      {{0,1,0},
       {0,0,1},
       {1,1,1}},
      {{1,0,1},
       {0,1,1},
       {0,1,0}}
    };
    System.out.println("Glider");
    System.out.println(printGrid(gliders[0]));
    int[][] res = ConwayLife.getGeneration(gliders[0], 1);

    gridTrue(ConwayLife.getGeneration(gliders[0], 1), gliders[1]);
    gridTrue(ConwayLife.getGeneration(gliders[1], 1), gliders[2]);
    gridTrue(ConwayLife.getGeneration(gliders[0], 2), gliders[2]);
  }

  public void gridTrue(int[][] g1, int g2[][]) {
   assertTrue("Got \n" + printGrid(g1) + "\ninstead of\n" + printGrid(g2), areEqual(g1, g2));
  }

  public static Boolean areEqual(int[][] g1, int[][] g2) {
    return Arrays.deepEquals(g1, g2);
  }

  public static String printGrid(int[][] grid) {
    // return Arrays.deepToString(grid);

    StringBuilder sb = new StringBuilder();

    for (int i = 0; grid != null && i < grid.length; i++) {
      for (int j = 0; grid[i] != null && j < grid[i].length; j++) {
        sb.append(grid[i][j]);
      }
      sb.append("\n");
    }

    return sb.toString();

  }

}
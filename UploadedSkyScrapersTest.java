import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.util.*;

// TODO: Replace examples and use TDD development by writing your own tests

public class UploadedSkyScrapersTest {
  static int[][] clues = {
      {3, 2, 2, 3, 2, 1,
       1, 2, 3, 3, 2, 2,
       5, 1, 2, 2, 4, 3,
       3, 2, 1, 2, 2, 4},
      {0, 0, 0, 2, 2, 0,
       0, 0, 0, 6, 3, 0,
       0, 4, 0, 0, 0, 0,
       4, 4, 0, 3, 0, 0},
      {0, 3, 0, 5, 3, 4,
       0, 0, 0, 0, 0, 1,
       0, 3, 0, 3, 2, 3,
       3, 2, 0, 3, 1, 0},
      {4, 3, 2, 5, 1, 5,
       2, 2, 2, 2, 3, 1,
       1, 3, 2, 3, 3, 3,
       5, 4, 1, 2, 3, 4 }};

  static int[][][] outcomes = {
    {
      {2, 1, 4, 3, 5, 6},
      {1, 6, 3, 2, 4, 5},
      {4, 3, 6, 5, 1, 2},
      {6, 5, 2, 1, 3, 4},
      {5, 4, 1, 6, 2, 3},
      {3, 2, 5, 4, 6, 1}
    },
    {
      {5, 6, 1, 4, 3, 2},
      {4, 1, 3, 2, 6, 5},
      {2, 3, 6, 1, 5, 4},
      {6, 5, 4, 3, 2, 1},
      {1, 2, 5, 6, 4, 3},
      {3, 4, 2, 5, 1, 6}
    },
    {
      {5, 2, 6, 1, 4, 3},
      {6, 4, 3, 2, 5, 1},
      {3, 1, 5, 4, 6, 2},
      {2, 6, 1, 5, 3, 4},
      {4, 3, 2, 6, 1, 5},
      {1, 5, 4, 3, 2, 6}
    },
    {
      {3, 4, 5, 1, 6, 2},
      {4, 5, 6, 2, 1, 3},
      {5, 6, 1, 3, 2, 4},
      {6, 1, 2, 4, 3, 5},
      {2, 3, 4, 6, 5, 1},
      {1, 2, 3, 5, 4, 6}
    }
  };

  // @Test
  // public void speedTest () {
  //   long start = System.currentTimeMillis();
  //   assertEquals(turnOutcome(outcomes[2], 2), SkyScrapersReal.solvePuzzle(turnClues(clues[2], 2)));
  //   long end = System.currentTimeMillis();
  //   System.out.println("Time:" + (end - start));
  // }

  // @Test
  // public void speedTest2 () {
  //   long start = System.currentTimeMillis();
  //   assertEquals(turnOutcome(outcomes[3], 1), SkyScrapersReal.solvePuzzle(turnClues(clues[3], 1)));
  //   long end = System.currentTimeMillis();
  //   System.out.println("Time:" + (end - start));
  // }

  @Test
  public void testSolvePuzzle () {
    for (int i = 0; i < clues.length; i++) {
      long start = System.currentTimeMillis();
        assertEquals(outcomes[i], UploadedSkyScraper.solvePuzzle(clues[i]));
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start));
        if (end - start > 1000) {
          System.out.println("i" + i);
          System.out.println(Arrays.toString(clues[i]));
          for (int j = 0; j < outcomes[i].length; j++) {
            System.out.println(Arrays.toString(outcomes[i][j]));
          }
        }
    }
  }

  // @Test
  // public void correctTest () {
  //   // try {
  //   //   Thread.sleep(20000);
  //   // } catch (Exception e) {};
  //   int[][] turnedOutcome = turnOutcome(outcomes[0], 2);
  //   int[] turnedClues = turnClues(clues[0], 2);
  //   System.out.println(Arrays.toString(turnedClues));
  //   SkyScrapersReal.printArr(turnedOutcome);
  //   long start = System.currentTimeMillis();
  //   assertEquals(turnedOutcome, SkyScrapersReal.solvePuzzle(turnedClues));
  //   long end = System.currentTimeMillis();
  //   System.out.println("Time:" + (end - start));
  // }

  @Test
  public void testSolveRandomPuzzle () {
    for (int i = 0; i < clues.length; i++) {
      for (int rotate = 1; rotate < 4; rotate++) {
        int[][] turnedOutcome = turnOutcome(outcomes[i], rotate);
        int[] turnedClues = turnClues(clues[i], rotate);
        System.out.println(Arrays.toString(turnedClues));
        long start = System.currentTimeMillis();
        assertEquals(turnedOutcome, UploadedSkyScraper.solvePuzzle(turnedClues));
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start));
        if (end - start > 1000) {
          System.out.println("i" + i);
          System.out.println("rotate" + rotate);
          System.out.println(Arrays.toString(turnedClues));
          for (int j = 0; j < turnedOutcome.length; j++) {
            System.out.println(Arrays.toString(turnedOutcome[j]));
          }
        }
      }
    }
  }

  /**
   * rotate the array 90 degrees
   * and just call turnOutcome again with rotateFactor - 1...
  */
  private static int[][] turnOutcome(int[][] outcome, int rotateFactor) {
    int[][] turnedOutcome = new int[outcome.length][outcome[0].length];
    if (rotateFactor <= 0) {
      return outcome;
    } else {
      for (int i = 0; i < outcome.length; i++) {
        for (int j = 0; j < outcome[0].length; j++) {
          turnedOutcome[i][j] = outcome[outcome.length - j - 1][i];
        }
      }
    }
    return turnOutcome(turnedOutcome, rotateFactor - 1);
  }

  private static int[] turnClues(int[] clues, int rotateFactor) {
    int[] turnedClues = new int[clues.length];

    if (rotateFactor <= 0) {
      return clues;
    }
    int sideLength = (clues.length + 1) / 4;
    for (int i = 0; i < clues.length; i++) {
      int newIndex = (i + sideLength) % clues.length;
      turnedClues[newIndex] = clues[i];
    }

    return turnClues(turnedClues, rotateFactor - 1);
  }

}
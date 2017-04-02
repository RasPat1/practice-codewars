import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

import java.util.*;

public class SkyScrapersTest {

    private static int clues[][] = {
    { 2, 2, 1, 3,
      2, 2, 3, 1,
      1, 2, 2, 3,
      3, 2, 1, 3 },
    { 0, 0, 1, 2,
      0, 2, 0, 0,
      0, 3, 0, 0,
      0, 1, 0, 0 },
    { 1, 2, 2, 3,
      3, 2, 1, 3,
      2, 2, 1, 3,
      2, 2, 3, 1}
    };

    private static int outcomes[][][] = {
    { { 1, 3, 4, 2 },
      { 4, 2, 1, 3 },
      { 3, 4, 2, 1 },
      { 2, 1, 3, 4 } },
    { { 2, 1, 4, 3 },
      { 3, 4, 1, 2 },
      { 4, 2, 3, 1 },
      { 1, 3, 2, 4 } },
    { { 4, 3, 1, 2 },
      { 1, 2, 4, 3 },
      { 3, 1, 2, 4 },
      { 2, 4, 3, 1 } },
    };

    private static int whacks[][][]  = {
    { { 1, 3, 4, 2 },
      { 4, 2, 1, 3 },
      { 4, 4, 2, 1 },
      { 0, 0, 0, 0 } },
    { { 2, 1, 4, 3 },
      { 3, 4, 1, 2 },
      { 4, 2, 3, 1 },
      { 1, 3, 2, 4 } }
    };

    @Test
    public void testIsValid () {
        assertEquals(false, SkyScrapers.isValidCol(whacks[0]));
        assertEquals(true, SkyScrapers.isValidCol(whacks[1]));
    }

    @Test
    public void testRowCol () {
        int[][] testSols = {
          {1,3,4,2},
          {1,4,4,0},
          {1,2,4,4},
          {4,4,2,1}
        };
        assertArraysEquals(testSols[0], SkyScrapers.getRowCol(whacks[0], 15));
        assertArraysEquals(testSols[1], SkyScrapers.getRowCol(whacks[0], 0));
        assertArraysEquals(testSols[2], SkyScrapers.getRowCol(whacks[0], 6));
        assertArraysEquals(testSols[3], SkyScrapers.getRowCol(whacks[0], 13));
    }

    public void assertArraysEquals(int[] a, int[] b) {
      // System.out.println(Arrays.toString(a));
      // System.out.println(Arrays.toString(b));

      for (int i = 0; i < a.length; i++) {
        assertEquals(a[i], b[i]);
      }
    }

    @Test
    public void testClueSafe () {
        assertEquals(false, SkyScrapers.isClueSafe(whacks[0], clues[0], false));
        assertEquals(false, SkyScrapers.isClueSafe(whacks[1], clues[0], false));
        // SkyScrapers.printArr(whacks[0]);
        // System.out.println(Arrays.toString(clues[1]));
        assertEquals(true, SkyScrapers.isClueSafe(whacks[0], clues[1], false));
        assertEquals(true, SkyScrapers.isClueSafe(whacks[1], clues[1], false));
    }

    // @Test
    // public void testSolvePuzzle1 () {
    //   long start = System.currentTimeMillis();
    //     assertEquals (SkyScrapers.solvePuzzle (clues[0]), outcomes[0]);
    //     long timeTaken = System.currentTimeMillis() - start;
    //     System.out.println("4x4-1 time taken: " + timeTaken + "ms");
    // }

    // @Test
    // public void testSolvePuzzle2 () {
    //   long start = System.currentTimeMillis();
    //     assertEquals (SkyScrapers.solvePuzzle (clues[1]), outcomes[1]);
    //     long timeTaken = System.currentTimeMillis() - start;
    //     System.out.println("4x4-2 time taken: " + timeTaken + "ms");
    // }

    // @Test
    // public void testSolvePuzzle3 () {
    //   long start = System.currentTimeMillis();
    //     assertEquals (outcomes[2], SkyScrapers.solvePuzzle (clues[2]));
    //     long timeTaken = System.currentTimeMillis() - start;
    //     System.out.println("4x4-3 time taken: " + timeTaken + "ms");
    // }

/******************************************************************************
 5x5
 ******************************************************************************/
    // @Test
    // public void testSolvePuzzle51 () {
    //   int[] clues = {1, 2, 3, 4, 5,
    //                  5, 4, 3, 2, 1,
    //                  1, 2, 2, 2, 2,
    //                  2, 2, 2, 2, 1};
    //   int[][] outcome = {{ 5, 4, 3, 2, 1},
    //                      { 1, 5, 4, 3, 2},
    //                      { 2, 1, 5, 4, 3},
    //                      { 3, 2, 1, 5, 4},
    //                      { 4, 3, 2, 1, 5}};
    //      long start = System.currentTimeMillis();
    //     assertEquals(outcome, SkyScrapers.solvePuzzle(clues));
    //     long timeTaken = System.currentTimeMillis() - start;
    //     System.out.println("5x5 time: " + timeTaken + "ms");
    // }

    /******************************************************************************
     6x6
     ******************************************************************************/
    @Test
    public void testSolvePuzzle61 () {
      int[] clues = {3, 2, 2, 3, 2, 1,
                     1, 2, 3, 3, 2, 2,
                     5, 1, 2, 2, 4, 3,
                     3, 2, 1, 2, 2, 4};
      int[][] outcome = {{ 2, 1, 4, 3, 5, 6},
                         { 1, 6, 3, 2, 4, 5},
                         { 4, 3, 6, 5, 1, 2},
                         { 6, 5, 2, 1, 3, 4},
                         { 5, 4, 1, 6, 2, 3},
                         { 3, 2, 5, 4, 6, 1 }};
       long start = System.currentTimeMillis();
        assertEquals(outcome, SkyScrapers.solvePuzzle(clues));
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("6x6 time: " + timeTaken + "ms");
    }

    @Test
    public void testSolvePuzzle62 () {
        int[] clues = {0, 0, 0, 2, 2, 0,
                       0, 0, 0, 6, 3, 0,
                       0, 4, 0, 0, 0, 0,
                       4, 4, 0, 3, 0, 0};
        int[][] outcome = {{ 5, 6, 1, 4, 3, 2 },
                        { 4, 1, 3, 2, 6, 5 },
                        { 2, 3, 6, 1, 5, 4 },
                        { 6, 5, 4, 3, 2, 1 },
                        { 1, 2, 5, 6, 4, 3 },
                        { 3, 4, 2, 5, 1, 6 }};
       long start = System.currentTimeMillis();
        assertEquals(outcome, SkyScrapers.solvePuzzle(clues));
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("6x6--2 time: " + timeTaken + "ms");
    }

    @Test
    public void testSolvePuzzle63 () {
        int[] clues = {0, 3, 0, 5, 3, 4,
                       0, 0, 0, 0, 0, 1,
                       0, 3, 0, 3, 2, 3,
                       3, 2, 0, 3, 1, 0};
        int[][] outcome = {{ 5, 2, 6, 1, 4, 3 },
                        { 6, 4, 3, 2, 5, 1 },
                        { 3, 1, 5, 4, 6, 2 },
                        { 2, 6, 1, 5, 3, 4 },
                        { 4, 3, 2, 6, 1, 5 },
                        { 1, 5, 4, 3, 2, 6 }};
       long start = System.currentTimeMillis();
        assertEquals(outcome, SkyScrapers.solvePuzzle(clues));
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("6x6--3 time: " + timeTaken + "ms");
    }

    @Test
    public void testSolvePuzzle64 () {

        int[] clues = {0, 3, 0, 3, 2, 3,
                       3, 2, 0, 3, 1, 0,
                       0, 3, 0, 5, 3, 4,
                       0, 0, 0, 0, 0, 1};
        int[][] outcome =  {{6,2,3,4,5,1},
                            {5,1,6,2,3,4},
                            {4,3,5,1,6,2},
                            {2,6,4,5,1,3},
                            {1,5,2,3,4,6},
                            {3,4,1,6,2,5}};
       long start = System.currentTimeMillis();
        assertEquals(outcome, SkyScrapers.solvePuzzle(clues));
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("6x6--4 time: " + timeTaken + "ms");
    }



}
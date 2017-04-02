import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

// TODO: Replace examples and use TDD development by writing your own tests

public class SolutionTest {
  @Test
  public void testSolvePuzzle () {
    int[][] clues = {
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
       3, 2, 0, 3, 1, 0};,
      {0, 3, 0, 3, 2, 3,
       3, 2, 0, 3, 1, 0,
       0, 3, 0, 5, 3, 4,
       0, 0, 0, 0, 0, 1}};

    int[][][] outcomes = {
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
      {6, 2, 3, 4, 5, 1},
      {5, 1, 6, 2, 3, 4},
      {4, 3, 5, 1, 6, 2},
      {2, 6, 4, 5, 1, 3},
      {1, 5, 2, 3, 4, 6},
      {3, 4, 1, 6, 2, 5}
    };

    for (int i = 0; i < clues.length; i++) {
      assertEquals(outcomes[i], SkyScrapers.solvePuzzle(clues[i]));
    }
  }

}
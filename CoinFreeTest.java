import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.List;


public class CoinFreeTest {

  	@Test
    public void testUS() {
			List<int[]> tests = new ArrayList<int[]>();

      tests.add(new int[]{1, 1});
      tests.add(new int[]{10, 1});
      tests.add(new int[]{25, 1});
      tests.add(new int[]{50, 2});
      tests.add(new int[]{73, 7});
      tests.add(new int[]{125, 5});
       tests.add(new int[]{100, 4});

			int[] denom = {1, 5, 10, 25};

      for (int[] test : tests) {
        int solution = CoinFree.solve(test[0], denom);
				assertEquals("Testing for " + test[0] + " cents.", test[1], solution);
      }
    }

    @Test
    public void testRichAmericans() {
      List<int[]> tests = new ArrayList<int[]>();

      tests.add(new int[]{12312, 495});
      tests.add(new int[]{34576, 1384});
      tests.add(new int[]{129671, 5189});
      tests.add(new int[]{12372, 498});
      tests.add(new int[]{120201, 4809});

      int[] denom = {1, 5, 10, 25};

      for (int[] test : tests) {
        int solution = CoinFree.solve(test[0], denom);
        assertEquals("Testing for " + test[0] + " cents.", test[1], solution);
      }
		}

    @Test
    public void testStrangeCurrency() {
      List<int[]> tests = new ArrayList<int[]>();

      tests.add(new int[]{1, 1});
      tests.add(new int[]{10, 2});
      tests.add(new int[]{25, 5});
      tests.add(new int[]{50, 6});
      tests.add(new int[]{73, 7});
      tests.add(new int[]{125, 7});
      tests.add(new int[]{100, 8});

      int[] denom = {1, 3, 7, 27};

      for (int[] test : tests) {
        int solution = CoinFree.solve(test[0], denom);
        assertEquals("Testing for " + test[0] + " cents.", test[1], solution);
      }
    }

    @Test
    public void testRichStrangers() {
      List<int[]> tests = new ArrayList<int[]>();

      tests.add(new int[]{1232, 48});
      tests.add(new int[]{346, 16});
      tests.add(new int[]{1291, 51});
      tests.add(new int[]{1232, 48});
      tests.add(new int[]{1201, 47});

      int[] denom = {1, 3, 7, 27};

      for (int[] test : tests) {
        int solution = CoinFree.solve(test[0], denom);
        assertEquals("Testing for " + test[0] + " cents.", test[1], solution);
      }

    }

}

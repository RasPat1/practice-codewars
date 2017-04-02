import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class PrimesTest {

  @Test
  public void test_0_10() {
    test(0, 10, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
  }

  @Test
  public void test_10_10() {
    test(10, 10, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71);
  }

  @Test
  public void test_100_10() {
    test(100, 10, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601);
  }

  @Test
  public void test_1000_10() {
    test(1000, 10, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017);
  }

  @Test
  public void test_1000000_10() {
    int[] primeArray = {15485867, 15485917, 15485927, 15485933, 15485941, 15485959, 15485989, 15485993, 15486013, 15486041};
    test(1000000, 10, primeArray);
    // 8.2 seconds
  }

  private void test(int skip, int limit, int... expect) {
    int[] found;
    try {
      long start = System.currentTimeMillis();
      found = Primes.stream().skip(skip).limit(limit).toArray();
      long end = System.currentTimeMillis();
      System.out.println("Test:[" + skip + ", " + limit + "] took " + (end - start) + "ms");
    }
    catch (Throwable e) {
      // see https://github.com/Codewars/codewars.com/issues/21
      throw new AssertionError("exception during test: "+e, e);
    }
    assertArrayEquals(expect, found);
  }

}
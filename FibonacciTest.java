import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FibonacciTest {

  @Test
  public void testFib0() {
    testFib(0, 0);
  }

  @Test
  public void testFib1() {
    testFib(1, 1);
  }

  @Test
  public void testFib2() {
    testFib(1, 2);
  }

  @Test
  public void testFib3() {
    testFib(2, 3);
  }

  @Test
  public void testFib4() {
    testFib(3, 4);
  }

  @Test
  public void testFib5() {
    testFib(5, 5);
  }

  @Test
  public void testFib6() {
    testFib(8, 6);
  }

  @Test
  public void testFib7() {
    testFib(13, 7);
  }

  @Test
  public void testFib8() {
    testFib(21, 8);
  }

  @Test
  public void testFib9() {
    testFib(34, 9);
  }

/******************************************************************************
 Negative
 ******************************************************************************/

  // @Test
  // public void testFibNeg1() {
  //   testFib(1, -1);
  // }
  // @Test
  // public void testFibNeg2() {
  //   testFib(-1, -2);
  // }
  // @Test
  // public void testFibNeg3() {
  //   testFib(2, -3);
  // }
  // @Test
  // public void testFibNeg4() {
  //   testFib(-3, -4);
  // }
  // @Test
  // public void testFibNeg5() {
  //   testFib(5, -5);
  // }
  // @Test
  // public void testFibNeg6() {
  //   testFib(-8, -6);
  // }


  /******************************************************************************
   Really Big
   ******************************************************************************/

  // @Test
  // public void testFibLarge() {
  //   testFib("359579325206583560961765665172189099052367214309267232255589801", 301);
  // }


  // @Test
  // public void testFibSoBig() {
  //   testFib("359579325206583560961765665172189099052367214309267232255589801", 1000000);
  // }

  private static void testFib(String expected, long input) {
    BigInteger found;
    try {
      found = Fibonacci.fib(BigInteger.valueOf(input));
    }
    catch (Throwable e) {
      // see https://github.com/Codewars/codewars.com/issues/21
      throw new AssertionError("exception during test: "+e, e);
    }
    assertEquals(new BigInteger(expected), found);
  }

  private static void testFib(long expected, long input) {
    BigInteger found;
    try {
      found = Fibonacci.fib(BigInteger.valueOf(input));
    }
    catch (Throwable e) {
      // see https://github.com/Codewars/codewars.com/issues/21
      throw new AssertionError("exception during test: "+e, e);
    }
    assertEquals(BigInteger.valueOf(expected), found);
  }

}
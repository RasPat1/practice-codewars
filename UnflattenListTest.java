import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class UnflattenListTests {
  @Test
  public void basicTests() {

    Object[] input = new Object[] { 4, 5, 1, 7, 1 };
    Object[] expected = new Object[] { new Object[] { 4, new Object[] { 5, 1, 7 } }, 1 };
    assertEquals(expected, Kata.unflatten(input, 2));

    input = new Object[] { 12, 1, 5, 3, 1, 55, 2, 3, 7, 8, 1 };
    expected = new Object[] { 12,1, new Object[] { 5, new Object[] { 3, 1, 55 }, 2}, new Object[] { 3, 7, 8 }, 1};
    assertEquals(expected, Kata.unflatten(input, 3));
  }
}
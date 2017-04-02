import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class KataTests {
    @Test
    public void basicTests() {
      assertEquals(-1, Kata.nextSmaller(1111111222222L));
      assertEquals(12, Kata.nextSmaller(21));
      assertEquals(-1, Kata.nextSmaller(1));
      assertEquals(-1, Kata.nextSmaller(10));
      assertEquals(790, Kata.nextSmaller(907));
      assertEquals(513, Kata.nextSmaller(531));
      assertEquals(10207, Kata.nextSmaller(10270));
      assertEquals(-1, Kata.nextSmaller(1027));
      assertEquals(414, Kata.nextSmaller(441));
      assertEquals(123456789, Kata.nextSmaller(123456798));
      assertEquals(8976543211L, Kata.nextSmaller(9112345678L));
      assertEquals(1798, Kata.nextSmaller(1879));
    }
}
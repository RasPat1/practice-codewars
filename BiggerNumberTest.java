import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class BiggerNumberTest {
    @Test
    public void basicTests() {
         // assertEquals(21, BiggerNumber.nextBiggerNumber(12));
         assertEquals(-1, BiggerNumber.nextBiggerNumber(21));
         assertEquals(-1, BiggerNumber.nextBiggerNumber(531));
         assertEquals(-1, BiggerNumber.nextBiggerNumber(7210));
         assertEquals(21, BiggerNumber.nextBiggerNumber(12));
         assertEquals(531, BiggerNumber.nextBiggerNumber(513));
         assertEquals(2071, BiggerNumber.nextBiggerNumber(2017));
         assertEquals(441, BiggerNumber.nextBiggerNumber(414));
         assertEquals(414, BiggerNumber.nextBiggerNumber(144));
         assertEquals(279197752, BiggerNumber.nextBiggerNumber(279197725));
         assertEquals(42977160, BiggerNumber.nextBiggerNumber(42977106));
         assertEquals(134697784, BiggerNumber.nextBiggerNumber(134697748));
         assertEquals(74079109, BiggerNumber.nextBiggerNumber(74079091));
         assertEquals(1166896403, BiggerNumber.nextBiggerNumber(1166896340));
         assertEquals(1166896403, BiggerNumber.nextBiggerNumber(1166896340));
         assertEquals(1166896403, BiggerNumber.nextBiggerNumber(1166896340));
         assertEquals(1166896403, BiggerNumber.nextBiggerNumber(1166896340));
         assertEquals(1166896403, BiggerNumber.nextBiggerNumber(1166896340));
    }
}
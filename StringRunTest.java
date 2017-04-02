import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class StringRunTest {
    @Test
    public void test_BasicTests() {
        assertEquals("-6,-3-1,3-5,7-11,14,15,17-20", StringRun.rangeExtraction(new int[] {-6,-3,-2,-1,0,1,3,4,5,7,8,9,10,11,14,15,17,18,19,20}));
        assertEquals("-3--1,2,10,15,16,18-20", StringRun.rangeExtraction(new int[] {-3,-2,-1,2,10,15,16,18,19,20}));
    }

    @Test
    public void test_RandomTests() {
        assertEquals("-21,-19,-16,-15,-13,-10,-7,-4,-3,-1,2,5,7,8,11,14,17-19,21-24,26,28,31", StringRun.rangeExtraction(new int[] {-21, -19, -16, -15, -13, -10, -7, -4, -3, -1, 2, 5, 7, 8, 11, 14, 17, 18, 19, 21, 22, 23, 24, 26, 28, 31}));
    }
  }
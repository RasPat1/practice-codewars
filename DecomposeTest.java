import static org.junit.Assert.*;
import org.junit.Test;

public class DecomposeTest {

    @Test
    public void test1() {
        assertEquals("",  Decompose.decompose(2));
        assertEquals("",  Decompose.decompose(3));
        assertEquals("3 4",  Decompose.decompose(5));
        assertEquals("6 8",  Decompose.decompose(10));
        assertEquals("1 2 4 10",  Decompose.decompose(11));
        assertEquals("1 3 5 8 49",  Decompose.decompose(50));
        assertEquals("2 5 8 34 624",  Decompose.decompose(625));
    }
}

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class SmallfuckTest {
    @Test
    public void testExamples() {
        // Flips the leftmost cell of the tape
        assertEquals(Smallfuck.interpreter("*", "00101100"), "10101100");

        // Flips the second and third cell of the tape
        assertEquals(Smallfuck.interpreter(">*>*", "00101100"), "01001100");

        // Flips all the bits in the tape
        assertEquals(Smallfuck.interpreter("*>*>*>*>*>*>*>*", "00101100"), "11010011");

        // Flips all the bits that are initialized to 0
        assertEquals(Smallfuck.interpreter("*>*>>*>>>*>*", "00101100"), "11111111");

        // Goes somewhere to the right of the tape and then flips all bits that are initialized to 1, progressing leftwards through the tape
        assertEquals(Smallfuck.interpreter(">>>>>*<*<<*", "00101100"), "00000000");

        // loop
        assertEquals(Smallfuck.interpreter("*[>*]", "00000000"), "11111111");

        // Nested loop
        assertEquals(Smallfuck.interpreter("[[]*>*>*>]", "000"), "000");
    }
}
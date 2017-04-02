import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class LCSTest {
    @Test
    public void exampleTests() {
        assertEquals("", LCS.lcs("a", "b"));
        assertEquals("abc", LCS.lcs("abcdef", "abc"));
        assertEquals("nottest", LCS.lcs("anothertest", "notatest"));
    }
}
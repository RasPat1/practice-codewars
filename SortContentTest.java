import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class SortContentTest {
    @Test
    public void exampleTests() {
        assertEquals(SortContent.sortTheInnerContent("sort the inner content in descending order"), "srot the inner ctonnet in dsnnieedcg oredr");
        assertEquals(SortContent.sortTheInnerContent("wait for me"), "wiat for me");
        assertEquals(SortContent.sortTheInnerContent("this kata is easy"), "tihs ktaa is esay");
    }
}
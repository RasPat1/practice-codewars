import static org.junit.Assert.*;
import org.junit.Test;

public class IntPartTest {

  @Test
  public void Numbers_Small() {
    // assertEquals("Range: 1 Average: 1.50 Median: 1.50", IntPart.part(2));
    // assertEquals("Range: 2 Average: 2.00 Median: 2.00", IntPart.part(3));
    // assertEquals("Range: 3 Average: 2.50 Median: 2.50", IntPart.part(4));
    // assertEquals("Range: 5 Average: 3.50 Median: 3.50", IntPart.part(5));
    // assertEquals("Range: 17 Average: 8.29 Median: 7.50", IntPart.part(8));
    assertEquals("Range: 80 Average: 27.08 Median: 22.50", IntPart.part(12));
    // assertEquals("Range: 1 Average: 1.50 Median: 1.50", IntPart.part(10));
    // assertEquals("Range: 2 Average: 8.28 Median: 7.50", IntPart.part(30));
    // assertEquals("Range: 3 Average: 2.50 Median: 2.50", IntPart.part(40));
    assertEquals("Range: 5 Average: 3.50 Median: 3.50", IntPart.part(50));
  }
}

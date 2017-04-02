import java.util.*;

import org.junit.Test;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

public class ScratchTest {

    @Test
    public void test() {
      List<Integer[][]> emptyList = new ArrayList<Integer[][]>();
      List<Integer[][]> simpleList = new ArrayList<Integer[][]>();
      simpleList.add(new Integer[5][1]);
      simpleList.add(new Integer[1][5]);
      simpleList.add(new Integer[5][1]);

      assertEquals(0, Scratch.parenDP(emptyList));
      assertEquals(8, Scratch.parenDP(simpleList));
    }
}
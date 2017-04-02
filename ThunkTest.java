package stream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runners.JUnit4;

public class ThunkTest {

  @Test
  public void nowTest() {
    // create from a value
    Thunk<Integer> lazyA = Thunk.now(42);
    int a = lazyA.get();

    assertEquals(42, a);
  }

  @Test
  public void createFromSupplierAndChain() {
    // create from a supplier
    Thunk<Integer> lazyB = Thunk.delay(() -> 21);
    // create a new lazy computation with a function (the type of the thunk can change!)
    Thunk<Integer> lazyC = lazyB.chain(x -> x * 2);

    // evaluates lazyB and then lazyC
    int c1 = lazyC.get();
    assertEquals(42, c1);

    // everything already evaluated here, since lazyC is shared!
    int c2 = lazyC.get();
    assertEquals(42, c2);
  }
}
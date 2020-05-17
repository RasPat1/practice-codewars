import java.util.stream.IntStream;

public class Utility {
  static Integer f1 = 0;
  static Integer f2 = 1;
  static Boolean started = false;

  public static IntStream generateFibonacciSequence() {
      return IntStream.generate( () -> next() );
  }
  public static int next() {
    if (!started) {
      started = true;
      return 1;
    }
    Integer f3 = f1 + f2;
    f1 = f2;
    f2 = f3;
    return f3;
  }

}
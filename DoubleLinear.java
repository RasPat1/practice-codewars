import java.util.*;
/**
 * u(0) = 1
 * y = 2x+1
 * z = 3x+1
 */
class DoubleLinear {

    public static int dblLinear (int n) {
      TreeSet<Integer> list = new TreeSet<Integer>();
      list.add(1);

      for (int i = 0; i < n; i++) {
        int num = list.first();
        list.add(num * 2 + 1);
        list.add(num * 3 + 1);
        list.remove(num);
      }

      return list.first();
    }
}
import java.util.*;

public class Scratch {

    /** You are given a list of matricies
     * compute the optimal parenthesization
     * Use dynamic Programming
     * returns locations of parentheses... >=0 for open,  <0 for close
     **/
    public static int parenDP(List<Integer[][]> matricies) {
      // 1) Define subproblem
      //   solve using substrings
      //   i, j for all i, j
      // 2)

      if (matricies.size() > 2) {
        // take min
        int minCost = Integer.MAX_VALUE;
        int minCostIndex = -1;
        for (int i = 1; i < matricies.size(); i++) {
          int firstMult = parenDP(matricies.subList(0, i));
          int secondMult = parenDP(matricies.subList(i, matricies.size()));
          int mCost = 1; // getMultCost(0,0); // what goes in here?
          int val = firstMult + secondMult + mCost;
          if (val < minCost) {
            minCost = val;
            minCostIndex = i;
          }
        }

        return minCost;
      } else if (matricies.size() == 2) {
        return getMultCost(matricies.get(0), matricies.get(1));
      } else {
        return 0;
      }
    }

    // n by 1 times 1 by n = n by n -> cost is nsq
    // 1 by n times n by 1 = 1 by 1 -> cost is n
    // n by m times m by p = n by p -> cost is
    public static int getMultCost(Integer[][] m1, Integer[][] m2) {
      return m1.length * m1[0].length * m2[0].length;
    }
}
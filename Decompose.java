import java.util.*;
import java.util.Iterator;

public class Decompose {

  static Boolean debug = false;

  public static String decompose(long n) {
    if (n == 11) {
      debug = false;
    }
    Map<Long, Set<Long>> memo = new HashMap<>();
    Set<Long> decomposeSet = decompose(n*n, true, memo);
    System.out.println(Arrays.toString(decomposeSet.toArray()));
    String solution = "";
    for (Long i: decomposeSet) {
      solution += i + " ";
    }

    return solution.equals("") ? "" : solution.substring(0, solution.length() - 1);
  }

  public static Set<Long> decompose(long nSq, Boolean start, Map<Long, Set<Long>> memo) {
    if (memo.containsKey(nSq)) {
      if (debug) {
        System.out.println("Used Memo:" + nSq);
      }
      return memo.get(nSq);
    }

    Set<Long> result = new TreeSet<>();

    long n = (long)Math.sqrt(nSq);

    // not sure on the base case
    if (nSq == 1) {
      result.add(1L);
      memo.put(nSq, result);
      if (debug) {
        System.out.println("added Base Case");
      }
      return result;
    }

    // Classic DP?
    // Find the largest decomp for a smaller number and then add the larger one?
    int smallestSet = Integer.MAX_VALUE;

    for (long i = n; i > 0; i--) {
      Set<Long> subset = new TreeSet<>();
      Long iSq = i*i;

      if (!start && iSq == nSq) {
        result.add(i);
        break;
      } else if (iSq < nSq) {
        subset = decompose(nSq - iSq, false, memo);
        if (subset == null || subset.size() == 0 || subset.contains(i)) {
          // not a valid option must be strictly increasing
          continue;
        } else {
          subset = new TreeSet<Long>(subset);
          subset.add(i);
          // Just take the first one that gives you a real answer
          memo.put(nSq, subset);
          return subset;
        }
      }
    }

    if (result.size() > 0) {
      memo.put(nSq, result);
    }
    return result;
  }

  // public static Boolean isBetterSet(Set<Long> set1, Set<Long> set2) {
  //   if (debug) {
  //     System.out.println(Arrays.toString(set1.toArray()));
  //     System.out.println(Arrays.toString(set2.toArray()));
  //     try{Thread.sleep(1000);} catch(Exception e) {};
  //   }
  //   Iterator s1 = ((TreeSet<Long>)set1).descendingIterator();
  //   Iterator s2 = ((TreeSet<Long>)set2).descendingIterator();
  //   while (s1.hasNext() && s2.hasNext()) {
  //     Long s1Val = (Long)s1.next();
  //     Long s2Val = (Long)s2.next();

  //     if (s1Val > s2Val) {
  //       if (debug) {
  //         System.out.println(s1Val);
  //         System.out.println(s2Val);
  //         try{Thread.sleep(1000);} catch(Exception e) {};
  //       }
  //       return true;
  //     } else if (s1Val < s2Val) {
  //       if (debug) {
  //         System.out.println(s1Val);
  //         System.out.println(s2Val);
  //         try{Thread.sleep(1000);} catch(Exception e) {};
  //       }
  //       return false;
  //     }
  //   }
  //   return s1.hasNext(); // if one is shorter than the other.. witht he same values... that'll never happen
  // }
}
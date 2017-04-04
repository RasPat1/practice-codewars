import java.util.*;
import java.text.DecimalFormat;
public class IntPart {

  static HashMap<Long, Set<List<Long>>> memo = new HashMap<Long, Set<List<Long>>>();

  public static String part(long n) {

    Set<List<Long>> parts = getParts(n);
    // Use just a treeset not a treeset and a hashset
    Set<Long> products = new TreeSet<Long>();
    // TreeSet<Long> medianList = new TreeSet<Long>();

    Long min = Long.MAX_VALUE;
    Long max = Long.MIN_VALUE;
    Long sum = 0L;

    for (List<Long> part: parts) {
      Long product = getProduct(part);
      Boolean added = products.add(product);

      if (added) { // if not duplicate
        sum += product;
        // medianList.add(product);
      }
      if (product < min) {
        min = product;
      }
      if (product > max) {
        max = product;
      }
    }

    Boolean averageMedian = products.size() % 2 == 0;
    Long medianSum = 0L;
    int medianIndex = products.size() / 2;
    int currentIndex = 0;

    for (Long product : products) {
      if (currentIndex == medianIndex) {
        medianSum += product;
      } else if (averageMedian && currentIndex +1 == medianIndex) {
        medianSum += product;
      }
      currentIndex++;
    }
    Long range = max - min;
    Double average = sum.doubleValue() / products.size();
    Double median = averageMedian ? medianSum.doubleValue() / 2 : medianSum.doubleValue();

    DecimalFormat df = new DecimalFormat("0.00");

    String result = "Range: " + range + " Average: " + df.format(average) + " Median: " + df.format(median);


    // get all the partitions
    // multiply all the partitions
    // get range, mean and media of multiplied values
    // convert to strings of int and 2 decimal digit float

    return result;
  }

  public static Long getProduct(List<Long> part) {
    Long product = 1L;

    for (Long p: part) {
      product *= p;
    }

    return product;
  }

  // public static Set<List<Long>> getParts(long n) {
  //   HashMap<Long, Set<List<Long>>> memo = new HashMap<Long, Set<List<Long>>>();
  //   return getParts(n, memo);
  // }

  public static Set<List<Long>> getParts(long n) {
    Set<List<Long>> result = new HashSet<>();

    if (memo.containsKey(n)) {
      return memo.get(n);
    }
    if (n == 1) {
      List<Long> list = new ArrayList<>();
      list.add(1L);
      result.add(list);
      memo.put(n, result);
      return result;
    }

    Set<List<Long>> previousParts = getParts(n - 1);
    for (List<Long> part : previousParts) {
      // go through each part and modify each of its values...
      List<Long> pLonger = new ArrayList<Long>(part);
      for (int i = 0; i < part.size(); i++) {
        List<Long> pOneMore = new ArrayList<Long>(part);
        pOneMore.set(i, pOneMore.get(i) + 1L);
        Collections.sort(pOneMore);
        result.add(pOneMore);
      }

      pLonger.add(1L);
      Collections.sort(pLonger);
      result.add(pLonger);
    }

    memo.put(n, result);
    return result;
  }
}
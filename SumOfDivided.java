import java.util.*;

public class SumOfDivided {
  public static String sumOfDivided(int[] l) {
    // get list (all ints)
    // sort list
    // get list max
    // get primes between 2 and listMax
    // go through each prime
      // go through each number in l
      // if its divisble by this prime add it to a sum
    // add (prime sum) to the string
    // return the string
    String result = "";
    Arrays.sort(l);

    int listMax = l[l.length - 1];
    int listMin = l[0];
    int absMax = Math.max(Math.abs(listMin), Math.abs(listMax));

    List<Integer> primes = new ArrayList<>();
    fillPrimes(primes, absMax);

    for (Integer prime : primes) {
      int sum = 0;
      for (int el : l) {
        if (el % prime == 0) {
          sum += el;
        }
      }
      if (sum > 0) {
        result += "(" + prime + " " + sum + ")";
      }
    }

    return result;
  }

  public static void fillPrimes(List<Integer> primes, int max) {

    boolean[] sieve = new boolean[max + 1];

    int div = 2;
    while (div < max) {
      int mult = 2;
      while (mult * div <= max) {
        sieve[mult * div] = true;
        mult++;
      }
      div++;
    }

    for (int i = 2; i < sieve.length; i++) {
      if (!sieve[i]) {
        primes.add(i);
      }
    }
  }

}

import java.util.stream.IntStream;
import java.util.*;

public class Primes {
  static List<Integer> knownPrimes = new ArrayList<Integer>();

  public static IntStream stream() {
    Map<String, Integer> metaData = new HashMap<>();
    metaData.put("lastKnownPrimeIndex", 0);

    return IntStream.generate( () -> next(metaData) );
  }

  public static int next(Map<String, Integer> metaData) {
    int desiredPrimeIndex = metaData.get("lastKnownPrimeIndex") + 1;

    while (knownPrimes.size() < desiredPrimeIndex) {
      int lastKnownPrime = knownPrimes.size() > 0 ? knownPrimes.get(knownPrimes.size() - 1) : 1;
      int nextPrime = getNextPrime(lastKnownPrime);
      knownPrimes.add(nextPrime);
    }

    int desiredPrimeNum = knownPrimes.get(desiredPrimeIndex - 1);
    metaData.put("lastKnownPrimeIndex", desiredPrimeIndex);
    return desiredPrimeNum;
  }

  public static int getNextPrime(int start) {
    int nextPrime = start + 1;

    while (!isPrime(nextPrime)) {
      nextPrime++;
    }

    return nextPrime;
  }

  public static Boolean isPrime(int potentialPrime) {
    Boolean isPrime = true;
    int upperBound = (int) Math.sqrt(potentialPrime);

    for (Integer div : knownPrimes) {
      if (div > upperBound) {
        break;
      }
      if (potentialPrime % div == 0) {
        return false;
      }
    }

    return isPrime;
  }
}

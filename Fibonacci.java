import java.math.BigInteger;

public class Fibonacci {

  // must handle negative numbers and very very large numbers
  public static BigInteger fib(BigInteger n) {
    Boolean isNeg = n.compareTo(BigInteger.ZERO) < 0 ? true : false;

    if (isNeg) {
      n = n.negate();
    }

    BigInteger result = fibCore(n);

    return isEven(n) && isNeg ? result.negate() : result;
  }


  public static BigInteger fibCore(BigInteger count) {
    BigInteger zero = BigInteger.ZERO;
    BigInteger one = BigInteger.ONE;
    BigInteger two = one.add(one);

    if (count.equals(zero)) {
      return zero;
    } else if (count.equals(one)) {
      return one;
    } else if (count.equals(two)) {
      return one;
    } else if (isEven(count)) {
      // F(2k) = F(k) * [2*F(k+1) - F(k)];
      BigInteger halfCount = count.divide(two); // k
      BigInteger fK = fibCore(halfCount); // F(k)
      BigInteger fKPlusOne = fibCore(halfCount.add(one)); // F(k+1)

      return fK.multiply(fKPlusOne.multiply(two).subtract(fK));
    } else {
      // F(2k+1) = F(k+1)^2 + F(k)^2
      BigInteger k = count.subtract(one).divide(two); // k
      BigInteger fKPlusOne = fibCore(k.add(one)); // F(k+1)
      BigInteger fK = fibCore(k); // F(k)

      return fKPlusOne.multiply(fKPlusOne).add(fK.multiply(fK));
    }
  }

  public static Boolean isEven(BigInteger n) {
    return !n.testBit(0);
  }
}
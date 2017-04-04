public class ASum {

    public static long findNb(long m) {
      // m = 1^3 + 2^3 + ... + n^3
      // aka (n(n +1)/2)^2
      // use quadratic function?
      // sqrt(m) * 2 = n(n+1)
      // sqrt(m) * 2 = n^sq + n
      // 0 = nsq + n - 2*sqrt(m)
      // -b +- sqrt(b^sq - 4ac) / 2a

      // a = 1
      // b = 1
      // c = 2 * sqrt(m)
      long a = 1;
      long b = 1;
      long c = -2 * (long)Math.sqrt(m);

      // if we're not an integer get teh fuck outta here
      if (c * c != 4 * m) {
        return -1;
      }

      // straight quadratic formula
      long sqrt = (long) Math.sqrt(b*b - 4*a*c);
      long low = (-1*b - sqrt) / (2*a);
      long high = (-1*b + sqrt) / (2*a);

      return low >= 0 ? low : high;
    }


}
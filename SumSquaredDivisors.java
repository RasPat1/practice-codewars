import java.util.*;

public class SumSquaredDivisors {
	
	public static String listSquared(long m, long n) {
  	List<String> results = new ArrayList<String>();
    
  	for (long i = m; i <= n; i++) {
    	List<Long> divisors = getDivisors(i);
      long sum = 0;
      for (long div : divisors) {
        sum += div * div;
      }

      long sqrt = (long) Math.sqrt(sum);

      if (sqrt * sqrt == sum) {
        results.add("[" + i + ", " + sum + "]");
      }
    }
    
    return Arrays.toString(results.toArray());
	}
  
  public static List<Long> getDivisors(long n) {
  	List<Long> result = new ArrayList<Long>();

    for (long i = 1; i <= n; i++) {
      if (n % i == 0) {
      	result.add(i);
      }
    }

		return result;
  }
}

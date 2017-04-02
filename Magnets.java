class Magnets {

    public static double doubles(int maxk, int maxn) {
      double force = 0.0;

      for (int k = 1; k <= maxk; k++) {
        for (int n = 1; n <= maxn; n++) {
          double denom = k * Math.pow((n + 1), 2*k);
          force += 1 / denom;
        }
      }

      return force;
    }
}
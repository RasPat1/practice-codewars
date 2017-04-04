import java.lang.reflect.*;
import java.util.Map;
import java.util.stream.*;

public class BagelSolver {
  public static Bagel getBagel() {
    // All we want to do here is pass a subtype of bagel
    // lets call it bagel wrapper
    // that subtype should implement an interface
    // that interface shoudl be proxied
    // the proxy should use an invocation handler
    // that invocation handler shoudl just return 4 always

    BagelWrapper bagelWrapper = new BagelWrapper();

    // BagelImpl proxy = (BagelImpl) Proxy.newProxyInstance(BagelImpl.class.getClassLoader(), new Class[] {BagelImpl.class}, new MyInvocationHandler(bagelWrapper));
    // System.out.println(proxy.bagelWrapper.getValue());

    System.out.println

    return bagelWrapper;
  }

  // public static <T> T passThroughProxy(Class<? extends T> iface, T target) {
  //     return simpleProxy(iface, new MyInvocationHandler(target));
  // }

  // public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>...otherIfaces) {
  //   Class<?>[] allInterfaces = Stream.concat(
  //       Stream.of(iface),
  //       Stream.of(otherIfaces))
  //       .distinct()
  //       .toArray(Class<?>[]::new);

  //   return (T) Proxy.newProxyInstance(
  //       iface.getClassLoader(),
  //       allInterfaces,
  //       handler);
  // }
}
// interface BagelImpl {
//   BagelWrapper bagelWrapper = new BagelWrapper();
//   public int getValue();
// }
class BagelWrapper extends Bagel implements InvocationHandler {
  // private final Object target;

  // public MyInvocationHandler(Object target) {
  //     this.target = target;
  // }
  @Override
  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
      return (Object) 4; //;m.invoke(target, args);
  }
}

// class MyInvocationHandler implements InvocationHandler {



// }



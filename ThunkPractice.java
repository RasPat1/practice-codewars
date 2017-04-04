package stream;

import java.util.*;
import java.util.function.Function;

public class Thunk<T> {
  private T _val;

  public Thunk(T val) {
    this._val = x;
  }

/******************************************************************************
 Static Functions
 ******************************************************************************/
  public static <U> Thunk<U> now(U x) {
    return new Thunk(x);
  }

  public static <U> Thunk<U> delay(Function<U, U> x) {
    return new Thunk(x);
  }

/******************************************************************************
 Instance Functions
 ******************************************************************************/
  public T get() {
    return this._val;
  }

  public Thunk<T> chain() {
    return null;
  }
}

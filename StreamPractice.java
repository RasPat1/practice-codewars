package stream;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import java.lang.RuntimeException;

import stream.Thunk;

public class Stream<T> {

    // head of the stream
    private T _head;

    // lazy tail of the stream
    private Thunk<Stream<T>> _tail;

    public Stream(T head, Thunk<Stream<T>> tail) {
        this._head = head;
        this._tail = tail;
    }

    // Returns the head of the stream.
    public T headS() {
        return _head;
    }

    // Returns the unevaluated tail of the stream.
    public Thunk<Stream<T>> tailS() {
        return _tail;
    }

    // Returns the evaluated tail of the stream.
    public Stream<T> next() {
        return _tail.get();
    }

    // .------------------------------.
    // | Static constructor functions |
    // '------------------------------'

    // Construct a stream by repeating a value.
    public static <U> Stream<U> repeatS(U x) {
        return new Stream(x, Thunk.delay(() -> Stream.repeatS(x)));
    }

    // Construct a stream by repeatedly applying a function.
    public static <U> Stream<U> iterateS(Function<U, U> f, U x) {
        return new Stream(x, Thunk.delay(() -> Stream.iterateS(f, f.apply(x))));
    }

        // Construct a stream by repeating a list forever. (Sadly no pure single linked lists :( )
    public static <U> Stream<U> cycleS(List<U> l) {
        return Stream.cycleImplS(l, 0);
    }

    public static <U> Stream<U> cycleImplS(List<U> l, Integer index) {
        return new Stream(l.get(index % l.size()), Thunk.delay(()->Stream.cycleImplS(l, index+1)));
    }

    // Construct a stream by counting numbers starting from a given one.
    public static Stream<Integer> fromS(int x) {
        return new Stream(x, Thunk.delay(() -> Stream.fromS(x+1)));
    }

    // Same as @{fromS} but count with a given step width.
    public static Stream<Integer> fromThenS(int x, int d) {
        return new Stream(x, Thunk.delay(() -> Stream.fromThenS(x+d, d)));
    }

    // .------------------------------------------.
    // | Stream reduction and modification (pure) |
    // '------------------------------------------'

    // Fold a stream from the left.
    public <R> R foldrS(BiFunction<T, Thunk<R>, R> f) {
      return f.apply(headS(), Thunk.delay(() -> foldrS(f)));
    }

    // Filter stream with a predicate. (Returns a lazy result.)
    public Thunk<Stream<T>> filterS(Predicate<T> p) {
      return Thunk.delay(() -> p.test(headS()) ?
        new Stream(headS(), next().filterS(p)) :
        new Stream(next().headS(), next().filterS(p))
      );
    }

    // Take a given amount of elements from the stream.
    public LinkedList<T> takeS(int n) {
        LinkedList<T> list = new LinkedList<T>();
                Stream<T> s = this;
        for (int i = 0; i < n; i++) {
          list.add(s.headS());
          s = s.next();
        }

        return list;
    }

    // Drop a given amount of elements from the stream.
    public Stream<T> dropS(int n) {
        Stream<T> s = this;
        for (int i = 0; i < n; i++) {
            s = s.next();
        }
        return s;
    }

    // Combine 2 streams with a function.
    public <U, R> Stream<R> zipWithS(BiFunction<T, U, R> f, Stream<U> other) {
        return new Stream(f.apply(headS(), other.headS()), Thunk.delay(() -> next().zipWithS(f, other.next())));
    }

    // Map every value of the stream with a function, returning a new stream.
    public <R> Stream<R> fmap(Function<T, R> f) {
        return new Stream(f.apply(headS()), Thunk.delay(()->next().fmap(f)) );
    }

    // Helper class, to create cyclic declarations, may be helpful for generating the fibonacci numbers.
    public static class CyclicRef<T> {
        public T value;

        public CyclicRef(T value) {
          this.value = value;
        }
    }

    // Return the stream of all fibonacci numbers.
    public static Stream<Integer> fibS() {
            return fibImplS(0, 1);
    }

        public static Stream<Integer> fibImplS(Integer f1, Integer f2) {
      return new Stream(f1 + f2, Thunk.delay(() -> fibImplS(f2, f1 + f2)));
    }

    // Return the stream of all prime numbers.
    public static Stream<Integer> primeS() {
            return Stream.fromS(2).filterS(x -> isPrime(x)).get();
    }
    public static Boolean isPrime(Integer n) {
        for (int i = 2; i < Math.ceil(Math.sqrt(n)); n++) {
        if (n % i == 0) {
          return false;
        }
      }

      return true;
    }

}

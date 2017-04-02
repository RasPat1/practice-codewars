package stream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Random;

import stream.Stream;

public class StreamTest {
    private Random rand = new Random();

    public static void assertEqInt(String m, int e, int a) {
        assertEquals(m, e, a);
    }

    @Test
    public void testBasics() {
        Stream<Integer> s = Stream.repeatS(0);

        assertEqInt("head of 0 sequence should be 0", 0, s.headS());
        assertEqInt("head of tail of the 0 sequence should be 0", 0, s.next().headS());

        for (int i = 0; i < 20; i++) {
            Stream<Integer> t = new Stream<Integer>(i, Thunk.now(s));
            assertEqInt("adding and removing should result in the same element", i, t.headS());
            assertEqInt("adding and removing should result in the same element after the head", s.headS(), t.next().headS());
        }
    }

    @Test
    public void testConstructors() {
        // repeatS
        {
            int v = rand.nextInt();
            for (int i : Stream.repeatS(v).takeS(10)) {
                assertEqInt("repeatS should repeat the same value", v, i);
            }
        }

        // iterateS
        {
            int multiplier = rand.nextInt();

            int previousI = 1;
            Stream<Integer> twiceStream = Stream.iterateS(x -> x * multiplier, 1);

            String previousS = "";
            Stream<String> addWSStream = Stream.iterateS(x -> x + " ", "");
            for (int i = 0; i < 10; i++) {
                assertEqInt("iterateS with doubling of ints", previousI, twiceStream.headS());
                assertEquals("iterateS with adding whitespaces", previousS, addWSStream.headS());

                twiceStream = twiceStream.next();
                addWSStream = addWSStream.next();
                previousI = previousI * multiplier;
                previousS = previousS + " ";
            }
        }

        // cycleS
        {
            ArrayList<Integer> l = new ArrayList<>(20);
            for (int i = 0; i < 20; i++) {
                l.add(rand.nextInt());
            }

            Stream<Integer> cs = Stream.cycleS(l);

            for (int i = 0; i < 40; i++) {
                assertEqInt("cycleS should repeat the list", l.get(i % 20), cs.headS());
                cs = cs.next();
            }
        }

        // fromS
        {
            int v = rand.nextInt(4000);

            Stream<Integer> s = Stream.fromS(v);
            for (int i = v; i <= v + 20; i++) {
                assertEqInt("fromS should count", i, s.headS());
                s = s.next();
            }
        }

        // fromThenS
        {
            int v = rand.nextInt();
            int d = rand.nextInt();
            Stream<Integer> s = Stream.fromThenS(v, d);
            for (int i = 1; i <= 20; i++) {
                assertEqInt("fromThenS should count by step", v, s.headS());
                v = v + d;
                s = s.next();
            }
        }
    }

    @Test
    public void testReduceAndModification() {
        // foldrS

         {
             int v = rand.nextInt();

             Stream<Integer> s = Stream.repeatS(v).foldrS((x, r) -> new Stream<Integer>(x + 1, r));
             for (int i : s.takeS(10)) {
                 assertEqInt("folding should work, with lazy tail", v + 1, i);
             }

             Stream<Integer> t = Stream.fromS(v);
             assertEqInt("folding without touching the tail", v, t.foldrS((x, r) -> x));
         }


        // filterS

        {
            for (int i : Stream.fromS(rand.nextInt()).filterS(x -> x % 2 == 0).get().takeS(10)) {
                System.out.println(i);
                assertTrue("filtering odds with filterS", i % 2 == 0);
            }
        }

        // takeS
        {
            Stream<Integer> s = Stream.fromS(0);

            for (int i = -2; i <= 2; i++) {
                assertEqInt("takeS should get the correct size", s.takeS(i).size(), Math.max(0, i));
            }
        }

        // dropS
        {
            Stream<Integer> s = Stream.fromS(0);

            for (int i = -2; i <= 2; i++) {
                assertEqInt("dropS should drop the correct amount", s.dropS(i).headS(), Math.max(0, i));
            }
        }

        // zipWithS
        {
             Stream<Integer> s = Stream.fromS(0).zipWithS((x, y) -> x * 2 + y, Stream.repeatS(42));
             Stream<Integer> t = Stream.fromThenS(42, 2);

             for (int i = 0; i < 20; i++) {
                 assertEqInt("zipWithS should work", t.headS(), s.headS());
                 s = s.next();
                 t = t.next();
             }
         }

        // fmap
         {
             Stream<Integer> s = Stream.fromThenS(42, 2).fmap(x -> (x - 42) / 2);
             Stream<Integer> t = Stream.fromS(0);

             for (int i = 0; i < 20; i++) {
                 assertEqInt("fmap should work", s.headS(), t.headS());

                 s = s.next();
                 t = t.next();
             }
         }
    }
}
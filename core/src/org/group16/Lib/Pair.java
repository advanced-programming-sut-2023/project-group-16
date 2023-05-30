package org.group16.Lib;

//<T1 extends Comparable<T1>, T2 extends Comparable<T2>>  implements Comparable<Pair<T1, T2>>
public class Pair<T1, T2> {
    private T1 a;
    private T2 b;

    public Pair(T1 a, T2 b) {
        this.a = a;
        this.b = b;
    }

    public T1 getA() {
        return a;
    }

    public void setA(T1 a) {
        this.a = a;
    }

    public T2 getB() {
        return b;
    }

    public void setB(T2 b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?> pair) {
            return pair.a.equals(a) && pair.b.equals(b);
        }
        return false;
    }

}

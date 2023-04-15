package org.group16.Lib;

//
public class OrderedPair<T1 extends Comparable<T1>, T2 extends Comparable<T2>> implements Comparable<OrderedPair<T1, T2>> {
    private T1 a;
    private T2 b;

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
        if (obj instanceof OrderedPair<?, ?> pair) {
            return pair.a.equals(a) && pair.b.equals(b);
        }
        return false;
    }

    @Override
    public int compareTo(OrderedPair<T1, T2> other) {
        if (a.equals(other.a))
            return b.compareTo(other.b);
        return a.compareTo(other.a);
    }
}

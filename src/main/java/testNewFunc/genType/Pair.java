package testNewFunc.genType;

public class Pair<T, V extends T> {


    T first;
    V second;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}

class PairTest {
    public static void main(String[] args) {
        Pair<Number, Integer> pair = new Pair<>(123.4, 345);
        System.out.println(pair.toString());
    }
}

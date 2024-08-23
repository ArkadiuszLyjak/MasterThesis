package testNewFunc.genType;


class TwoGen<T, V> {
    private T t;
    private V v;

    TwoGen(T t, V v) {
        this.t = t;
        this.v = v;
    }

    public T getT() {
        return t;
    }

    public V getV() {
        return v;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setV(V v) {
        this.v = v;
    }

    void showAllObj() {
        System.out.println("ARG T: " + getT());
        System.out.println("Arg V: " +  getV());
    }
}


public class SimpGen {

    public static void main(String[] args) {
        System.out.println("wiiooo");

        TwoGen<String, Integer> stringIntegerTwoGen = new TwoGen<>("Arek", 44);
        stringIntegerTwoGen.showAllObj();

    }

}

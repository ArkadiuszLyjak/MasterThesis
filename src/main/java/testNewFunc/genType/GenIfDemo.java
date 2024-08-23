package testNewFunc.genType;


import java.util.Arrays;

interface Containment<T> {
    boolean contains(T o);
}


public class GenIfDemo<T> implements Containment<T> {
    T[] tab;

    GenIfDemo(T[] t) {
        tab = t;
    }

    public boolean contains(T t) {
        for (T typ: tab) {
            if (typ.equals(t)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GenIfDemo{" +
                "tab=" + Arrays.toString(tab) +
                '}';
    }
}

class DemoInterface {
    public static void main(String[] args) {
        Integer[] integers = {1, 23, 343, 667};

        GenIfDemo<Integer> genIfDemo = new GenIfDemo<>(integers);
//        System.out.println(genIfDemo);
        System.out.println(genIfDemo.contains(1));
    }
}

package testNewFunc.genType;

public class GenDemo<T> {

    private T ob;

    GenDemo(T o) {
        ob = o;
    }

    public T getOb() {
        return ob;
    }

    public void setOb(T ob) {
        this.ob = ob;
    }

    public void showTob() {
        System.out.println(this.getOb());
    }
}

class GenDemoMainApp {
    public static void main(String[] args) {
        GenDemo<String> ob = new GenDemo<>("Dupka");
        ob.showTob();

        GenDemo<Integer> obInt = new GenDemo<>(234545);
        obInt.showTob();
    }
}

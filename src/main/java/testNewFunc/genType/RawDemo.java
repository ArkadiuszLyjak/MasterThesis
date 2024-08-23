package testNewFunc.genType;

public class RawDemo<T> {
    T ob;

    public RawDemo(T ob) {
        this.ob = ob;
    }

    public T getOb() {
        return ob;
    }

    public void setOb(T ob) {
        this.ob = ob;
    }

    @Override
    public String toString() {
        return "RawDemo{" +
                "ob=" + ob +
                '}';
    }
}

class MainRawDemo {
    public static void main(String[] args) {

        try {

            RawDemo<Integer> rawDemo = new RawDemo<>(435);
            RawDemo<String> stringRawDemo = new RawDemo<>("A to string");
            RawDemo demo = new RawDemo<>(new Double(34.455));

            double d = (Double) demo.getOb();
            System.out.println(d);

//            int i = (Integer) demo. getOb();

//            stringRawDemo = demo;
//            System.out.println(stringRawDemo.toString());
        }catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }
}

package testNewFunc.genType;

public class GenConsDemo {

    private int sum;

    <T extends Number> GenConsDemo(T argT) {
        sum = 0;

        for (int i = 0; i <= argT.intValue(); i++) {
            sum += i;

        }
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "GenConsDemo{" +
                "sum=" + sum +
                '}';
    }
}

class Summation {
    public static void main(String[] args) {
        GenConsDemo genConsDemo = new GenConsDemo(585_403_856);
        String result = genConsDemo.toString();
        System.out.println(result);
    }
}

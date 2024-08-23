package testNewFunc.genType;

public class NumericFNS<T extends Number> {
    T t;

    public NumericFNS(T t) {
        this.t = t;
    }

    double reciprocal() {
        return 1/ t.doubleValue();
    }

    double returnFraction() {
        return t.doubleValue() - t.intValue();
    }

    boolean absEqual(NumericFNS<?> obj) {
        return Math.abs(t.doubleValue()) == Math.abs(obj.t.doubleValue());

    }
}

class MainApp {
    public static void main(String[] args) {
        NumericFNS<Double> numericFNS = new NumericFNS<>(34.3445);
        NumericFNS<Number> numberNumericFNS = new NumericFNS<>(34.3445);

        boolean result = numericFNS.absEqual(numberNumericFNS);
        System.out.println(result);

//        System.out.println(numericFNS.reciprocal());
//        System.out.println(numericFNS.returnFraction());
    }
}

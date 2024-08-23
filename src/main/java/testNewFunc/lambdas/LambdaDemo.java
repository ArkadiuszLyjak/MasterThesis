package testNewFunc.lambdas;

interface MyValue {
    double getValue();
}


interface MyParamValue {
    double getValue(double v);
}

interface SomeTest<T> {
    boolean test(T a, T b);
}


interface NumericTest {
    boolean test(int n, int m);
}

public class LambdaDemo {
    public static void main(String[] args) {

//        MyValue myValue;
//        myValue = () -> 45.545;

//        MyParamValue myParamValue;
//        myParamValue = (d) -> 1/d;

//        System.out.println(myValue.getValue());
//        System.out.println(myParamValue.getValue(23.34));

//        NumericTest lessThan;
//        NumericTest isEqual;
//        NumericTest isFactor;

//        isFactor = (a, b) -> a % b == 0;
//        System.out.println(isFactor.test(1, 2));
//
//        isEqual = (a, b) -> a == b;
//        System.out.println(isEqual.test(1, 1));
//
//        lessThan = (n, m) -> (n < 0 ? -n : n) == (m < 0 ? -m : m);
//        System.out.println(lessThan.test(-1, 1));

        SomeTest<String> stringSomeTest = (String s1, String s2) -> s1.equals(s2);
        boolean test = stringSomeTest.test("dupa", "dupa");
        System.out.println(test);

    }
}

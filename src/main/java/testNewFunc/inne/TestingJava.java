package testNewFunc.inne;

import java.io.IOException;


public class TestingJava {

    public static void main(String[] args) throws IOException {

//        byte b = 33;
//        short s = 34;
//        long l = 3234234;
//        int i = 10;
//        float f = 34.4566F;
//        double d = 34.34;
//        String str = new String("jakis napis");
//        BigInteger bigInteger = new BigInteger("3424536475675857757");
//        BigDecimal bigDecimal = new BigDecimal(bigInteger, MathContext.DECIMAL64);


    }
}

class Sup {
    void who() {
        System.out.println("who() klasy Sup");
    }
}

class Sub1 extends Sup {
    void who() {
        System.out.println("who() klasy Sub1");
    }
}

class Sub2 extends Sup {
    void who() {
        System.out.println("who() klasy Sub2");
    }
}

class DynDispDemo {
    public static void main(String args[]) {
        Sup superOb = new Sup();
        Sub1 subOb1 = new Sub1();
        Sub2 subOb2 = new Sub2();

        Sup supRef;

        supRef = superOb;
        supRef.who();

        supRef = subOb1;
        supRef.who();

        supRef = subOb2;
        supRef.who();
    }
}










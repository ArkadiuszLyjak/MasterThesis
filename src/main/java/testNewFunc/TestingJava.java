package testNewFunc;

import java.io.IOException;


public class TestingJava {

    public static void main(String[] args) throws IOException {
        System.out.println("Testowanie zaczęte...");

//        byte b = 33;
//        short s = 34;
//        long l = 3234234;
//        int i = 10;
//        float f = 34.4566F;
//        double d = 34.34;
//        String str = new String("jakis napis");
//        BigInteger bigInteger = new BigInteger("3424536475675857757");
//        BigDecimal bigDecimal = new BigDecimal(bigInteger, MathContext.DECIMAL64);


        StaticDemo ob1 = new StaticDemo();
        StaticDemo ob2 = new StaticDemo();

        // Ka¿dy obiekt ma w³asn¹ kopiê sk³adowej x.
        ob1.x = 10;
        ob2.x = 20;
        System.out.println("Oczywiœcie, sk³adowe ob1.x i ob2.x " + "s¹ niezale¿ne.");
        System.out.println("ob1.x: " + ob1.x + "\nob2.x: " + ob2.x);
        System.out.println();

        // Ka¿dy obiekt wspó³dzieli z innymi jedn¹ kopiê zmiennej static.
        System.out.println("Zmienna y jest zadeklarowana jako static i tym samym wspó³dzielona.");
        StaticDemo.y = 19;
        System.out.println("Nadaje StaticDemo.y wartoœæ 19.");

        System.out.println("ob1.sum(): " + ob1.sum());
        System.out.println("ob2.sum(): " + ob2.sum());
        System.out.println();

        StaticDemo.y = 100;
        System.out.println("Zmienia wartoœæ StaticDemo.y na 100");

        System.out.println("ob1.sum(): " + ob1.sum());
        System.out.println("ob2.sum(): " + ob2.sum());
        System.out.println();
    }
}


class StaticDemo {
    static int y; // zmienna static
    int x; // zwyk³a zmienna sk³adowa

    // Zwraca sumê sk³adowej x
    // i zmiennej static y.
    int sum() {
        return x + y;
    }
}






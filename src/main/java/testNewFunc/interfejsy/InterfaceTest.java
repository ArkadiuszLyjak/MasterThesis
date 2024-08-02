package testNewFunc.interfejsy;

class Itest implements Common {
    public Itest() {
    }

    public void func() {
        System.out.println("Wywołana metoda func() z kl. Itest");
    }
}
public class InterfaceTest {
    public static void main(String[] args) {
        Itest obj = new Itest();
        obj.func();
        obj.func2();

        Common common = new Itest();
        common.func();
        common.func2();
        Common.func3();
    }
}

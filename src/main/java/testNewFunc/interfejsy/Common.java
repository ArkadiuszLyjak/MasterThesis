package testNewFunc.interfejsy;

public interface Common {

    public void func();

    public default void func2() {
        System.out.println("Wywołana metoda func2() z int. Common");
    };

    public static void func3() {
        System.out.println("Wywołana met. stat. func3() z int. Common");
    }
}

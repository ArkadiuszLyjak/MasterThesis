package testNewFunc.Numbers;

public class NumbersApp {
    public static void main(String[] args) {
        System.out.println("Numbers testing...");

        int number1 = 10;
        int number2 = 20;


        NumbersIntegers numbersIntegers = new NumbersIntegers(1);
        System.out.println(numbersIntegers.isEven(number1));
        numbersIntegers.isEven();
    }
}

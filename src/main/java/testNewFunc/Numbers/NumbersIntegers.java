package testNewFunc.Numbers;

public class NumbersIntegers extends NumbersBase {

    int n;

    public NumbersIntegers(int n) {
        this.n = n;
    }

    public boolean isEven() {
        return n % 2 == 0;
    }
}

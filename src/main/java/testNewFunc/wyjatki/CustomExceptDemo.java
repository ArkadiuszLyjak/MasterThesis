package testNewFunc.wyjatki;

// Używa własnego wyjątku.
// Tworzy klasę wyjątku.
class NonIntResultException extends Exception {
    int a, b;

    public NonIntResultException(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "NonIntResultException: " + a + " / " + b + " daje wynik niecałkowity.";
    }
}

public class CustomExceptDemo {
    public static void main(String[] args) {
        // Tablica numer zawiera kilka wartości do testowania.
        int numer[] = {4, 8, 15, 32, 64, 127, 256, 512};
        int denom[] = {2, 0, 4, 4, 0, 8}; // Tablica dzielników.

        for (int i = 0; i < numer.length; i++) {
            try {
                if (i >= denom.length) {
                    throw new ArrayIndexOutOfBoundsException("Brak odpowiedniego dzielnika dla " + numer[i]);
                }

                if (denom[i] == 0) throw new ArithmeticException("Dzielenie przez zero!");

                double result = (double) numer[i] / denom[i];

                System.out.println("Wynik " + numer[i] + " / " + denom[i] + " = " + result);

                // Sprawdzenie, czy wynik jest liczbą całkowitą.
                if (result % 1 != 0) {
                    throw new NonIntResultException(numer[i], denom[i]);
                }
            } catch (NonIntResultException e) {
                System.out.println(e);
            } catch (ArithmeticException e) {
                System.out.println("Błąd arytmetyczny: dzielenie przez zero przy " + numer[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Błąd indeksu: " + e.getMessage());
            }
        }
    }
}

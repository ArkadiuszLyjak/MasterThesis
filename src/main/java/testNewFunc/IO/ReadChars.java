package testNewFunc.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadChars {

    public static void main(String[] args) throws IOException {
        String providedString = returnStringFromInput();
        Character character = returnCharFromInput();
    }

    public static String returnStringFromInput() throws IOException {
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter a string (type 'SS' to stop): ");

            do {
                line = bufferedReader.readLine();
                System.out.println("The string is: " + line);
            } while (!line.equals("SS"));

            System.out.println("Zakończono.");
        }

        return line;
    }

    public static Character returnCharFromInput() throws IOException {
        char c = 0;

        System.out.println("Enter a character: ");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            c = (char) br.read();
            System.out.println("The character is: " + c);
        }

        return c;
    }
}

package testNewFunc.IO;

import java.io.*;

public class KtoD {
    public static void main(String[] args) throws IOException {
        String path = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc2.txt";
        takeInputString(path);
        readFile(path);
    }

    static void takeInputString(String filePath) {
        String line;

        System.out.println("Wpisz tekst (naciśnij Enter na pustej linii, aby zakończyć): ");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             FileWriter fileWriter = new FileWriter(filePath)) {

            while (true) {
                line = br.readLine();
                if (line.isEmpty()) {
                    break;
                }
                fileWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Zawartość pliku:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

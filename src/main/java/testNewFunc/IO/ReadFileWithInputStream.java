package testNewFunc.IO;

import java.io.*;

public class ReadFileWithInputStream {

    public static void main(String[] args) throws IOException {
        String filePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\numbers.txt";

        // Zapisz liczby do pliku
        writeNumbersToFile(filePath);

        // Odczytaj liczby z pliku przy użyciu FileInputStream
        Double[] numbers = readNumbersFromFile(filePath);

        // Oblicz i wyświetl średnią
        if (numbers != null) {
            double average = avgNums(numbers);
            System.out.println("Średnia: " + average);
        }
    }

    public static void writeNumbersToFile(String filePath) {
        Double[] numbers = new Double[]{2.2, 3.3, 4.4};

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Double number : numbers) {
                writer.write(number.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Double[] readNumbersFromFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath)))) {

            return reader.lines()
                    .map(Double::valueOf)
                    .toArray(Double[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double avgNums(Double... a) {
        double sum = 0.0;

        for (double v : a) {
            sum += v;
        }

        return sum / a.length;
    }
}

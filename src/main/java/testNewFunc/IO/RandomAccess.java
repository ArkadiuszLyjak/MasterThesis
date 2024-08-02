package testNewFunc.IO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomAccess {

    public static void main(String[] args) throws IOException {
        String fileAbsolutePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\rwaccess.dat";

        // Pobranie pełnej ścieżki
        String absolutePath = FilePathInfo.filePath(new File(fileAbsolutePath), "absolute");

        // Podwójne ukośniki w pełnej ścieżce
        String absolutePathWithSlashDoubled = absolutePath.replace("\\", "\\\\");

        // Pobranie nazwy pliku
        String filename = FilePathInfo.filePath(new File(fileAbsolutePath), "fileName");

        // Utworzenie tablicy double z losowymi wartościami
        double[] doubleData = RandomArray.doubleArrayRandomCreate(1000);

        for (double d : doubleData) {
            System.out.println(d);
        }

        // Zapis do pliku
        try (RandomAccessFile raf = new RandomAccessFile(fileAbsolutePath, "rw")) {
            for (double datum : doubleData) {
                raf.writeDouble(datum);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // Odczyt z pliku
        try (RandomAccessFile raf = new RandomAccessFile(fileAbsolutePath, "r")) {
            System.out.println("Rozmiar pliku w bajtach: " + raf.length());

            // Odczyt i wyświetlenie wartości double z pliku
            for (int i = 0; i < raf.length() / 8; i++) {
                raf.seek(i * 8);
                double d = raf.readDouble();
                System.out.println(d);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static class RandomAccessFileClass {
        private RandomAccessFile randomAccessFile;
        private final String filePath;
        private final String mode;

        RandomAccessFileClass(String filePath, String mode) throws IOException {
            this.filePath = filePath;
            this.mode = mode;
            getRandomAccessFile();
        }

        private void getRandomAccessFile() throws IOException {
            if (randomAccessFile == null) {
                randomAccessFile = new RandomAccessFile(filePath, mode);
            }
        }
    }

    private static class RandomArray {
        static double[] doubleArrayRandomCreate(int multiplier) {
            double[] data = new double[10];
            Random random = new Random();
            for (int i = 0; i < data.length; i++) {
                data[i] = round(random.nextDouble() * multiplier, 4);
            }
            return data;
        }

        private static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException("Liczba miejsc po przecinku nie może być ujemna.");
            BigDecimal bd = new BigDecimal(Double.toString(value));
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    private static class FilePathInfo {
        static String filePath(File file, String mode) {
            String path = "";
            switch (mode) {
                case "absolute":
                    path = file.getAbsolutePath();
                    break;
                case "fileName":
                    path = file.getName();
                    break;
                case "parent":
                    path = file.getParent();
                    break;
                default:
                    throw new IllegalArgumentException("Nieznany tryb: " + mode);
            }
            return path;
        }
    }
}

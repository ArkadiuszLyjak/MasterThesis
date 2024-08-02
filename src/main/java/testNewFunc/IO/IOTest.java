package testNewFunc.IO;

// Wczytuje tablicę bajtów z klawiatury.

import java.io.*;

public class IOTest {
    public static void main(String[] args) throws IOException {
        String filePathRead = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc.txt";
        String filePathWrite = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc2.txt";

        File fileToReadFrom = new File(filePathRead);
        File fileToWriteTo = new File(filePathWrite);

        try (BufferedReader br = new BufferedReader(new FileReader(fileToReadFrom));
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWriteTo))) {

                 String line;

                 while ((line = br.readLine()) != null) {
                     bw.write(line);
                     bw.write("\n");
                 }
             }catch(IOException e) {
            e.printStackTrace();
        }

    }
}

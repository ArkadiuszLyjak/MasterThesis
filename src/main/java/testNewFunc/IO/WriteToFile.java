package testNewFunc.IO;

import java.io.*;

public class WriteToFile {


    public static void main(String[] args) {
        String filePathRead = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc.txt";
        String filePathWrite = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc2.txt";


        File fileRead;
        File fileWrite;

        FileOutputStream fos;
        FileInputStream fis;

        int i;

        try {
            fileRead = new File(filePathRead);
            fileWrite = new File(filePathWrite);

            fis = new FileInputStream(fileRead);
            fos = new FileOutputStream(fileWrite);

            do {
                i = fis.read();
                fos.write(i);
            } while (i != -1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

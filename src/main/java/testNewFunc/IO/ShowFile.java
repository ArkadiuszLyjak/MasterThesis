package testNewFunc.IO;

import java.io.*;

public class ShowFile {

    String filePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc.txt";

    public static void main(String[] args) {
        FileInputStream fis = null;
        BufferedReader br = null;
        String filePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc.txt";

        try {
            br = new BufferedReader(new FileReader(filePath));
            fis = new FileInputStream(filePath);

//            int i;
//            while ((i = fis.read()) != -1) {
//                System.out.println((char) i);
//            }

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

                if (br != null) {
                    br.close();
                }
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }


    }


}

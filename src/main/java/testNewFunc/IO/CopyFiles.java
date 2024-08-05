package testNewFunc.IO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFiles {

    public static void main(String[] args) throws FileNotFoundException {
        String sourcePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc.txt";
        String targetPath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\dest.txt";

//        copyFileUsingIO(sourcePath, targetPath);
//        copyFileUsingNIO(sourcePath, targetPath);

        checkFilesEqual(sourcePath, targetPath);
    }

    // copy using NIO
    public static void copyFileUsingNIO(String srcPath, String destPath) {

    }

    public static boolean checkFilesEqual(String srcPath, String destPath) {

        boolean equalsORnot;

        try (FileInputStream fileInputStreamFirst = new FileInputStream(srcPath);
             FileInputStream fileInputStreamSecond = new FileInputStream(destPath)) {

            int firstByteOfSourceFile;
            int secondByteOfDestFile;

            do {
                firstByteOfSourceFile = fileInputStreamFirst.read();
                secondByteOfDestFile = fileInputStreamSecond.read();
                System.out.print((char) firstByteOfSourceFile);
//                System.out.print((char) firstByteOfSourceFile + " --> " + (char) secondByteOfDestFile);

            } while (firstByteOfSourceFile != -1 && secondByteOfDestFile != -1
                    && firstByteOfSourceFile == secondByteOfDestFile);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    // copy using IO
    public static void copyFileUsingIO(String srcPath, String destPath) {

        try (FileInputStream fis = new FileInputStream(srcPath);
             FileOutputStream fos = new FileOutputStream(destPath)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

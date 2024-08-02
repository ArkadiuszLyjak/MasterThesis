package testNewFunc.IO;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathExample {
    public static void main(String[] args) {
        // Ścieżka do pliku
        String filePathWrite = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\dataFiles\\Arc2.txt";

        // Tworzenie obiektu Path
        Path path = Paths.get(filePathWrite);

        // Ścieżka absolutna
        Path absolutePath = path.toAbsolutePath();
        System.out.println("Ścieżka absolutna: " + absolutePath);

        // Ścieżka względna (zależna od bieżącego katalogu roboczego)
        Path relativePath = Paths.get("src/main/java/testNewFunc/dataFiles/Arc2.txt");
        System.out.println("Ścieżka względna: " + relativePath);

        // Nazwa pliku
        Path fileName = path.getFileName();
        System.out.println("Nazwa pliku: " + fileName);

        // Ścieżka do katalogu rodzica
        Path parentDir = path.getParent();
        System.out.println("Ścieżka do katalogu rodzica: " + parentDir);

        // Ścieżka do katalogu rodzica katalogu rodzica
        Path grandParentDir = parentDir.getParent();
        System.out.println("Ścieżka do katalogu dziadka: " + grandParentDir);

        // Sprawdzanie, czy ścieżka jest absolutna
        boolean isAbsolute = path.isAbsolute();
        System.out.println("Czy ścieżka jest absolutna? " + isAbsolute);

        // Budowanie ścieżki względnej z katalogu bieżącego
        Path currentDirRelative = Paths.get("testNewFunc", "dataFiles", "Arc2.txt");
        System.out.println("Ścieżka względna z katalogu bieżącego: " + currentDirRelative);

        // Budowanie ścieżki z katalogu bieżącego z użyciem metody resolve
        Path resolvedPath = Paths.get("").resolve("src/main/java/testNewFunc/dataFiles/Arc2.txt");
        System.out.println("Ścieżka z użyciem resolve: " + resolvedPath);
    }
}

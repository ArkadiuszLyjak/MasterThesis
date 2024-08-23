package testNewFunc.IO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class RenameFilesAndDirectories {

    private static final Map<Character, String> polishToEnglishMap = new HashMap<>();

    static {
        polishToEnglishMap.put('±', "a");
        polishToEnglishMap.put('æ', "c");
        polishToEnglishMap.put('ê', "e");
        polishToEnglishMap.put('³', "l");
        polishToEnglishMap.put('ñ', "n");
        polishToEnglishMap.put('ó', "o");
        polishToEnglishMap.put('¶', "s");
        polishToEnglishMap.put('¼', "z");
        polishToEnglishMap.put('¿', "z");
        polishToEnglishMap.put('¡', "A");
        polishToEnglishMap.put('Æ', "C");
        polishToEnglishMap.put('Ê', "E");
        polishToEnglishMap.put('£', "L");
        polishToEnglishMap.put('Ñ', "N");
        polishToEnglishMap.put('Ó', "O");
        polishToEnglishMap.put('¦', "S");
        polishToEnglishMap.put('¬', "Z");
        polishToEnglishMap.put('¯', "Z");
    }

    public static void main(String[] args) {
        String directoryPath = "e:\\Filmoteka\\";
        File directory = new File(directoryPath);
        String logFilePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\rename_log.txt";
        String conflictFilePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\conflicts_log.txt";
        String directoryConflictFilePath = "C:\\repo\\MasterThesis\\src\\main\\java\\testNewFunc\\IO\\directory_conflicts_log.txt";

        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFilePath));
             BufferedWriter conflictWriter = new BufferedWriter(new FileWriter(conflictFilePath));
             BufferedWriter directoryConflictWriter = new BufferedWriter(new FileWriter(directoryConflictFilePath))) {

            if (directory.exists() && directory.isDirectory()) {
                renameFilesAndDirectories(directory, logWriter, conflictWriter, directoryConflictWriter);
            } else {
                System.out.println("Podana ¶cie¿ka nie jest katalogiem lub nie istnieje.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renameFilesAndDirectories(File directory, BufferedWriter logWriter, BufferedWriter conflictWriter, BufferedWriter directoryConflictWriter) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // Pomiñ ukryte pliki/katalogi, pliki systemowe oraz te zaczynaj±ce siê od '_'
                if (file.isHidden() || file.getName().startsWith("_") || isSystemFile(file)) {
                    continue;
                }

                if (file.isDirectory()) {
                    if (renameFileOrDirectory(file, true, logWriter, conflictWriter, directoryConflictWriter)) {
                        System.out.println("Przetwarzanie katalogu: " + file.getAbsolutePath());
                        logWriter.write("Przetwarzanie katalogu: " + file.getAbsolutePath());
                        logWriter.newLine();
                    }
                    renameFilesAndDirectories(file, logWriter, conflictWriter, directoryConflictWriter); // Rekursja do obs³ugi podkatalogów
                } else {
                    if (renameFileOrDirectory(file, false, logWriter, conflictWriter, directoryConflictWriter)) {
                        System.out.println("Przetwarzanie pliku: " + file.getAbsolutePath());
                        logWriter.write("Przetwarzanie pliku: " + file.getAbsolutePath());
                        logWriter.newLine();
                    }
                }

                logWriter.newLine(); // Dodanie pustej linii po ka¿dym pliku/katalogu
            }
        }
    }

    public static boolean isSystemFile(File file) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        return attrs.isOther(); // Mo¿e wymagaæ dostosowania w zale¿no¶ci od specyficznych atrybutów systemu plików
    }

    public static boolean renameFileOrDirectory(File file, boolean isDirectory, BufferedWriter logWriter, BufferedWriter conflictWriter, BufferedWriter directoryConflictWriter) throws IOException {
        String originalName = file.getName();
        String newNameStr = convertToValidName(originalName);

        File newFile = new File(file.getParent(), newNameStr);
        if (!file.equals(newFile)) {
            if (newFile.exists()) {
                if (isDirectory) {
                    directoryConflictWriter.write("Conflict: " + file.getAbsolutePath() + " -> " + newFile.getAbsolutePath());
                    directoryConflictWriter.write("Reason: Directory with new name already exists.");
                    directoryConflictWriter.newLine();
                } else {
                    conflictWriter.write("Conflict: " + file.getAbsolutePath() + " -> " + newFile.getAbsolutePath());
                    conflictWriter.write("Reason: File with new name already exists.");
                    conflictWriter.newLine();
                }
                return false;
            }

            boolean success = file.renameTo(newFile);
            if (success) {
                logWriter.write("Renamed: " + file.getAbsolutePath() + " -> " + newFile.getAbsolutePath());
            } else {
                logWriter.write("Failed to rename: " + file.getAbsolutePath());
                logWriter.write("Reason: Unable to rename file or directory, possibly due to length constraints or permissions.");
            }
            logWriter.newLine();
            return true;
        }

        return false;
    }

    public static String convertToValidName(String name) {
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (polishToEnglishMap.containsKey(c)) {
                newName.append(polishToEnglishMap.get(c));
            } else if (Character.isLetterOrDigit(c) || c == '.' || (c == '_' && i > 0 && name.charAt(i - 1) != '_')) {
                newName.append(c);
            } else {
                newName.append('_');
            }
        }

        // Usuwanie podkre¶leñ z koñca nazwy
        while (newName.length() > 0 && newName.charAt(newName.length() - 1) == '_') {
            newName.setLength(newName.length() - 1);
        }

        // Zamiana wielokrotnych podkre¶leñ na pojedyncze podkre¶lenie
        return newName.toString().replaceAll("_+", "_");
    }
}

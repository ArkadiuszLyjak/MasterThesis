package MasterThesis.arc_file_tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class FileDataReader {
    public static void netFileRead(String sFileName, Consumer<String> consumer) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(sFileName))) {
            int lines = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.substring(0,2).compareTo("//") != 0) {
                    if (lines == 0 ) {
                    }
                    else {
                        consumer.accept(line);
                    }
                    lines++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package MasterThesis.arc_file_tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

public class FileDataWriter {

    //region fileDataWrite
    public static void fileDataWrite(String path, Consumer<PrintWriter> consumer) throws IOException {
        try (FileWriter fileWriter = new FileWriter(path);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            consumer.accept(printWriter);
        }
    }
    //endregion

}

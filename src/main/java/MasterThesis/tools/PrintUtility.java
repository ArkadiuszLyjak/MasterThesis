package MasterThesis.tools;

import java.io.Reader;
import java.util.function.Consumer;
import java.util.function.Function;

public class PrintUtility {

    //region createExtenderWithCharacter
    public static String createExtenderWithCharacter(
            int wholeStringLength,
            Character c,
            Function<String[], Integer> function,
            String... strings) {

        StringBuilder sb = new StringBuilder();

        int length = 0;
        while ((wholeStringLength - (function.apply(strings) + length)) >= 0) {
            length = sb.append(c).length();
        }

        return sb.toString();

    }
    //endregion

}

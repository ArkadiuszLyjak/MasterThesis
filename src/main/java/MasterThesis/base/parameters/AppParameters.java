package MasterThesis.base.parameters;

import MasterThesis.tools.PrintUtility;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

@Getter
@Setter
public class AppParameters {

    //region Description
    private static AppParameters appParameters;

    //Default values
    String arcFileName = "Arc.txt";
    String lineTypeFileName = "Line_Type.txt";
    String nodeFileName = "Node.txt";
    String transformerTypeFileName = "Transformer_Type.txt";
    String dataArcResultsFileName = "ARC_RESULTS.txt";
    //    String dataArcResultsFileName = "MO_DATA_ARC_RESULTS.txt";
    String dataNodeResultsFileName = "NODE_RESULTS.txt";
    //    String dataNodeResultsFileName = "MO_DATA_NODE_RESULTS.txt";
    String delimiter = "|";
    String sourceDataFilesPath = "C:\\repo\\MasterThesis\\dataFiles\\";
    String arcResultsOutputFile = sourceDataFilesPath + "ARC_RESULTS.txt";
    String nodeResultsOutputFile = sourceDataFilesPath + "NODE_RESULTS.txt";
    //endregion

    //region SINGLENTON getInstance
    private AppParameters() {
    }

    public static AppParameters getInstance() {
        if (appParameters == null) {
            appParameters = new AppParameters();
        }
        return appParameters;
    }
    //endregion

    //region toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        final int dimension = 50;
        char filler = '-';

        fmt.format("\n%45s\n", "----- APPLICATION PARAMETERS ------");

        //region Arc file name
        fmt.format("%s %s \"%s\"\n", "Arc file name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        arcFileName, "Arc file name: "),
                arcFileName);
        //endregion

        //region Line type file name
        fmt.format("%s %s \"%s\"\n", "Line type file name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        lineTypeFileName, "Line type file name: "),
                lineTypeFileName);
        //endregion

        //region Node file type name
        fmt.format("%s %s \"%s\"\n", "Node file type name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        nodeFileName, "Node file type name: "),
                nodeFileName);
        //endregion

        //region Transformer type file name
        fmt.format("%s %s \"%s\"\n", "Transformer type file name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        transformerTypeFileName, "Transformer type file name: "),
                transformerTypeFileName);
        //endregion

        //region Data Arc result file name
        fmt.format("%s %s \"%s\"\n", "Data Arc result file name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        dataArcResultsFileName, "Data Arc result file name: "),
                dataArcResultsFileName);
        //endregion

        //region Data node result file name
        fmt.format("%s %s \"%s\"\n", "Data node result file name: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        dataNodeResultsFileName, "Data node result file name: "),
                dataNodeResultsFileName);
        //endregion

        //region Source data file path
        fmt.format("%s %s \"%s\"\n", "Source data file path: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        sourceDataFilesPath, "Source data file path: "),
                sourceDataFilesPath);
        //endregion

        //region Delimiter
        fmt.format("%s %s \"%s\"\n", "Delimiter: ",
                PrintUtility.createExtenderWithCharacter(
                        dimension,
                        filler,
                        strings -> {
                            int lengthStringsSum = 0;
                            for (String s : strings) {
                                lengthStringsSum += s.length();
                            }

                            return lengthStringsSum;
                        },
                        delimiter, "Delimiter: "),
                delimiter);
        //endregion

        return sb.toString();
    }
    //endregion

    //region getters
    public String getNodeFileFullPath() {
        return sourceDataFilesPath + nodeFileName;
    }

    public String getArcFullFileName() {
        return sourceDataFilesPath + arcFileName;
    }

    public String getLineTypeFullFileName() {
        return sourceDataFilesPath + lineTypeFileName;
    }

    public String getNodeFullFileName() {
        return sourceDataFilesPath + nodeFileName;
    }

    public String getTransformerTypeFullFileName() {
        return sourceDataFilesPath + transformerTypeFileName;
    }

    public String getDataArcResultsFullFileName() {
        return sourceDataFilesPath + dataArcResultsFileName;
    }

    public String getDataNodeResultsFullFileName() {
        return sourceDataFilesPath + dataNodeResultsFileName;
    }
    //endregion

}

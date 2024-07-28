package MasterThesis.arc_file_tools;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcFactory;
import MasterThesis.base.parameters.AppParameters;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.lineType.LineTypeFactory;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import MasterThesis.transformer_type.TransformerTypeEntity;
import MasterThesis.transformer_type.TransformerTypeFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class FileDataService {

    static DecimalFormat df = new DecimalFormat("00.000000");
    AppParameters params = AppParameters.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();
    private static FileDataService instance;

    //region getInstance
    private FileDataService() {
    }

    public static FileDataService getInstance() {
        if (instance == null) {
            instance = new FileDataService();
        }
        return instance;
    }
    //endregion

    //region write Arc Results
    public void writeArcResultsToFile() throws IOException {
        String delim = params.getDelimiter();
        FileDataWriter.fileDataWrite(params.getArcResultsOutputFile(), printWriter -> {
            printWriter.write("ID|TYPE|TRANSMIT POWER\n");
            elNet.arcMap.forEach((ID, arcEntity) -> {
                printWriter.print(ID);
                printWriter.print(delim);
                printWriter.write(arcEntity.getType().toString());
                printWriter.print(delim);
                printWriter.printf("%-(2.4f%s", arcEntity.getPowerFlowReal(), delim);
                printWriter.println();
            });
        });
    }
    //endregion

    //region write Node Results
    public void writeNodeResultsToFile() throws IOException {
        String delim = params.getDelimiter();

        FileDataWriter.fileDataWrite(params.getNodeResultsOutputFile(), printWriter -> {
            printWriter.write("Node ID|Voltage\n");
            elNet.nodeMap.forEach((ID, nodeEntity) -> {
                printWriter.printf("%3d%s", ID, delim);
                printWriter.printf("%-9.4f", nodeEntity.getVoltageReal());
//                printWriter.printf("%-9.4f", nodeEntity.getVoltagePU());
                printWriter.print("\n");
            });
        });
    }
    //endregion

    //region readNodeFile
    public void readNodeFile() {
        try {
            FileDataReader.netFileRead(params.getNodeFileFullPath(), fileLine -> {
                NodeEntity node = NodeFactory.prepareFromString(fileLine);
                elNet.nodeMap.put(node.getId(), node);
                elNet.nodeEntityList.add(node);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region readLineTypeFile
    public void readLineTypeFile() {
        try {
            FileDataReader.netFileRead(params.getLineTypeFullFileName(), fileLine -> {
                LineTypeEntity lineType = LineTypeFactory.prepareFromString(fileLine);
                elNet.lineTypeMap.put(lineType.getId(), lineType);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region readTransformerTypeFile
    public void readTransformerTypeFile() {
        try {
            FileDataReader.netFileRead(params.getTransformerTypeFullFileName(), fileLine -> {
                TransformerTypeEntity transformerTypeEntity = TransformerTypeFactory.prepareFromString(fileLine);

                elNet.transformerTypeMap.put(transformerTypeEntity.getId(), transformerTypeEntity);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region readArcFile
    public void readArcFile() {
        try {
            FileDataReader.netFileRead(params.getArcFullFileName(), fileLine -> {
                ArcEntity arc = ArcFactory.prepareFromString(fileLine);

                //region adds all records regardless of state
                elNet.arcMap.put(arc.getId(), arc);
                elNet.arcList.add(arc);
                //endregion
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion

}

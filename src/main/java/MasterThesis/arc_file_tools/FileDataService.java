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

public class FileDataService {

    static FileDataService instance;
    AppParameters params = AppParameters.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

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

    //region readNodeFile
    public void readNodeFile() {
        try {
            FileDataReader.netFileRead(params.getNodeFileFullPath(), fileLine -> {
                NodeEntity node = NodeFactory.prepareFromString(fileLine);
                elNet.nodeMap.put(node.getId(), node);
                elNet.nodeList.add(node);
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

                /*//region Adds only the records that are turned on and prints to the screen
                if (arc.getCondition() == 1) {
                    elNet.arcMap.put(arc.getId(), arc);
                    elNet.arcList.add(arc);
                    System.out.println(arc);
                } else {
                    System.out.printf("[%3d] %3d > %3d %s%n", arc.getId(), arc.getStartNode(), arc.getEndNode(), "-------- not connected --------");
                }
                //endregion*/
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion

}

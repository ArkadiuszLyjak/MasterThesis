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

    //region read_Node_File
    public void read_Node_File() {
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

    //region read_Line_Type_File
    public void read_Line_Type_File() {
        try {
            FileDataReader.netFileRead(params.getLineTypeFullFileName(), fileLine -> {
                        LineTypeEntity lineType = LineTypeFactory.prepareFromString(fileLine);
                        elNet.lineTypeMap.put(lineType.getId(), lineType);
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region read_Transformer_Type_File
    public void read_Transformer_Type_File() {
        try {
            FileDataReader.netFileRead(params.get_Transformer_Type_Full_File_Name(), fileLine -> {
                        TransformerTypeEntity transformer_Type_Entity =
                                TransformerTypeFactory.prepareFromString(fileLine);

                        elNet.transformerTypeMap.put(
                                transformer_Type_Entity.getId(),
                                transformer_Type_Entity);
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region read_Arc_File
    public void read_Arc_File() {
        try {
            FileDataReader.netFileRead(params.getArcFullFileName(), fileLine -> {
                        ArcEntity arc = ArcFactory.prepare_From_String(fileLine);
                        elNet.arcMap.put(arc.getId(), arc);
                        elNet.arcList.add(arc);
                    }
            );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //endregion

}

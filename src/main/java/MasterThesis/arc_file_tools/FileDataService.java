package MasterThesis.arc_file_tools;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcFactory;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.line_type.LineType;
import MasterThesis.line_type.LineTypeFactory;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import MasterThesis.transformer_type.TransformerTypeEntity;
import MasterThesis.transformer_type.TransformerTypeFactory;

import java.io.FileNotFoundException;

public class FileDataService {
    AppParametersService paramsService =  AppParametersService.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();
    static FileDataService instance;
    private FileDataService(){};

    public  static FileDataService getInstance(){
        if (instance == null){
            instance = new FileDataService();
        }
        return instance;
    }


    public void readNodeFiles() {
        try {
            FileDataReader.netFileRead(paramsService.getNodeFileFullPath(),
                    s -> {
                        NodeEntity node = NodeFactory.prepareFromString(s);
                        elNet.nodeMap.put(node.getId(), node);
                    });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public  void readLineTypeFiles() {
        try {
            FileDataReader.netFileRead(paramsService.getLineTypeFullFileName(),
                    s -> {
                        LineType lineType = LineTypeFactory.prepareFromString(s);
                        elNet.lineTypeMap.put(lineType.getId(), lineType);
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void readTransformerTypeFiles() {
        try {
                FileDataReader.netFileRead(paramsService.getTransformerTypeFullFileName(),
                s -> {
                    TransformerTypeEntity transformerTypeEntity = TransformerTypeFactory.prepareFromString(s);
                    elNet.transformerTypeMap.put(transformerTypeEntity.getId(), transformerTypeEntity);
                }
        );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void readArcFiles() {
        try {

            FileDataReader.netFileRead(paramsService.getArcFullFileName(),
                    s -> {
                        ArcEntity arc = ArcFactory.prepareFromString(s);
                        elNet.arcMap.put(arc.getId(), arc);
                    }
            );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

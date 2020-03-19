package MasterThesis.bfs;

import MasterThesis.arc.ArcEntity;
import MasterThesis.line_type.LineType;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class ElectricalNetwork {

    public  List<NodeEntity> nodeList ;
    public  List<TransformerTypeEntity> transformerTypeList ;
    public  List<LineType> lineTypeList ;
    public  List<ArcEntity> arcEntityList ;

    static ElectricalNetwork electricalNetwork;
    private  ElectricalNetwork(){

    }

    public static ElectricalNetwork getInstance(){
        if (electricalNetwork == null){
            electricalNetwork = new ElectricalNetwork();
        }
        return electricalNetwork;
    }
    public void initList(){
         nodeList = new ArrayList<>();
        transformerTypeList = new ArrayList<>();
         lineTypeList = new ArrayList<>();
         arcEntityList = new ArrayList<>();
    }
}

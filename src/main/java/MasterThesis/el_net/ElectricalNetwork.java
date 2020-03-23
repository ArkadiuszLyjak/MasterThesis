package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.line_type.LineType;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ElectricalNetwork {

    public Map<Long ,NodeEntity>            nodeMap;
    public Map<Long, LineType>              lineTypeMap ;
    public Map<Long, TransformerTypeEntity> transformerTypeMap;
    public Map<Long,ArcEntity>              arcMap;
    //Mapa wezlow -> lista lukow
    public Map<Long, List<Long>> nodeArcList_Map = new HashMap<>();

    static ElectricalNetwork electricalNetwork;
    private  ElectricalNetwork(){

    }

    public static ElectricalNetwork getInstance(){
        if (electricalNetwork == null){
            electricalNetwork = new ElectricalNetwork();
            electricalNetwork.initMaps();
            }
        return electricalNetwork;
    }

    public void initMaps(){
        nodeMap            = new HashMap<>();
        lineTypeMap        = new HashMap<>();
        transformerTypeMap = new HashMap<>();
        arcMap             = new HashMap<>();
    }

    public void cleanMaps(){
        nodeMap.clear();
        lineTypeMap.clear();
        transformerTypeMap.clear();
        arcMap.clear();

    }

}

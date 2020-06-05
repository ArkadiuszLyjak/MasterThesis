package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.line_type.LineType;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ElectricalNetwork {

    public Map<Long, LineType>              lineTypeMap ;
    public Map<Long, TransformerTypeEntity> transformerTypeMap;

    public Map<Long ,NodeEntity>    nodeMap;
    public List<NodeEntity>         nodeList;

    public Map<Long,ArcEntity>      arcMap;
    public List<ArcEntity>          arcList;

    //Mapa wezlow -> lista lukow
    public Map<Long, List<Long>> nodeArcList_Map = new HashMap<>();

    static ElectricalNetwork electricalNetwork;
    private  ElectricalNetwork(){

    }

    public static ElectricalNetwork getInstance(){
        if (electricalNetwork == null){
            electricalNetwork = new ElectricalNetwork();
            electricalNetwork.init();
            }
        return electricalNetwork;
    }

    public void init(){
        nodeMap            = new HashMap<>();
        lineTypeMap        = new HashMap<>();
        transformerTypeMap = new HashMap<>();
        arcMap             = new HashMap<>();
        arcList            = new ArrayList<>();
        nodeList           = new ArrayList<>();
    }

    public void clean(){
        nodeMap.clear();
        lineTypeMap.clear();
        transformerTypeMap.clear();
        arcMap.clear();
        arcList.clear();
        nodeList.clear();

    }

    public List<Long> getDistributeNodeIdList() {
        List<Long> distributeNodeList = new ArrayList<>();
        for (Long arcId : nodeArcList_Map.get(0) ){
            distributeNodeList.add(arcMap.get(arcId).getEndNodeId());
        }
        return distributeNodeList;
    }
}

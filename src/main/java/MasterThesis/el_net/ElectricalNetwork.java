package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.el_net.directMethod.InsideLoopEveryNodeCalcEntity;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.util.*;

public class ElectricalNetwork {

    //region Properties
    private static ElectricalNetwork electricalNetwork;
    public Map<Long, ArcEntity> arcMap;                         // maps from files
    public Map<Long, LineTypeEntity> lineTypeMap;               // maps from files
    public Map<Long, NodeEntity> nodeMap;                       // maps from files
    public Map<Long, TransformerTypeEntity> transformerTypeMap; // maps from files

    public List<NodeEntity> nodeList;
    public List<ArcEntity> arcList;

    public Map<Long, List<Long>> neighborsForwardMap = new LinkedHashMap<>();
    public Map<Long, List<Long>> neighborsReverseMap = new LinkedHashMap<>();
    public Map<Long, ArrayList<Long>> nodesNeighborsForwardReverseMap = new LinkedHashMap<>();

    public List<Long> nodesWithNoNeighborsBackList = new LinkedList<>();
    public List<Long> nodesWithNoNeighborsInFrontList = new LinkedList<>();

    // Mapa<nr_iteracji, Mapa<nr_węzła, obl_węzłowe>>
    public Map<Long, Map<Long, InsideLoopEveryNodeCalcEntity>> everyIterateCalcMap = new LinkedHashMap<>();
    //endregion

    /**
     * @see #arcMap
     */

    //region getInstance - Singleton
    //region Constructor
    private ElectricalNetwork() {
    }
    //endregion

    public static ElectricalNetwork getInstance() {
        if (electricalNetwork == null) {
            electricalNetwork = new ElectricalNetwork();
            electricalNetwork.init();
        }
        return electricalNetwork;
    }
    //endregion

    //region init
    public void init() {
        lineTypeMap = new LinkedHashMap<>();
        transformerTypeMap = new LinkedHashMap<>();
        nodeMap = new LinkedHashMap<>();
        arcMap = new LinkedHashMap<>();
        arcList = new LinkedList<>();
        nodeList = new LinkedList<>();
    } //
    //endregion

    //region clean
    public void clean() {
        nodeMap.clear();
        lineTypeMap.clear(); //
        transformerTypeMap.clear(); //
        arcMap.clear(); //
        arcList.clear(); //
        nodeList.clear(); //
    }
    //endregion

}

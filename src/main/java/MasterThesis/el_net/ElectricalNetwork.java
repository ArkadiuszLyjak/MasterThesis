package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElectricalNetwork {

    //region Properties
    private static ElectricalNetwork electricalNetwork;

    public Map<Long, LineTypeEntity> lineTypeMap;               // maps from files
    public Map<Long, TransformerTypeEntity> transformerTypeMap; // maps from files
    public Map<Long, NodeEntity> nodeMap;                       // maps from files
    public Map<Long, ArcEntity> arcMap;                         // maps from files
    public List<NodeEntity> nodeList;
    public List<ArcEntity> arcList;

    public Map<Long, List<Long>> neighborsConsequentMap = new LinkedHashMap<>();
    public Map<Long, List<Long>> neighborsPredecessorMap = new LinkedHashMap<>();
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

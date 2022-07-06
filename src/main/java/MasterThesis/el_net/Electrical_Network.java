package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.line_type.Line_Type_Entity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.Transformer_Type_Entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Electrical_Network {

    //region Properties
    private static Electrical_Network electricalNetwork;

    public Map<Long, Line_Type_Entity> lineTypeMap;                     // maps from files
    public Map<Long, Transformer_Type_Entity> transformerTypeMap; // maps from files
    public Map<Long, NodeEntity> nodeMap;                       // maps from files
    public Map<Long, ArcEntity> arcMap;                         // maps from files
    public List<NodeEntity> nodeList;                           //
    public List<ArcEntity> arcList;                             //

    public Map<Long, List<Long>> neighbors_Consequent_Map = new LinkedHashMap<>();
    public Map<Long, List<Long>> neighborsPredecessorMap = new LinkedHashMap<>();
    //endregion

    /**
     * @see #arcMap
     */

    //region getInstance - Singleton
    //region Constructor
    private Electrical_Network() {
    }
    //endregion

    public static Electrical_Network getInstance() {
        if (electricalNetwork == null) {
            electricalNetwork = new Electrical_Network();
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
        nodeMap.clear(); //
        lineTypeMap.clear(); //
        transformerTypeMap.clear(); //
        arcMap.clear(); //
        arcList.clear(); //
        nodeList.clear(); //
    }
    //endregion

}

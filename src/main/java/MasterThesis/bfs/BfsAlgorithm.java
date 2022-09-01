package MasterThesis.bfs;

import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.NodeArcVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BfsAlgorithm {

    private static BfsAlgorithm instance;
    private static ElectricalNetwork elNet;

    //Mapa odwiedzin na poszczegolnych poziomach
    public Map<Long, List<NodeArcVO>> arcLevelsMap = new HashMap<>();

    //Maksymalny poziom odwiedzin
    Long maxNetLevel;

    //region getInstance BfsAlgorithm - Singleton
    private BfsAlgorithm() {
    }

    public static BfsAlgorithm getInstance() {
        if (instance == null) {
            instance = new BfsAlgorithm();
            elNet = ElectricalNetwork.getInstance();
        }
        return instance;
    }
    //endregion

    //region getNetLevel
    public Long getNetLevel() {
        return maxNetLevel;
    }

    //endregion

    //region isNodeLevelExist
    private boolean isNodeLevelExist(Long level, Long nodeId) {
        if (arcLevelsMap.containsKey(level)) {
            for (NodeArcVO nodeArcVO : arcLevelsMap.get(level)) {
                if (nodeArcVO.nodeId.equals(nodeId)) {
                    return true;
                }
            }
        }
        return false;
    }
    //endregion

    /*//region generateLevelsOrder
    private void generateLevelsOrder(Long beginNode, Long netLevel) {
        List<NodeArcVO> nodeArcVOList = new ArrayList<>();

        if (elNet.nodeArcNeighboursListMap.containsKey(beginNode)) {

            if (maxNetLevel < netLevel) {
                maxNetLevel = netLevel;
            }

            for (Long arcId : elNet.nodeArcNeighboursListMap.get(beginNode)) {
                NodeArcVO nodeArcVO = new NodeArcVO();
                nodeArcVO.netLevel = netLevel;
                nodeArcVO.nodeId = beginNode;
                nodeArcVO.arcId = arcId;
                nodeArcVO.neighborNodeId = elNet.arcMap.get(arcId).getEndNodeId();
                nodeArcVOList.add(nodeArcVO);
            }

            if (arcLevelsMap.containsKey(netLevel)) {
                arcLevelsMap.get(netLevel).addAll(nodeArcVOList);
            } else {
                arcLevelsMap.put(netLevel, nodeArcVOList);
            }

            for (NodeArcVO nodeArcVO : nodeArcVOList) {
                if (!isNodeLevelExist(nodeArcVO.netLevel + 1, nodeArcVO.neighborNodeId)) {
                    generateLevelsOrder(nodeArcVO.neighborNodeId, nodeArcVO.netLevel + 1);
                }
            }

        }
    }
//endregion*/

    //region generate Levels Order
    private void generateLevelsOrder(Long beginNode, Long netLevel) {
        List<NodeArcVO> nodeArcVOList = new ArrayList<>();

        if (elNet.neighborsForwardMap.containsKey(beginNode)) {

            if (maxNetLevel < netLevel) {
                maxNetLevel = netLevel;
            }

            for (Long arcId : elNet.neighborsForwardMap.get(beginNode)) {
                NodeArcVO nodeArcVO = new NodeArcVO();
                nodeArcVO.netLevel = netLevel;
                nodeArcVO.nodeId = beginNode;
                nodeArcVO.arcId = arcId;
                nodeArcVO.neighborNodeId = elNet.arcMap.get(arcId).getEndNode();
                nodeArcVOList.add(nodeArcVO);
            }

            if (arcLevelsMap.containsKey(netLevel)) {
                arcLevelsMap.get(netLevel).addAll(nodeArcVOList);
            } else {
                arcLevelsMap.put(netLevel, nodeArcVOList);
            }

            for (NodeArcVO nodeArcVO : nodeArcVOList) {
                if (!isNodeLevelExist(nodeArcVO.netLevel + 1, nodeArcVO.neighborNodeId)) {
                    generateLevelsOrder(nodeArcVO.neighborNodeId, nodeArcVO.netLevel + 1
                    );
                }
            }

        }
    }

//endregion

    //region generate Levels Order
    public void generateLevelsOrder() {
        maxNetLevel = 0L;
        arcLevelsMap.clear();
        generateLevelsOrder(0L, 0L);
    }
    //endregion

}

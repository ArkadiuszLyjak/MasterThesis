package MasterThesis.bfs;

import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.NodeArcVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BfsAlgorithm {
    //Maksymalny poziom odwiedzin
    Long maxNetLevel ;

    //Mapa odwiedzin na poszczegolnych poziomach
    public Map<Long, List<NodeArcVO>> arcLevelsMap = new HashMap<>();

    private static BfsAlgorithm instance;
    private static ElectricalNetwork elNet;
    // Singlenton
    public static BfsAlgorithm getInstance(){
        if (instance == null ){
            instance = new BfsAlgorithm();
            elNet = ElectricalNetwork.getInstance();
        }
        return instance;
    }

    public Long getNetLevel() {
        return maxNetLevel;
    };

    private boolean isNodeLevelExist(Long level, Long nodeId){
        if (arcLevelsMap.containsKey(level)) {
            for (NodeArcVO nodeArcVO : arcLevelsMap.get(level)) {
                if (nodeArcVO.nodeId.equals(nodeId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void generateLevelsOrder(Long beginNode, Long netLevel ){
        List<NodeArcVO> nodeArcVOList = new ArrayList<>();
        if (elNet.nodeArcList_Map.containsKey(beginNode) ) {
            if (maxNetLevel < netLevel){
                maxNetLevel = netLevel;
            }
           for (Long arcId : elNet.nodeArcList_Map.get(beginNode)) {

               NodeArcVO nodeArcVO = new NodeArcVO();
               nodeArcVO.netLevel = netLevel;
               nodeArcVO.nodeId = beginNode;
               nodeArcVO.arcId = arcId;
               nodeArcVO.neighborNodeId = elNet.arcMap.get(arcId).getEndNodeId();
               nodeArcVOList.add(nodeArcVO);
           }

           if( arcLevelsMap.containsKey(netLevel)) {
               arcLevelsMap.get(netLevel).addAll(nodeArcVOList);
           }
           else{
               arcLevelsMap.put(netLevel,nodeArcVOList);
           }

           for (NodeArcVO nodeArcVO: nodeArcVOList) {
               if (!isNodeLevelExist(nodeArcVO.netLevel + 1 , nodeArcVO.neighborNodeId)) {
                   generateLevelsOrder(nodeArcVO.neighborNodeId,
                           nodeArcVO.netLevel + 1
                   );
               }
           }
        }
    }

    public void generateLevelsOrder(){
        maxNetLevel = 0L;
        arcLevelsMap.clear();
        generateLevelsOrder(0L, 0L );
    }

}

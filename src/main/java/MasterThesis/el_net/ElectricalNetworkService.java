package MasterThesis.el_net;

import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.node.NodeEntity;
import java.util.ArrayList;
import java.util.List;

public class ElectricalNetworkService {
    AppParametersService paramsService =  AppParametersService.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

   static ElectricalNetworkService instance;

   private ElectricalNetworkService() {
    }

    public static ElectricalNetworkService getInstance(){
       if (instance == null){
           instance = new ElectricalNetworkService();
       }
       return instance;
    }



    public void nodeArcListBuild(){
        elNet.arcMap.forEach((aLong, arcEntity) ->  {
            if (!elNet.nodeArcList_Map.containsKey(arcEntity.getStartNodeId())) {
                elNet.nodeArcList_Map.put(arcEntity.getStartNodeId(), new ArrayList<>());
            }
            elNet.nodeArcList_Map.get(arcEntity.getStartNodeId()).add(arcEntity.getId());
        });
    }


    public boolean isDistributeNode(NodeEntity node) {

        List<Long> distributeNodeList = new ArrayList<>();
        List<Long> arcList = elNet.nodeArcList_Map.get(0L);
        for (Long arcId : arcList ){
            distributeNodeList.add(elNet.arcMap.get(arcId).getEndNodeId());
        }

        for (Long nodeId : distributeNodeList){
            if (nodeId == node.getId()){
                return true;
            }
        }
        return false;
    }
}

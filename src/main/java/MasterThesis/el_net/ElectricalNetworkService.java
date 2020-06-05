package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcFactory;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.arc_file_tools.FileDataReader;
import MasterThesis.line_type.LineType;
import MasterThesis.line_type.LineTypeFactory;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import MasterThesis.transformer_type.TransformerTypeEntity;
import MasterThesis.transformer_type.TransformerTypeFactory;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import com.sun.org.apache.bcel.internal.generic.DRETURN;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        for (Long nodeId : elNet.getDistributeNodeIdList()){
            if (nodeId == node.getId()){
                return true;
            }
        }
        return false;
    }
}

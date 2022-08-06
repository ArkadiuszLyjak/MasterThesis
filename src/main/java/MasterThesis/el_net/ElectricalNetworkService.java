package MasterThesis.el_net;

import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.node.NodeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElectricalNetworkService {

    static ElectricalNetworkService instance;
    AppParametersService params_Service = AppParametersService.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    //region getInstance - Singleton
    private ElectricalNetworkService() {
    }

    public static ElectricalNetworkService getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkService();
        }
        return instance;
    }
    //endregion

    /**
     * <p>Metoda generuje listę sąsiadów dla każdego węzła (początkowego)
     * w postaci ID do odczytu z odpowiedniego katalogu wskazującego
     * unikalny nr węzła-sąsiada.<br></p>
     *
     * <p>neighbors map [(węzeł_początkowy), lista (ID_węzłów_sąsiadów)]</p>
     */

    //region Generate forward neighbors map
    public void nodeNeighborsFollowingListBuild() {

        elNet.arcMap.forEach((aLong, arcEntity) -> {

            long startNode = arcEntity.getStartNode();
            long id = arcEntity.getId();

//            System.out.printf("%d->%d%n", startNode, arcEntity.getEndNode());

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighbors__FORWARD__Map.containsKey(startNode)) {
                elNet.neighbors__FORWARD__Map.put(startNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighbors__FORWARD__Map.get(startNode).add(id); // dodaje ID sąsiada

        });

    }
    //endregion

    //region Generate reverse neighbors map
    public void nodeNeighbors__REVERSE__ListBuild() {
        elNet.arcMap.forEach((aLong, arcEntity) -> {

            long endNode = arcEntity.getEndNode(); // start z końcowego węzła
            long id = arcEntity.getId();

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighbors__REVERSE__Map.containsKey(endNode)) {
                elNet.neighbors__REVERSE__Map.put(endNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighbors__REVERSE__Map.get(endNode).add(id);
        });

    }
    //endregion

    //region isDistributeNode
    public boolean isDistributeNode(NodeEntity node) {

        List<Long> distributeNodeList = new ArrayList<>();
        List<Long> arcList = elNet.neighbors__FORWARD__Map.get(0L);

        for (Long arcId : arcList) {
            distributeNodeList.add(elNet.arcMap.get(arcId).getEndNode());
        }

        for (Long nodeId : distributeNodeList) {
            if (Objects.equals(nodeId, node.getId())) {
                return true;
            }
        }
        return false;
    }
    //endregion
}

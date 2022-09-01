package MasterThesis.el_net;

import MasterThesis.base.entity.BaseEntity;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.node.NodeEntity;

import java.util.*;
import java.util.stream.Collectors;

public class ElectricalNetworkService {

    //region getInstance - Singleton
    static ElectricalNetworkService instance;
    AppParametersService appParametersService = AppParametersService.getInstance();
    ElectricalNetwork electricalNetwork = ElectricalNetwork.getInstance();

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
    public void nodeNeighborsForwardListBuild() {

        electricalNetwork.arcMap.forEach((arcID, arcEntity) -> {

            if (arcEntity.getCondition() == 1) {
                long startNode = arcEntity.getStartNode();

                // StartNode - węzeł początkowy (nie ID!)
                if (!electricalNetwork.neighborsForwardMap.containsKey(startNode)) {
                    electricalNetwork.neighborsForwardMap.put(startNode, new ArrayList<>());
                }

                // dodaje ID całego rekordu, gdzie znajduje się sąsiad
                electricalNetwork.neighborsForwardMap.get(startNode).add(arcID); // dodaje ID sąsiada
            }

               /* long startNode = arcEntity.getStartNode();

                // StartNode - węzeł początkowy (nie ID!)
                if (!electricalNetwork.neighborsForwardMap.containsKey(startNode)) {
                    electricalNetwork.neighborsForwardMap.put(startNode, new ArrayList<>());
                }

                // dodaje ID całego rekordu, gdzie znajduje się sąsiad
                electricalNetwork.neighborsForwardMap.get(startNode).add(arcID); // dodaje ID sąsiada*/

        });

    }
    //endregion

    //region Generate reverse neighbors map
    public void nodeNeighborsReverseListBuild() {
        electricalNetwork.arcMap.forEach((arcID, arcEntity) -> {

            /*//region Description 1
            long endNode = arcEntity.getEndNode(); // start z końcowego węzła

            // StartNode - węzeł początkowy (nie ID!)
            if (!electricalNetwork.neighborsReverseMap.containsKey(endNode)) {
                electricalNetwork.neighborsReverseMap.put(endNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            electricalNetwork.neighborsReverseMap.get(endNode).add(arcID);
            //endregion*/

            //region Description 2
            if (electricalNetwork.arcMap.get(arcID).getCondition() == 1) {
    long endNode = arcEntity.getEndNode(); // start z końcowego węzła

    // StartNode - węzeł początkowy (nie ID!)
                if(!electricalNetwork.neighborsReverseMap.containsKey(endNode))

    {
        electricalNetwork.neighborsReverseMap.put(endNode, new ArrayList<>());
    }

    // dodaje ID całego rekordu, gdzie znajduje się sąsiad
                electricalNetwork.neighborsReverseMap.get(endNode).

    add(arcID);
}
//endregion
        });
                }
//endregion

    //region isDistributeNode
    public boolean isDistributeNode(NodeEntity node) {

        List<Long> distributeNodeList = new ArrayList<>();
        List<Long> arcList = electricalNetwork.neighborsForwardMap.get(0L);

        for (Long arcId : arcList) {
            distributeNodeList.add(electricalNetwork.arcMap.get(arcId).getEndNode());
        }

        for (Long nodeId : distributeNodeList) {
            if (Objects.equals(nodeId, node.getId())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region self conductance of the node
    // quantities necessary for the main algorithm
    public void calcNodeSelfCond() {

        //region Tworzy mapę węzłów i ich wszystkich sąsiadów z przodu i z tyłu.
        electricalNetwork.arcMap.forEach((arcID, arcEntity) -> {

            long startNode = arcEntity.getStartNode();
            long endNode = arcEntity.getEndNode();
            boolean containsStartKey = electricalNetwork.nodesNeighborsForwardReverseMap.containsKey(startNode);
            boolean containsEndKey = electricalNetwork.nodesNeighborsForwardReverseMap.containsKey(endNode);

            if (!containsStartKey) {
                electricalNetwork.nodesNeighborsForwardReverseMap.put(startNode, new ArrayList<>());
            } else {
//                System.out.println("Mapa zawiera: " + startNode);
            }

            if (!containsEndKey) {
                electricalNetwork.nodesNeighborsForwardReverseMap.put(endNode, new ArrayList<>());
            } else {
//                System.out.println("Mapa zawiera: " + endNode);
            }

        });
        //endregion

        //region print neighbors forward map
        // along - unikalny nr węzła // longs - ID sąsiadów
        electricalNetwork.neighborsForwardMap.forEach((uniqueStartNode, forwardNeighborIDsList) ->

        {
            if (electricalNetwork.nodesNeighborsForwardReverseMap.containsKey(uniqueStartNode)) {
                electricalNetwork.nodesNeighborsForwardReverseMap.get(uniqueStartNode).addAll(forwardNeighborIDsList);
            } else {
//                System.out.println("Mapa nie zawiera klucza: " + uniqueStartNode);
            }
        });
        //endregion

        //region print neighbors reverse map
        // along - unikalny nr węzła // longs - ID sąsiadów
        electricalNetwork.neighborsReverseMap.forEach((uniqueEndNode, reverseNeighborsIDlist) ->

        {
            if (electricalNetwork.nodesNeighborsForwardReverseMap.containsKey(uniqueEndNode)) {
                electricalNetwork.nodesNeighborsForwardReverseMap.get(uniqueEndNode).addAll(reverseNeighborsIDlist);
            }
        });
        //endregion

        //region calculate selfConductance for every node
        electricalNetwork.nodeMap.forEach((uniqueNodeNum, nodeEntity) ->

        {
            double selfConductance = 0;

            if (electricalNetwork.nodesNeighborsForwardReverseMap.containsKey(uniqueNodeNum)) {
                try {
                    for (Long IDnbr : electricalNetwork.nodesNeighborsForwardReverseMap.get(uniqueNodeNum)) {
                        double calcSelfConductance = 1 / electricalNetwork.arcMap.get(IDnbr).getResistancePU();
                        if (!(calcSelfConductance == Double.POSITIVE_INFINITY)) {
                            selfConductance = selfConductance + calcSelfConductance;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Dzielenie przez zero!");
                }
                nodeEntity.setSelfConductancePU(selfConductance);
            }

        });
        //endregion
    }
    //endregion

    //region generate list of nodes with no neighbors in front of them
    public void createNoNeighborsNodesList() {
        // list of all available nodes
        List<Long> listOfAllNodesAvailable = electricalNetwork.nodeList
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        // set of neighbors forward map
        Set<Map.Entry<Long, List<Long>>> setOfNeighborsForwardMap =
                electricalNetwork.neighborsForwardMap.entrySet();

        // set only keys
        Set<Long> ids = electricalNetwork.neighborsForwardMap.keySet();

        // values from forward neighbors map
        Collection<List<Long>> values = electricalNetwork.neighborsForwardMap.values();

        // build list of nodes with no neighbors in front
        listOfAllNodesAvailable.forEach(aLong -> {
            if (!ids.contains(aLong)) electricalNetwork.nodesWithNoNeighborsInFront.add(aLong);
        });

    }
    //endregion

}

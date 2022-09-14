package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.base.entity.BaseEntity;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.node.NodeEntity;

import java.util.*;
import java.util.stream.Collectors;

public class ElectricalNetworkService {

    //region getInstance - Singleton
    static ElectricalNetworkService instance;
    AppParametersService appParametersService = AppParametersService.getInstance();
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

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

        elNet.arcMap.forEach((arcID, arcEntity) -> {

//            if (arcEntity.getCondition() == 1) {
            long startNode = arcEntity.getStartNode();

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighborsForwardMap.containsKey(startNode)) {
                elNet.neighborsForwardMap.put(startNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighborsForwardMap.get(startNode).add(arcID); // dodaje ID sąsiada
//                }

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
        elNet.arcMap.forEach((arcID, arcEntity) -> {

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
//                    if (electricalNetwork.arcMap.get(arcID).getCondition() == 1) {
            long endNode = arcEntity.getEndNode(); // start z końcowego węzła

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighborsReverseMap.containsKey(endNode)) {
                elNet.neighborsReverseMap.put(endNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighborsReverseMap.get(endNode).

                    add(arcID);
//            }
//endregion
        });
    }
//endregion

    //region isDistributeNode
    public boolean isDistributeNode(NodeEntity node) {

        List<Long> distributeNodeList = new ArrayList<>();
        List<Long> arcList = elNet.neighborsForwardMap.get(0L);

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

    //region self conductance of the node
    // quantities necessary for the main algorithm
    public void calcNodeSelfCond() {

        //region Tworzy mapę węzłów i ich wszystkich sąsiadów z przodu i z tyłu.
        elNet.arcMap.forEach((arcID, arcEntity) -> {

            long startNode = arcEntity.getStartNode();
            long endNode = arcEntity.getEndNode();
            boolean containsStartKey = elNet.nodesNeighborsForwardReverseMap.containsKey(startNode);
            boolean containsEndKey = elNet.nodesNeighborsForwardReverseMap.containsKey(endNode);

            if (!containsStartKey) {
                elNet.nodesNeighborsForwardReverseMap.put(startNode, new ArrayList<>());
            } else {
//                System.out.println("Mapa zawiera: " + startNode);
            }

            if (!containsEndKey) {
                elNet.nodesNeighborsForwardReverseMap.put(endNode, new ArrayList<>());
            } else {
//                System.out.println("Mapa zawiera: " + endNode);
            }

        });
        //endregion

        //region print neighbors forward map
        // along - unikalny nr węzła // longs - ID sąsiadów
        elNet.neighborsForwardMap.forEach((uniqueStartNode, forwardNeighborIDsList) ->

        {
            if (elNet.nodesNeighborsForwardReverseMap.containsKey(uniqueStartNode)) {
                elNet.nodesNeighborsForwardReverseMap.get(uniqueStartNode).addAll(forwardNeighborIDsList);
            } else {
//                System.out.println("Mapa nie zawiera klucza: " + uniqueStartNode);
            }
        });
        //endregion

        //region print neighbors reverse map
        // along - unikalny nr węzła // longs - ID sąsiadów
        elNet.neighborsReverseMap.forEach((uniqueEndNode, reverseNeighborsIDlist) ->

        {
            if (elNet.nodesNeighborsForwardReverseMap.containsKey(uniqueEndNode)) {
                elNet.nodesNeighborsForwardReverseMap.get(uniqueEndNode).addAll(reverseNeighborsIDlist);
            }
        });
        //endregion

        //region calculate selfConductance for every node
        elNet.nodeMap.forEach((uniqueNodeNum, nodeEntity) ->

        {
            double selfConductance = 0;

            if (elNet.nodesNeighborsForwardReverseMap.containsKey(uniqueNodeNum)) {
                try {
                    for (Long IDnbr : elNet.nodesNeighborsForwardReverseMap.get(uniqueNodeNum)) {
                        double calcSelfConductance = 1 / elNet.arcMap.get(IDnbr).getResistancePU();
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
    public void createNoFrontNeighborsNodesList() {
        // list of all available nodes
        List<Long> listOfAllNodesAvailable = elNet.nodeList
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        // set of neighbors forward map
        Set<Map.Entry<Long, List<Long>>> setOfNeighborsForwardMap =
                elNet.neighborsForwardMap.entrySet();

        // set only keys
        Set<Long> ids = elNet.neighborsForwardMap.keySet();

        // values from forward neighbors map
        Collection<List<Long>> values = elNet.neighborsForwardMap.values();

        // build list of nodes with no neighbors in front
        listOfAllNodesAvailable.forEach(aLong -> {
            if (!ids.contains(aLong)) elNet.nodesWithNoNeighborsInFront.add(aLong);
        });

    }
    //endregion

    //region create no back neighbors nodes list
    public void createNoBackNeighborsNodesList() {

        // list of all available nodes
        Set<Map.Entry<Long, List<Long>>> entrySet = elNet.neighborsReverseMap.entrySet();

        entrySet.forEach(longListEntry -> {
            long key = longListEntry.getKey();
            List<Long> value = longListEntry.getValue();
            for (Long nodeID : value) {
                ArcEntity arcEntity = elNet.arcMap.get(nodeID);
                long startNode = arcEntity.getStartNode();
                if (startNode == 0) {
                    elNet.nodesWithNoNeighborsBackList.add(arcEntity.getEndNode());
                }
            }
        });

    }
    //endregion

}

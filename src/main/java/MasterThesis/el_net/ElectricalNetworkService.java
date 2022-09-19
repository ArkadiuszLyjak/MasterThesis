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
            long startNode = arcEntity.getStartNode();

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighborsForwardMap.containsKey(startNode)) {
                elNet.neighborsForwardMap.put(startNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighborsForwardMap.get(startNode).add(arcID); // dodaje ID sąsiada
        });

    }
    //endregion

    //region Generate reverse neighbors map
    public void nodeNeighborsReverseListBuild() {
        elNet.arcMap.forEach((arcID, arcEntity) -> {

            //region Description 2
            long endNode = arcEntity.getEndNode(); // start z końcowego węzła

            // StartNode - węzeł początkowy (nie ID!)
            if (!elNet.neighborsReverseMap.containsKey(endNode)) {
                elNet.neighborsReverseMap.put(endNode, new ArrayList<>());
            }

            // dodaje ID całego rekordu, gdzie znajduje się sąsiad
            elNet.neighborsReverseMap.get(endNode).add(arcID);
            //endregion
        });
    }
    //endregion

    //region Generate forward reverse neighbors map
    public void nodeNeighborsForwardReverseListBuild() {

        elNet.nodeList.forEach(nodeEntity -> {

            Long node = nodeEntity.getId();

            if (!elNet.nodesNeighborsForwardReverseMap.containsKey(node)) {
                elNet.nodesNeighborsForwardReverseMap.put(node, new ArrayList<>());
            }

            if (elNet.neighborsForwardMap.containsKey(node))
                elNet.nodesNeighborsForwardReverseMap.get(node)
                        .addAll(elNet.neighborsForwardMap.get(node));

            if (elNet.neighborsReverseMap.containsKey(node))
                elNet.nodesNeighborsForwardReverseMap.get(node)
                        .addAll(elNet.neighborsReverseMap.get(node));

        });

//        Set<Map.Entry<Long, ArrayList<Long>>> entrySet = elNet.nodesNeighborsForwardReverseMap.entrySet();
//        Iterator<Map.Entry<Long, ArrayList<Long>>> entryIterator = entrySet.iterator();
//        while (entryIterator.hasNext()) {
//            System.out.println(entryIterator.next());
//        }

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

        //region calculate selfConductance for every node
        elNet.nodeMap.forEach((uniqueNodeNum, nodeEntity) -> {
            double selfConductance = 0;

            if (elNet.nodesNeighborsForwardReverseMap.containsKey(uniqueNodeNum)) {
                try {
                    for (Long IDnbr : elNet.nodesNeighborsForwardReverseMap.get(uniqueNodeNum)) {
                        double calcSelfConductance = 1 / elNet.arcMap.get(IDnbr).getResistancePU();

                        System.out.printf("%d %3d G:%f R:%(.6f\n",
                                uniqueNodeNum, IDnbr, calcSelfConductance, elNet.arcMap.get(IDnbr).getResistancePU());

                        if (!(calcSelfConductance == Double.POSITIVE_INFINITY)) {
                            selfConductance = selfConductance + calcSelfConductance;
                        }

                    }
                } catch (Exception e) {
                    System.out.println("Dzielenie przez zero!");
                }
                nodeEntity.setSelfConductancePU(selfConductance);

                System.out.printf("%3d Gii:%(.6f\n", uniqueNodeNum, selfConductance);
                System.out.println();
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

        // set only keys
        Set<Long> ids = elNet.neighborsForwardMap.keySet();

        // build list of nodes with no neighbors in front
        listOfAllNodesAvailable.forEach(aLong -> {
            if (!ids.contains(aLong))
                elNet.nodesWithNoNeighborsInFrontList.add(aLong);
        });

    }
    //endregion

    //region create no back neighbors nodes list
    public void createNoBackNeighborsNodesList() {

        // list of all available nodes
        Set<Map.Entry<Long, List<Long>>> entrySet = elNet.neighborsReverseMap.entrySet();

        entrySet.forEach(longListEntry -> {
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

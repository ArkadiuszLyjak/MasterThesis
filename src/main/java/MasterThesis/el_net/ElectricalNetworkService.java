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

    //region isEmpty
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
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
    public void nodeNeighborsForwardMapBuild() {

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
    public void nodeNeighborsReverseMapBuild() {
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

        elNet.nodeEntityList.forEach(nodeEntity -> {

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

//                        System.out.printf("%d %3d G:%f R:%(.6f\n",
//                        uniqueNodeNum, IDnbr, calcSelfConductance, elNet.arcMap.get(IDnbr).getResistancePU());

                        if (!(calcSelfConductance == Double.POSITIVE_INFINITY)) {
                            selfConductance = selfConductance + calcSelfConductance;
                        }

                    }
                } catch (ArithmeticException ae) {
                    System.out.println(ae.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                nodeEntity.setSelfConductancePU(selfConductance);

//                System.out.printf("%3d Gii:%(.6f\n", uniqueNodeNum, selfConductance);
//                System.out.println();
            }

        });
        //endregion
    }
    //endregion

    //region generate list of nodes with no neighbors in front of them
    public void createNoFrontNeighborsNodesList() {
        // list of all available nodes
        List<Long> listOfAllNodesAvailable = elNet.nodeEntityList
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        // set only keys
        Set<Long> ids = elNet.neighborsForwardMap.keySet();
//        Set<Long> ids = elNet.nodesNeighborsForwardReverseMap.keySet();

        // build list of nodes with no neighbors in front
        listOfAllNodesAvailable.forEach(aLong -> {
            if (!ids.contains(aLong))
                elNet.nodesWithNoNeighborsInFrontList.add(aLong);
        });

    }
    //endregion

    //region create no back neighbors nodes list
    public void createNoBackNeighborsNodesList() {

        /*Lista węzłów*/
        List<Long> nodeList = elNet.nodeEntityList.stream()
                .mapToLong(NodeEntity::getId)
                .boxed()
                .collect(Collectors.toList());

//        for (Long aLong : nodeList) System.out.println(aLong);
//        for (Map.Entry<Long, List<Long>> entry : elNet.neighborsReverseMap.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//        }

        nodeList.removeAll(elNet.neighborsReverseMap.keySet());
        elNet.nodesWithNoNeighborsBackList.addAll(nodeList);

//        if (!isEmpty(elNet.nodesWithNoNeighborsBackList)) {
//            for (Long aLong : elNet.nodesWithNoNeighborsBackList) {
//                System.out.println("> " + aLong);
//            }
//        }

    }

//endregion
}

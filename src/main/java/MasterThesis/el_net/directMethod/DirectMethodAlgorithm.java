package MasterThesis.el_net.directMethod;

import MasterThesis.data_calc.BaseValues;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;

import java.util.Formatter;
import java.util.List;

import static MasterThesis.data_calc.BaseValues.voltageBase;

public class DirectMethodAlgorithm {

    private static DirectMethodAlgorithm instance;

    //region Singleton
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    private DirectMethodAlgorithm() {
    }

    public static DirectMethodAlgorithm getInstance() {
        if (instance == null) {
            instance = new DirectMethodAlgorithm();
        }

        return instance;
    }
    //endregion

    //region direct method calculation
    public void calculateDirectMethod(int nodes, int powerNodes) {
        long iterateMax = 0;

        //region NODE POWER TRANSMIT
        System.out.println("\n-------------------------------------------------------");
        System.out.println("------------------ NODE POWER TRANSMIT ----------------");
        System.out.println("-------------------------------------------------------");

        int a; // error indicator
        double deltaCurrentThisIterPresentNode; // bieżąca wart. poprawki prądu
        //endregion

        //region do loop
        do {

            a = 0;

            if (iterateMax == 100) break; // safety "valve"
            iterateMax++;

            double currentPU;

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {

                IterateMapBuilder iterateMapBuilder = new IterateMapBuilder(iterateMax);

                //region for NodeType.OTHER_NODE
                if (node.getNodeType() == NodeType.OTHER_NODE) {
                    //region nodes > powerNodes
                    if (nodes > powerNodes) {
                        long nodeNumber = node.getId();
                        iterateMapBuilder.nodeNumber(nodeNumber);

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        currentPU = node.getCurrentPU();
                        iterateMapBuilder.currentPU(currentPU);

                        double u_i = node.getVoltagePU();              // napięcie w wężle liczonym
                        iterateMapBuilder.u_i(u_i);

                        double g_ii = node.getSelfConductancePU();     // konduktancja własna węzła
                        iterateMapBuilder.g_ii(g_ii);

                        // napięcia w węzłach-sąsiadach dla iter. poprzedniej
                        // konduktancja między liczonym węzłem a jego sąsiadem
                        double item_Ii = currentPU;
                        iterateMapBuilder.item_Ii(item_Ii);

                        double item_Ui_Gii = u_i * g_ii;
                        iterateMapBuilder.item_Ui_Gii(item_Ui_Gii);

                        //region item_sum = sum Uj * G_ij
                        double item_sum = 0;
                        List<Long> neighborsIDsList = elNet.neighborsForwardMap.get(nodeNumber);

                        if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {
                            for (Long neighborFWDid : neighborsIDsList) {

                                try {

                                    long nodeUniqueNeighborFWD = elNet.arcMap.get(neighborFWDid).getEndNode();
                                    NeighborEntityBuilder neighborEntityBuilder =
                                            new NeighborEntityBuilder(nodeUniqueNeighborFWD);

                                    double u_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
                                    neighborEntityBuilder.u_j(u_j);

                                    double g_ij = 1 / elNet.arcMap.get(neighborFWDid).getResistancePU();
                                    neighborEntityBuilder.g_ij(g_ij);

                                    item_sum += u_j * g_ij;
                                    neighborEntityBuilder.item_sum(item_sum);

                                    NeighborEntity neighborEntity = neighborEntityBuilder.build();
                                    iterateMapBuilder.neighborMapCalc(neighborEntity);

                                } catch (NullPointerException e) {
                                    System.out.println("pusta lista!");
                                    e.printStackTrace();
                                }
                            }
                        }
                        //endregion

                        double item_sqrt = Math.sqrt(3.00); // ok, 1,7320508075688772935274463415059
                        deltaCurrentThisIterPresentNode = item_Ii - (item_Ui_Gii - item_sum) / item_sqrt;
                        iterateMapBuilder.deltaCurrentThisIterPresentNode(deltaCurrentThisIterPresentNode);

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        // j.w poprawka prądu wyliczona powyżej
                        // konduktancja własna węzła
                        double deltaVoltageThisIterPresentNode = (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;
                        iterateMapBuilder.deltaVoltageThisIterPresentNode(deltaVoltageThisIterPresentNode);
                        //endregion

                        //region napięcie w węźle dla obecnej iteracji i węzła
                        // napięcie w węźle w poprzedniej iteracji
                        // zmiana napięcia wyliczona powyżej - deltaVoltageThisIterPresentNode
                        double voltageThisIterPresentNode = u_i + deltaVoltageThisIterPresentNode;
                        iterateMapBuilder.voltageThisIterPresentNode(voltageThisIterPresentNode);

                        elNet.nodeMap.get(nodeNumber).setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltageReal(voltageThisIterPresentNode * voltageBase);
                        node.setCurrentReal(item_Ii * BaseValues.currentBase);
                        //endregion

                        //region error settings
                        double checkErrorParam = deltaCurrentThisIterPresentNode < 0 ?
                                -deltaCurrentThisIterPresentNode : deltaCurrentThisIterPresentNode;

                        if (a == 0) {
                            if (checkErrorParam >= BaseValues.epsilon) {
                                a = 1;
                            }
                        }

                        iterateMapBuilder.errorIndicator(a);
                        //endregion

                        //region utw. instancji klasy obl. pośrednich
                        InterimEntity interimEntity = iterateMapBuilder.build();
                        elNet.interimNodeMap.put(nodeNumber, interimEntity);
                        //endregion

                    } else {
                        System.out.println("algorytm LEWY - 5A");
                    }

                    //endregion
                }
                //endregion

            }
            //endregion

            elNet.mapIterate.put(iterateMax, elNet.interimNodeMap);

        } while (a == 1);
        //endregion
    }
    //endregion

    //region Active power flow
    public void activePowerFlow() {
        double u_n = voltageBase;

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        elNet.neighborsForwardMap.forEach((node, nodeNeighborsIDs) -> {

            if (node > 0) {
                double u_i = elNet.nodeMap.get(node).getVoltagePU();

                for (Long nodeID : nodeNeighborsIDs) {
//                    formatter.format("Ui:%-(4.4f  ", u_i);

                    double u_j = elNet.nodeMap.get(elNet.arcMap.get(nodeID).getEndNode()).getVoltagePU();

//                    formatter.format("Uj:%-(4.4f  ", u_j); // Uj

                    double g_ij = 1 / elNet.arcMap.get(nodeID).getResistancePU();

//                    formatter.format("Gij:%-(4.2f ", g_ij); // Gij

                    if (g_ij != Double.POSITIVE_INFINITY && g_ij != 0) {
                        double pwr_ij = u_n * (u_i - u_j) * g_ij;
                        elNet.arcMap.get(nodeID).setActivePowerFlowPU(pwr_ij);

                        formatter.format("Ui-Uj:%-(8.4f  ", u_i - u_j);         // Ui - Uj
                        formatter.format("%3d-->%3d %(.4f  ",                   // start-->end flow
                                node,
                                elNet.arcMap.get(nodeID).getEndNode(),
                                elNet.arcMap.get(nodeID).getActivePowerFlowPU());

                    }

                    if (u_i - u_j < 0) {
                        System.out.println(formatter);
                    }

//                    System.out.println(formatter);

                    stringBuilder.setLength(0);

                }
            }

        });

    }
    //endregion
}

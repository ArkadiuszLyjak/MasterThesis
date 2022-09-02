package MasterThesis.el_net;

import MasterThesis.data_calc.BaseValues;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;

import java.util.Formatter;
import java.util.List;

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
    public void calculateDirectMethod(int nodes, int powerNodes, long iterateMax) {
        //region NODE POWER TRANSMIT
        System.out.println("\n-------------------------------------------------------");
        System.out.println("------------------ NODE POWER TRANSMIT ----------------");
        System.out.println("-------------------------------------------------------");

        int a; // error indicator
        double deltaCurrentThisIterPresentNode; // bieżąca wart. poprawki prądu
        //endregion

        //region do loop
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        do {
            a = 0;
            if (iterateMax == 100) break; // safety "valve"
            iterateMax++;
            formatter.format("\nk:%d - pętla do-while\n\n", iterateMax);

            int loopCounter = 0; // zmienna pomocnicza
            double currentPU = 0;

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {

                if (node.getNodeType() == NodeType.OTHER_NODE) {
                    long nodeNumber = node.getId();

//                    formatter.format("i:%3d ", loopCounter++);

                    if (nodes > powerNodes) {
                        formatter.format("n:%3d  ", nodeNumber);

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        currentPU = node.getCurrentPU();
                        double v_i = node.getVoltagePU();              // napięcie w wężle liczonym
                        double g_ii = node.getSelfConductancePU();     // konduktancja własna węzła

                        // napięcia w węzłach-sąsiadach dla iter. poprzedniej
                        // konduktancja między liczonym węzłem a jego sąsiadem
                        double item_Ii = currentPU;
                        formatter.format("I:%-(6.4f  ", item_Ii);

                        double item_Ui_Gii = v_i * g_ii;
//                        formatter.format("Ui*Gii:%-(6.2e ", item_Ui_Gii);

                        double item_sum = 0;

                        List<Long> neighborsIDsList = elNet.neighborsForwardMap.get(nodeNumber);

                        if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {
                            for (Long neighborFWDid : neighborsIDsList) {
                                try {
                                    long nodeUniqueNeighborFWD = elNet.arcMap.get(neighborFWDid).getEndNode();
                                    double v_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
                                    double g_ij = 1 / elNet.arcMap.get(neighborFWDid).getResistancePU();
                                    item_sum += v_j * g_ij;
                                } catch (NullPointerException e) {
                                    System.out.println("pusta lista!");
                                    e.printStackTrace();
                                }
                            }
                        }

                        formatter.format("∑%-(6.4f  ", item_sum);

                        double item_sqrt = Math.sqrt(3.00);

                        deltaCurrentThisIterPresentNode = item_Ii - ((item_Ui_Gii - item_sum) / item_sqrt);  //
                        formatter.format("ΔI:%-(10.2e  ", deltaCurrentThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        // j.w poprawka prądu wyliczona powyżej
                        // konduktancja własna węzła
                        double deltaVoltageThisIterPresentNode =
                                (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;
                        //endregion

                        //region napięcie w węźle dla obecnej iteracji i węzła
                        // napięcie w węźle w poprzedniej iteracji
                        // zmiana napięcia wyliczona powyżej - deltaVoltageThisIterPresentNode
                        double voltageThisIterPresentNode = v_i + deltaVoltageThisIterPresentNode;
                        elNet.nodeMap.get(nodeNumber).setVoltagePU(voltageThisIterPresentNode);
                        formatter.format("V:%-(6.4f  ", voltageThisIterPresentNode);

                        node.setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltageReal(voltageThisIterPresentNode * BaseValues.voltageBase);
                        node.setCurrentReal(item_Ii * BaseValues.currentBase);
                        //endregion

                        //region error settings
                        double checkErrorParam = deltaCurrentThisIterPresentNode < 0 ?
                                -deltaCurrentThisIterPresentNode : deltaCurrentThisIterPresentNode;

//                        formatter.format("a:%d", a);

                        if (a == 0) {
                            if (checkErrorParam >= BaseValues.epsilon) {
                                a = 1;
                            }
                        }
                        //endregion

                        System.out.println(sb);
                        sb.setLength(0);

                    } else {
                        System.out.println("algorytm LEWY - 5A");
                    }
                }
            }
            //endregion

        } while (a == 1);
        //endregion
    }
    //endregion

    //region Active power flow
    public void activePowerFlow() {
        double u_n = BaseValues.voltageBase;

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        elNet.neighborsForwardMap.forEach((node, nodeNeighborsIDs) -> {

            if (node > 0) {
                double u_i = elNet.nodeMap.get(node).getVoltagePU();

                for (Long nodeID : nodeNeighborsIDs) {
                    formatter.format("Ui:%-(4.4f  ", u_i);

                    double u_j = elNet.nodeMap.get(elNet.arcMap.get(nodeID).getEndNode()).getVoltagePU();

                    formatter.format("Uj:%-(4.4f  ", u_j); // Uj

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

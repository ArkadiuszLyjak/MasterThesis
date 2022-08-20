package MasterThesis.el_net;

import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;

import java.util.Formatter;
import java.util.List;

public class DirectMethodAlgorithm {

    private static DirectMethodAlgorithm instance;
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    private DirectMethodAlgorithm() {
    }

    public static DirectMethodAlgorithm getInstance() {
        if (instance == null) {
            instance = new DirectMethodAlgorithm();
        }

        return instance;
    }

    public void calculateDirectMethod(int nodes, int powerNodes, long iterateMax) {
        //region NODE POWER TRANSMIT
        System.out.println("\n-------------------------------------------------------");
        System.out.println("------------------ NODE POWER TRANSMIT ----------------");
        System.out.println("-------------------------------------------------------");

        // number to iterate nodes
        int a = 0; // error indicator

        double deltaCurrentThisIterPresentNode;
        double eps = 0.0000000000000000001;
        //endregion

        //region do loop
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        do {
            a = 0;
            if (iterateMax == 1000) break; // safety valve
            iterateMax++;
            formatter.format("\nk:%d - pętla do-while\n\n", iterateMax);

            int loopCounter = 0; // zmienna pomocnicza
            double currentPU = 0;

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {

                if (node.getNodeType() == NodeType.OTHER_NODE) {
                    long nodeNumber = node.getId();

                    formatter.format("i:%3d ", loopCounter++);

                    if (nodes > powerNodes) {
                        formatter.format("n:%3d ", nodeNumber);

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        currentPU = node.getCurrentPU();
                        double v_i = node.getVoltagePU();                   // napięcie w wężle liczonym
                        double g_ii = node.getSelfConductance();            // konduktancja własna węzła

                        // napięcia w węzłach-sąsiadach dla iter. poprzedniej
                        // konduktancja między liczonym węzłem a jego sąsiadem
                        double item_Ii = currentPU;
                        formatter.format("I:%.2e ", item_Ii);

                        double item_Ui_Gii = v_i * g_ii;
//                        formatter.format("Ui*Gii:%.2e ", item_Ui_Gii);

                        double item_sum = 0;

                        List<Long> neighborsIDsList = elNet.nbrsFWDmap.get(nodeNumber);

                        if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {
                            for (Long neighborFWDid : neighborsIDsList) {
                                try {
                                    long nodeUniqueNeighborFWD = elNet.arcMap.get(neighborFWDid).getEndNode();
                                    double v_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
                                    double g_ij = 1 / elNet.arcMap.get(neighborFWDid).getResistancePU();
                                    item_sum += v_j * g_ij;
                                } catch (NullPointerException e) {
                                    System.out.println("pusty zbiór!");
                                    e.printStackTrace();
                                }
                            }
                        }

                        formatter.format("∑ %.2e ", item_sum);

                        double item_sqrt = Math.sqrt(3.00);

                        deltaCurrentThisIterPresentNode = item_Ii - ((item_Ui_Gii - item_sum) / item_sqrt);  //
                        formatter.format("ΔI:%+.2e ", deltaCurrentThisIterPresentNode);
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
                        //endregion

                        double checkErrorParam = deltaCurrentThisIterPresentNode < 0 ?
                                -deltaCurrentThisIterPresentNode : deltaCurrentThisIterPresentNode;

                        //region error settings
                        formatter.format("a:%d", a);

                        if (a == 0) {
                            if (checkErrorParam >= eps) {
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
}

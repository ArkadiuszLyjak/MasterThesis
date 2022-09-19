package MasterThesis.el_net.directMethod;

import MasterThesis.data_calc.BaseValues;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;

import java.io.IOException;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public void calculateDirectMethod() {

        long iterateMax = 0;

        System.out.println("\n-------------------------------------------------------");
        System.out.println("------------------ NODE POWER TRANSMIT ----------------");
        System.out.println("-------------------------------------------------------");

        int a; // error indicator

        //region do loop
        do {

            a = 0;

            if (iterateMax == 10) break; // safety "valve"
            iterateMax++;

            System.out.printf("\n\n----------- iterate: %d -----------\n\n\n", iterateMax);

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {
                System.out.println();

                //region data for every node
                long nodeNumber = node.getId();
                double deltaCurrentThisIterPresentNode; // bieżąca wart. poprawki prądu
                double deltaVoltageThisIterPresentNode; // bieżąca wart. poprawki prądu
                double voltageThisIterPresentNode;      // bieżąca wart. napięcia w liczonym węźle
                double currentThisIterPresentNode;      // bieżąca wart. prądu w liczonym węźle

                double item_sqrt = Math.sqrt(3.00); // ok, 1,7320508075688772935274463415059

                //region pobranie prądu początkowego
                if (iterateMax == 1) {
                    currentThisIterPresentNode = node.getCurrentInitialPU();
                } else {
                    currentThisIterPresentNode = node.getCurrentPU();
                }
                //endregion

                double u_i = node.getVoltagePU();               // napięcie w wężle liczonym
                double g_ii = node.getSelfConductancePU();      // konduktancja własna węzła
                double item_Ui_Gii = u_i * g_ii;
                double item_Ii = currentThisIterPresentNode;
                double item_sum = 0;

                List<Long> neighborsIDsList = elNet.neighborsForwardMap.get(nodeNumber);

                System.out.println("--------------- START -------------------");
                //region obliczenie sumy "Uj * Gij" po sąsiadach
                if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {

                    for (Long neighborFWDid : neighborsIDsList) {

                        try {

                            long nodeUniqueNeighborFWD = elNet.arcMap.get(neighborFWDid).getEndNode();
                            System.out.printf("\t%3d - neighbor\n", nodeUniqueNeighborFWD);

                            double u_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
                            System.out.printf("\t%-10s %(.2e\n", "Uj", u_j);

                            double g_ij = 1 / elNet.arcMap.get(neighborFWDid).getResistancePU();
                            System.out.printf("\t%-10s %(.2e\n", "Gij", g_ij);

                            item_sum += u_j * g_ij;
                            System.out.printf("\t%-10s %(.2e\n", "Uj*Gij", u_j * g_ij);

                        } catch (NullPointerException e) {
                            System.out.println("pusta lista!");
                            e.printStackTrace();
                        }

                    }

                    System.out.printf("\t%-10s %(.2e\n", "item_sum", item_sum);

                }
                //endregion

                double checkErrorParam;
                //endregion

                //region for NodeType.OTHER_NODE
                if (node.getNodeType() == NodeType.OTHER_NODE) {

                    //region powerNodes
                    if (!elNet.nodesWithNoNeighborsBackList.contains(node.getId())) {
                        System.out.printf("\n[%3d] - %s\n", node.getId(), "algorytm PRAWY - 5B");

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = item_Ii - (item_Ui_Gii - item_sum) / item_sqrt;
                        System.out.printf("%-14s %(.2e\n", "Ii", item_Ii);
                        System.out.printf("%-14s %(.2e\n", "Ui * Gii", item_Ui_Gii);
                        System.out.printf("%-14s %(.2e\n", "sum", item_sum);
                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;
                        System.out.printf("%-14s %(.2e\n", "Gii", g_ii);
                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region napięcie w węźle dla obecnej iteracji i węzła
                        voltageThisIterPresentNode = u_i + deltaVoltageThisIterPresentNode;
                        System.out.printf("%-14s %(.2e\n", "Ui", u_i);
                        System.out.printf("%-14s %(.2e\n", "voltage", voltageThisIterPresentNode);

                        //region setters
                        elNet.nodeMap.get(nodeNumber).setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltageReal(voltageThisIterPresentNode * voltageBase);
                        node.setCurrentReal(item_Ii * BaseValues.currentBase);
                        //endregion
                        //endregion

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
                        System.out.printf("%-14s %d\n\n", "indicator a", a);

                        //endregion

                    } else {

                        System.out.printf("[%3d] - %s\n", node.getId(), "algorytm LEWY - 5A");

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = node.getVoltagePU() -
                                ((item_sqrt * node.getCurrentPU() + item_sum) / g_ii);
                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = (deltaVoltageThisIterPresentNode * g_ii) / item_sqrt;
                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        currentThisIterPresentNode = node.getCurrentPU() + deltaCurrentThisIterPresentNode;
                        System.out.printf("%-14s %(.2e\n", "current", currentThisIterPresentNode);
                        node.setCurrentPU(currentThisIterPresentNode);

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
                        System.out.printf("%-14s %d\n\n", "indicator a", a);

                        //endregion
                    }
                    //endregion
                }
                //endregion

            }
            //endregion

        }
        while (a == 1);
        //endregion
    }
    //endregion

    //region check and set error
    private int checkAndSetErrorIndicator(double deltaCurrent, int a) {

        PreparedErrorIndicator preparedErrorIndicator = deltaCurrentIndefiniteSign ->
                deltaCurrentIndefiniteSign < 0 ? -deltaCurrentIndefiniteSign : deltaCurrentIndefiniteSign;

        SetErrorIndicator setErrorIndicator = (errorIndicator, preparedDeltaCurrent) -> {
            if (errorIndicator == 0) {
                if (preparedDeltaCurrent.prepareIndicator(deltaCurrent) >= BaseValues.epsilon) {
                    errorIndicator = 1;
                }
            }
            return errorIndicator;
        };

        return setErrorIndicator.setIndicator(a, preparedErrorIndicator);

    }

    //region FunctionalInterfaces
    @FunctionalInterface
    interface SetErrorIndicator {
        int setIndicator(int a, PreparedErrorIndicator preparedErrorIndicator);
    }

    @FunctionalInterface
    interface PreparedErrorIndicator {
        double prepareIndicator(double deltaCurrent);
    }
    //endregion
    //endregion

    //region Active power flow
    public void activePowerFlow() {
        double u_n = voltageBase;

        elNet.neighborsForwardMap.forEach((node, nodeNeighborsIDs) -> {

            if (node > 0) {
                double u_i = elNet.nodeMap.get(node).getVoltagePU();

                for (Long nodeID : nodeNeighborsIDs) {
//                    System.out.printf("Ui:%-(4.4f  ", u_i);

                    double u_j = elNet.nodeMap.get(elNet.arcMap.get(nodeID).getEndNode()).getVoltagePU();
//                    System.out.printf("Uj:%-(4.4f  ", u_j);

                    double g_ij = 1 / elNet.arcMap.get(nodeID).getResistancePU();
//                    System.out.printf("Gij:%-(4.2f ", g_ij);

                    if (g_ij != Double.POSITIVE_INFINITY && g_ij != 0) {
                        double pwr_ij = u_n * (u_i - u_j) * g_ij;
                        elNet.arcMap.get(nodeID).setActivePowerFlowPU(pwr_ij);

                        System.out.printf("Ui-Uj:%-(8.4f  ", u_i - u_j);
                        System.out.printf("%3d-->%3d %(.4f  ",                   // start-->end flow
                                node,
                                elNet.arcMap.get(nodeID).getEndNode(),
                                elNet.arcMap.get(nodeID).getActivePowerFlowPU());

                        System.out.println();

                    }
                }
            }
        });
    }
//endregion
}

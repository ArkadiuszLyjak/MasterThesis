package MasterThesis.el_net;

import MasterThesis.data_calc.BaseValues;
import MasterThesis.el_net.InsideCalcContener.DirectMethodInsideCalcEntityBuilder;
import MasterThesis.el_net.InsideCalcContener.NeighborEntityBuilder;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeType;

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
    public void calculateDirectMethod() {

        long iterateMax = 0;

        System.out.println("\n-------------------------------------------------------");
        System.out.println("------------------ NODE POWER TRANSMIT ----------------");
        System.out.println("-------------------------------------------------------");

        int a; // error indicator

        //region obliczenia dla węzłów-sąsiadów po obu stronach
        do {

            a = 0;

            if (iterateMax == 10) break; // safety "valve"
            iterateMax++;

//            DirectMethodInsideCalcEntityBuilder directMethodBuilder =
//            new DirectMethodInsideCalcEntityBuilder(iterateMax);

            System.out.printf("\n----------- iterate: %d -----------\n", iterateMax);

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {

                //region data for every node
                //region zmienne wewnętrzne używane w obl. iteracyjnych
                //long nodeNumber = node.getId();
//                directMethodBuilder.nodeNumber(nodeNumber);
//                System.out.printf("Node=%-3d ", node.getId());

                double deltaCurrentThisIterPresentNode = 0.0; // bieżąca wart. poprawki prądu
//                System.out.printf("ΔI=%(-6.4f ", deltaCurrentThisIterPresentNode);

                double deltaVoltageThisIterPresentNode = 0.0; // bieżąca wart. poprawki prądu
//                System.out.printf("ΔV=%(-6.4f ", deltaVoltageThisIterPresentNode);

                double voltageThisIterPresentNode = 0.0;      // bieżąca wart. napięcia w liczonym węźle
//                System.out.printf("V=%(-6.4f ", voltageThisIterPresentNode);

                double currentThisIterPresentNode = 0.0;      // bieżąca wart. prądu w liczonym węźle
//                System.out.printf("I=%(-6.4f ", currentThisIterPresentNode);

                double item_sqrt = Math.sqrt(3.00); // ok, 1,7320508075688772935274463415059
//                System.out.printf("√=%(-6.4f\n", item_sqrt);
                //endregion

                //region pobranie prądu początkowego
                if (iterateMax == 1) {
                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    directMethodBuilder.currentPU(currentThisIterPresentNode);
//                    System.out.printf("I=%(.4f ", currentThisIterPresentNode);
                } else {
                    currentThisIterPresentNode = node.getCurrentPU();
//                    directMethodBuilder.currentPU(currentThisIterPresentNode);
//                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    System.out.printf("I=%(.4f ", currentThisIterPresentNode);
                }
                //endregion

                //region dane znamionowe / wejściowe
                double u_i = node.getVoltagePU();               // napięcie w wężle liczonym
//                directMethodBuilder.u_i(u_i);
//                System.out.printf("[V.%3d %(7.4f] ", nodeNumber, u_i);

                double g_ii = node.getSelfConductancePU();      // konduktancja własna węzła
//                directMethodBuilder.g_ii(g_ii);
//                System.out.printf("[Gii %(6.2f] ", g_ii);

                double item_Ui_Gii = u_i * g_ii;
//                directMethodBuilder.item_Ui_Gii(item_Ui_Gii);
//                System.out.printf("[Ui*Gii %(.4f] ", item_Ui_Gii);

                double item_Ii = currentThisIterPresentNode;
//                directMethodBuilder.item_Ii(item_Ii);
//                System.out.printf("[Ii %(.4f] ", item_Ii);

//                System.out.println();

                double item_sum = 0.0;

                //endregion

                //region obliczenie sumy "Uj * Gij" po sąsiadach
                //  List<Long> neighborsIDsList = elNet.nodesNeighborsForwardReverseMap.get(node.getId());

                //   if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {

                // for (Long neighborID : neighborsIDsList) {
                for (Long neighborID : elNet.nodesNeighborsForwardReverseMap.get(node.getId())) {
                    Long nodeUniqueNeighborFWD = 0L;

                    try {
//                        System.out.printf("\n\tnbr:%3d ", neighborID);

                        if (node.getId().compareTo(elNet.arcMap.get(neighborID).getEndNode()) != 0) {
                            nodeUniqueNeighborFWD = elNet.arcMap.get(neighborID).getEndNode();
                        } else {
                            nodeUniqueNeighborFWD = elNet.arcMap.get(neighborID).getStartNode();
                        }

                        if (elNet.nodeMap.containsKey(nodeUniqueNeighborFWD)) {
                            double u_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
//                            neighborEntityBuilder.u_j(u_j);
//                            System.out.printf("[%d %(.4f] ", nodeUniqueNeighborFWD, u_j);

                            double g_ij = 1 / elNet.arcMap.get(neighborID).getResistancePU();
//                            neighborEntityBuilder.g_ij(g_ij);
//                            System.out.printf("[G%d.%d %(.4f] ", nodeNumber, nodeUniqueNeighborFWD, g_ij);

                            item_sum += u_j * g_ij;
//                            neighborEntityBuilder.item_sum(item_sum);
//                            System.out.printf("[U%d*G%d%d %(.4f] ", nodeUniqueNeighborFWD, nodeNumber, nodeUniqueNeighborFWD, u_j * g_ij);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("pusta lista!");
                        e.printStackTrace();
                    }

//                        directMethodBuilder.neighborMapCalc(neighborEntityBuilder.build());

                }

//                    System.out.printf("\t%-10s %(.2e\n", "item_sum", item_sum);

//                System.out.println();

                //endregion
                //endregion

                //region for NodeType.OTHER_NODE
                if (node.getNodeType() == NodeType.OTHER_NODE) {

                    //region powerNodes
                    if (!elNet.nodesWithNoNeighborsBackList.contains(node.getId())) {

                        System.out.printf("\n[%3d] - %s\n", node.getId(), "algorytm PRAWY - 5B");

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = item_Ii - (item_Ui_Gii - item_sum) / item_sqrt;
//                        directMethodBuilder.deltaCurrentThisIterPresentNode(deltaCurrentThisIterPresentNode);
//                        System.out.printf("%-14s %(.4f\n", "Ii", item_Ii);
//                        System.out.printf("%-14s %(.2e\n", "Ui * Gii", item_Ui_Gii);
//                        System.out.printf("%-14s %(.2e\n", "sum", item_sum);
//                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;
//                        directMethodBuilder.deltaVoltageThisIterPresentNode(deltaVoltageThisIterPresentNode);
//                        System.out.printf("%-14s %(.2e\n", "Gii", g_ii);
//                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region napięcie w węźle dla obecnej iteracji i węzła
                        voltageThisIterPresentNode = u_i + deltaVoltageThisIterPresentNode;
//                        directMethodBuilder.voltageThisIterPresentNode(voltageThisIterPresentNode);
//                        System.out.printf("%-14s %(.2e\n", "Ui", u_i);
//                        System.out.printf("%-14s %(.2e\n", "voltage", voltageThisIterPresentNode);

                        //region setters
                        node.setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltageReal(voltageThisIterPresentNode * voltageBase);
                        node.setCurrentReal(item_Ii * BaseValues.currentBase);
                        //endregion
                        //endregion

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                        System.out.printf("%-14s %d\n\n", "indicator a", a);
                        //endregion

                    } else {

                        System.out.printf("[%3d] - %s\n", node.getId(), "algorytm LEWY - 5A");

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = node.getVoltagePU() -
                                ((item_sqrt * currentThisIterPresentNode + item_sum) / g_ii);
//                        directMethodBuilder.deltaVoltageThisIterPresentNode(deltaVoltageThisIterPresentNode);
//                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = (deltaVoltageThisIterPresentNode * g_ii) / item_sqrt;
//                        directMethodBuilder.deltaCurrentThisIterPresentNode(deltaCurrentThisIterPresentNode);
//                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        currentThisIterPresentNode = currentThisIterPresentNode + deltaCurrentThisIterPresentNode;
//                        System.out.printf("%-14s %(.2e\n", "current", currentThisIterPresentNode);
                        node.setCurrentPU(currentThisIterPresentNode);

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                        directMethodBuilder.errorIndicator(a);
//                        System.out.printf("%-14s %d\n\n", "indicator a", a);

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

        /*//region obliczenia dla węzłów-sąsiadów z wyszczególnieniem na "forward" i "reverse"
        do {

            a = 0;

            if (iterateMax == 10) break; // safety "valve"
            iterateMax++;

            System.out.printf("\n----------- iterate: %d -----------\n", iterateMax);

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeList) {

                //region data for every node
                //region Description
                long nodeNumber = node.getId();
//                System.out.printf("%-3d ", nodeNumber);

                double deltaCurrentThisIterPresentNode = 0.0; // bieżąca wart. poprawki prądu
//                System.out.printf("%-6.4f ", deltaCurrentThisIterPresentNode);

                double deltaVoltageThisIterPresentNode = 0.0; // bieżąca wart. poprawki prądu
//                System.out.printf("%-6.4f ", deltaVoltageThisIterPresentNode);

                double voltageThisIterPresentNode = 0.0;      // bieżąca wart. napięcia w liczonym węźle
//                System.out.printf("%-6.4f ", voltageThisIterPresentNode);

                double currentThisIterPresentNode = 0.0;      // bieżąca wart. prądu w liczonym węźle
//                System.out.printf("%-6.4f ", currentThisIterPresentNode);

                double item_sqrt = Math.sqrt(3.00); // ok, 1,7320508075688772935274463415059
//                System.out.printf("%-6.4f ", item_sqrt);

//                System.out.println();
                //endregion

                //region pobranie prądu początkowego
                if (iterateMax == 1) {
                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    System.out.printf("%.4f ", currentThisIterPresentNode);
                } else {
                    currentThisIterPresentNode = node.getCurrentPU();
//                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    System.out.printf("%.4f ", currentThisIterPresentNode);
                }
                //endregion

                double u_i = node.getVoltagePU();               // napięcie w wężle liczonym
//                System.out.printf("[V.%d %.4f] ", nodeNumber, u_i);

                double g_ii = node.getSelfConductancePU();      // konduktancja własna węzła
//                System.out.printf("%.4f ", g_ii);

                double item_Ui_Gii = u_i * g_ii;
//                System.out.printf("%.4f ", item_Ui_Gii);

                double item_Ii = currentThisIterPresentNode;
//                System.out.printf("%.4f ", item_Ii);

                double item_sum = 0.0;

                List<Long> neighborsIDsList = elNet.neighborsForwardMap.get(nodeNumber);

                //region obliczenie sumy "Uj * Gij" po sąsiadach
                if (neighborsIDsList != null && !neighborsIDsList.isEmpty()) {

                    for (Long neighborFWDid : neighborsIDsList) {

                        try {

                            long nodeUniqueNeighborFWD = elNet.arcMap.get(neighborFWDid).getEndNode();
//                            System.out.printf("%3d ", nodeUniqueNeighborFWD);

                            double u_j = elNet.nodeMap.get(nodeUniqueNeighborFWD).getVoltagePU();
//                            System.out.printf("[%d %(.4f] ", nodeUniqueNeighborFWD, u_j);

                            double g_ij = 1 / elNet.arcMap.get(neighborFWDid).getResistancePU();
//                            System.out.printf("[G%d.%d %(.4f] ", nodeNumber, nodeUniqueNeighborFWD, g_ij);

                            item_sum += u_j * g_ij;
//                            System.out.printf("[U%d*G%d%d %(.4f] ", nodeUniqueNeighborFWD, nodeNumber, nodeUniqueNeighborFWD, u_j * g_ij);

                        } catch (NullPointerException e) {
                            System.out.println("pusta lista!");
                            e.printStackTrace();
                        }

                    }

//                    System.out.printf("\t%-10s %(.2e\n", "item_sum", item_sum);

                }
                //endregion
                //endregion

                //region for NodeType.OTHER_NODE
                if (node.getNodeType() == NodeType.OTHER_NODE) {

                    //region powerNodes
                    if (!elNet.nodesWithNoNeighborsBackList.contains(node.getId())) {
//                        System.out.printf("\n[%3d] - %s\n", node.getId(), "algorytm PRAWY - 5B");

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = item_Ii - (item_Ui_Gii - item_sum) / item_sqrt;
//                        System.out.printf("%-14s %(.4f\n", "Ii", item_Ii);
//                        System.out.printf("%-14s %(.2e\n", "Ui * Gii", item_Ui_Gii);
//                        System.out.printf("%-14s %(.2e\n", "sum", item_sum);
//                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;
//                        System.out.printf("%-14s %(.2e\n", "Gii", g_ii);
//                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region napięcie w węźle dla obecnej iteracji i węzła
                        voltageThisIterPresentNode = u_i + deltaVoltageThisIterPresentNode;
//                        System.out.printf("%-14s %(.2e\n", "Ui", u_i);
//                        System.out.printf("%-14s %(.2e\n", "voltage", voltageThisIterPresentNode);

                        //region setters
                        node.setVoltagePU(voltageThisIterPresentNode);
                        node.setVoltageReal(voltageThisIterPresentNode * voltageBase);
                        node.setCurrentReal(item_Ii * BaseValues.currentBase);
                        //endregion
                        //endregion

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                        System.out.printf("%-14s %d\n\n", "indicator a", a);

                        //endregion

                    } else {

//                        System.out.printf("[%3d] - %s\n", node.getId(), "algorytm LEWY - 5A");

                        //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                        deltaVoltageThisIterPresentNode = node.getVoltagePU() -
                                ((item_sqrt * currentThisIterPresentNode + item_sum) / g_ii);
//                        System.out.printf("%-14s %(.2e\n", "delta voltage", deltaVoltageThisIterPresentNode);
                        //endregion

                        //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                        deltaCurrentThisIterPresentNode = (deltaVoltageThisIterPresentNode * g_ii) / item_sqrt;
//                        System.out.printf("%-14s %(.2e\n", "delta current", deltaCurrentThisIterPresentNode);
                        //endregion

                        currentThisIterPresentNode = currentThisIterPresentNode + deltaCurrentThisIterPresentNode;
//                        System.out.printf("%-14s %(.2e\n", "current", currentThisIterPresentNode);
                        node.setCurrentPU(currentThisIterPresentNode);

                        //region error settings
                        a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                        System.out.printf("%-14s %d\n\n", "indicator a", a);

                        //endregion
                    }
                    //endregion
                }
                //endregion

            }
            //endregion

        }
        while (a == 1);
        //endregion*/
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

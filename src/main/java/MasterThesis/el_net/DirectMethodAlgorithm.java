package MasterThesis.el_net;

import MasterThesis.data_calc.BaseValues;
import MasterThesis.node.NodeEntity;

import java.util.*;

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

//        System.out.println("\n---------------------------------------------------");
//        System.out.println("---------------- NODE POWER TRANSMIT --------------");
//        System.out.println("---------------------------------------------------");

        int a; // error indicator

        //region obliczenia dla węzłów-sąsiadów po obu stronach
        do {

            a = 0;

//            if (iterateMax == 1) break; // safety "valve"
//            if (iterateMax == 2) break; // safety "valve"
            if (iterateMax == 100) break; // safety "valve"
//            if (iterateMax == 1000) break; // safety "valve"
            iterateMax++;

//            System.out.println("\n-------------------------------------------------------");
//            System.out.printf("---------------------- iterate: %d ---------------------\n", iterateMax);
//            System.out.println("-------------------------------------------------------");

            //region Description
            double deltaCurrentThisIterPresentNode = 0;     // bieżąca wart. poprawki prądu
//            System.out.printf("%s%(-6.4f\n", "ΔI_init = ", deltaCurrentThisIterPresentNode);

            double deltaVoltageThisIterPresentNode = 0;     // bieżąca wart. poprawki prądu
//            System.out.printf("%s%(-6.4f \n", "ΔV_init = ", deltaVoltageThisIterPresentNode);

            double voltageTempThisIterPresentNode = 0;      // bieżąca wart. napięcia w liczonym węźle
//            System.out.printf("%s%(-6.4f \n", "V_init = ", voltageTempThisIterPresentNode);
            Map<Long, Double> nodeVoltageCalculatedMap = new LinkedHashMap<>();

            double currentThisIterPresentNode;

            double currentTempThisIterPresentNode = 0;      // bieżąca wart. prądu w liczonym węźle
//            System.out.printf("%s%(-6.4f \n", "I_init = ", currentTempThisIterPresentNode);
            Map<Long, Double> nodeCurrentCalculatedMap = new LinkedHashMap<>();

            double item_sqrt = Math.sqrt(3.00);             // ok, 1,7320508075688772935274463415059
//            System.out.printf("%s%(-6.4f\n\n", "pierw3_init = ", item_sqrt);
            //endregion

            //region iteracje dla każdego węzła
            for (NodeEntity node : elNet.nodeEntityList) {

                //region data for every node
                //region zmienne wewnętrzne używane w obl. iteracyjnych
                long computedNodeNumber = node.getId();
//                System.out.printf("\n\t\t\t\t----- node = %d (k=%d) -----\n", node.getId(), iterateMax);
                //endregion

                //region pobranie prądu początkowego
                if (iterateMax == 1) {
                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    System.out.printf("I.%d(k=0) = %(.4f\n", computedNodeNumber, currentThisIterPresentNode);
                } else {
//                    currentTempThisIterPresentNode = node.getCurrentPU();
//                    currentThisIterPresentNode = node.getCurrentPU();
                    currentThisIterPresentNode = node.getCurrentInitialPU();
//                    System.out.printf("I.%d(k=%d) = %(.4f\n", computedNodeNumber, iterateMax - 1, currentThisIterPresentNode);
                }
                //endregion

                //region dane znamionowe / wejściowe
                double u_i = node.getVoltagePU();               // napięcie w wężle liczonym
//                System.out.printf("V.%d(k=%d) = %(.4f\n", computedNodeNumber, iterateMax - 1, u_i);

                double g_ii = node.getSelfConductancePU();      // konduktancja własna węzła
//                System.out.printf("G%d.%d = %(.4f\n", computedNodeNumber, computedNodeNumber, g_ii);

                double item_Ui_Gii = u_i * g_ii;
//                System.out.printf("U%d*G%d.%d = %(.4f\n", computedNodeNumber, computedNodeNumber, computedNodeNumber, item_Ui_Gii);

                double sum_Uj_Gij = 0.0;
                //endregion

                //region obliczenie sumy "Uj * Gij" po sąsiadach
                //region wybór mapy: forward lub reverse
                Map<Long, List<Long>> map = new LinkedHashMap<>();
                if (elNet.neighborsForwardMap.containsKey(computedNodeNumber)) {
                    map = elNet.neighborsForwardMap;
                } else if (elNet.neighborsReverseMap.containsKey(computedNodeNumber)) {
                    map = elNet.neighborsReverseMap;
                } else if (!elNet.neighborsForwardMap.containsKey(computedNodeNumber)
                        && !elNet.neighborsReverseMap.containsKey(computedNodeNumber)) {
                    throw new Error("Mapa jest pusta!");
                }
                //endregion

                for (Long neighborID : map.get(computedNodeNumber)) {
//                for (Long neighborID : elNet.nodesNeighborsForwardReverseMap.get(node.getId())) {

                    Long nodeUniqueNeighborNumber = 0L;

                    try {

                        if (node.getId().compareTo(elNet.arcMap.get(neighborID).getEndNode()) != 0) {
                            nodeUniqueNeighborNumber = elNet.arcMap.get(neighborID).getEndNode();
                        } else {
                            nodeUniqueNeighborNumber = elNet.arcMap.get(neighborID).getStartNode();
                        }

//                        System.out.printf("\n\t-> nbrID:(%d) node:%d", neighborID, nodeUniqueNeighborNumber);

                        if (elNet.nodeMap.containsKey(nodeUniqueNeighborNumber)) {
                            double u_j = elNet.nodeMap.get(nodeUniqueNeighborNumber).getVoltagePU();
//                            System.out.printf("\n\t\tV.%d = %(.4f",
//                            nodeUniqueNeighborNumber,
//                            u_j);

                            double g_ij = 1 / elNet.arcMap.get(neighborID).getResistancePU();
//                            System.out.printf("\n\t\tG%d.%d = %(.4f",
//                                    computedNodeNumber,
//                                    nodeUniqueNeighborNumber,
//                                    g_ij);

                            sum_Uj_Gij = sum_Uj_Gij + (u_j * g_ij);
//                            System.out.printf("\n\t\tU%d*G%d.%d = %(.4f\n",
//                                    nodeUniqueNeighborNumber,
//                                    computedNodeNumber,
//                                    nodeUniqueNeighborNumber,
//                                    u_j * g_ij);
                        }

                    } catch (NullPointerException e) {
                        System.out.println("pusta lista!");
                        e.printStackTrace();
                    }
                }

//                System.out.printf("\n\t\tSuma Uj(k-1)*Gij = %(.4f\n", sum_Uj_Gij);
                //endregion
                //endregion

                //region for NodeType.OTHER_NODE
//                if (node.getNodeType() == NodeType.OTHER_NODE) {

                //region powerNodes
                if (!(elNet.nodeEntityList.size() <= elNet.nodesWithNoNeighborsBackList.size())) {

//                    System.out.println("\n\nalgorytm PRAWY - 5B");

                    //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                    deltaCurrentThisIterPresentNode =
                            currentThisIterPresentNode - (item_Ui_Gii - sum_Uj_Gij) / item_sqrt;

//                    System.out.printf("\tΔI.%d(k=%d) = %(.4f\n",
//                            computedNodeNumber, iterateMax, deltaCurrentThisIterPresentNode);

//                    System.out.printf("\tU%d*G%d.%d %(.4f\n",
//                    computedNodeNumber, computedNodeNumber, computedNodeNumber, item_Ui_Gii);

//                    System.out.printf("Suma U%s %(.4f\n", "Suma Uj(k-1)*Gij",
//                    sum_Uj_Gij);
                    //endregion

                    //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                    deltaVoltageThisIterPresentNode = (deltaCurrentThisIterPresentNode * item_sqrt) / g_ii;

//                    System.out.printf("\tΔV.%d(k=%d) = %(.4f\n",
//                            computedNodeNumber, iterateMax, deltaVoltageThisIterPresentNode);

//                        System.out.printf("G%d.%d = %(.4f\n", computedNodeNumber, computedNodeNumber, g_ii);
                    //endregion

                    //region napięcie w węźle dla obecnej iteracji i węzła
                    voltageTempThisIterPresentNode = u_i + deltaVoltageThisIterPresentNode;

//                    System.out.printf("\tV.%d(k=%d) = %(.4f\n",
//                            computedNodeNumber, iterateMax - 1, u_i);

//                    System.out.printf("\tV.%d(k=%d) = %(.4f\n",
//                            computedNodeNumber, iterateMax, voltageTempThisIterPresentNode);

                    //region setters
                    nodeVoltageCalculatedMap.put(computedNodeNumber, voltageTempThisIterPresentNode);
                    //endregion
                    //endregion

                    //region error settings
                    a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                    System.out.printf("\ta = %d\n", a);
                    //endregion

                } else {

//                    System.out.printf("\n[%d] - %s\n", node.getId(), "algorytm LEWY - 5A");

                    //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                    deltaVoltageThisIterPresentNode = node.getVoltagePU() -
                            ((item_sqrt * currentThisIterPresentNode + sum_Uj_Gij) / g_ii);
//                    System.out.printf("%-14s %(.4f\n", "delta voltage", deltaVoltageThisIterPresentNode);
                    //endregion

                    //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                    deltaCurrentThisIterPresentNode = (deltaVoltageThisIterPresentNode * g_ii) / item_sqrt;
//                    System.out.printf("%-14s %(.4f\n", "delta current", deltaCurrentThisIterPresentNode);
                    //endregion

                    currentTempThisIterPresentNode = currentThisIterPresentNode + deltaCurrentThisIterPresentNode;
//                    System.out.printf("%-14s %(.4f\n", "I", currentTempThisIterPresentNode);

                    nodeCurrentCalculatedMap.put(computedNodeNumber, currentTempThisIterPresentNode);

                    //region error settings
                    a = checkAndSetErrorIndicator(deltaCurrentThisIterPresentNode, a);
//                    System.out.printf("a = %d\n", a);
                    //endregion
                }
                //endregion
//                }
                //endregion
            }

            //region setting and printings after each loop
            //region Voltage
            /*//region print voltage PU calculated in previous iteration
            Set<Map.Entry<Long, Double>> entriesVoltage = nodeVoltageCalculatedMap.entrySet();
            if (!nodeVoltageCalculatedMap.isEmpty()) {
                System.out.println();
                for (Map.Entry<Long, Double> entry : entriesVoltage) {
                    System.out.printf("node: %3d = voltage: %s\n",
                            entry.getKey(),
                            new DecimalFormat("0.000000").format(entry.getValue()));
                }
            } else {
                System.out.println("\nnodeVoltageCalculatedMap is empty");
            }
            //endregion*/

            //region setting voltage PU / voltage real calculated in previous iteration
            Set<Long> nodes = nodeVoltageCalculatedMap.keySet();
            for (Long node : nodes) {
                elNet.nodeMap.get(node).setVoltagePU(nodeVoltageCalculatedMap.get(node));
                elNet.nodeMap.get(node).setVoltageReal(nodeVoltageCalculatedMap.get(node) * voltageBase);
            }
            //endregion
            //endregion

            //region Current
            /*//region print current PU calculated in previous iteration
            Set<Map.Entry<Long, Double>> entriesCurrent = nodeCurrentCalculatedMap.entrySet();
            if (!entriesCurrent.isEmpty()) {
                for (Map.Entry<Long, Double> currentEntries : entriesCurrent) {
                    System.out.println("node: " + currentEntries.getKey() + " current: " + currentEntries.getValue());
                }
            } else {
                System.out.println("\nnodeCurrentCalculatedMap is empty");
            }
            //endregion*/

            //region setting current PU / current real calculated in previous iteration
            Collection<Double> currentValues = nodeCurrentCalculatedMap.values();
            Set<Long> currentKeySet = nodeCurrentCalculatedMap.keySet();
            for (Long nodeKey : currentKeySet) {
                elNet.nodeMap.get(nodeKey).setCurrentPU(nodeCurrentCalculatedMap.get(nodeKey));
                elNet.nodeMap.get(nodeKey).setCurrentReal(nodeCurrentCalculatedMap.get(nodeKey)); // ???
            }
            //endregion
            //endregion
            //endregion

        }
        while (a == 1);
        //endregion
    }
    //endregion

    //region check and set error
    private int checkAndSetErrorIndicator(double deltaCurrent, int a) {

        PreparedErrorIndicator preparedErrorIndicator = deltaCurrentIndefiniteSign -> deltaCurrentIndefiniteSign < 0 ? -deltaCurrentIndefiniteSign : deltaCurrentIndefiniteSign;

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

//                        System.out.printf("Ui-Uj:%-(8.4f  ", u_i - u_j);
//                        System.out.printf("%3d-->%3d %(.4f  ",                   // start-->end flow
//                        node,
//                        elNet.arcMap.get(nodeID).getEndNode(),
//                        elNet.arcMap.get(nodeID).getActivePowerFlowPU());
//                        System.out.println();

                    }
                }
            }
        });
    }
    //endregion
}

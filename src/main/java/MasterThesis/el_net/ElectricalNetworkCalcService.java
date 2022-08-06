package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

public class ElectricalNetworkCalcService {

    private static ElectricalNetworkCalcService instance;
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    //region ElectricalNetworkCalcService
    private ElectricalNetworkCalcService() {
    }
    //endregion

    //region getInstance
    public static ElectricalNetworkCalcService getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkCalcService();
        }
        return instance;
    }
    //endregion

    //region calcLineImmitance
    public void calcLineImmitance() {
        for (ArcEntity arc : elNet.arcList) {

            if (arc.getType() == ArcType.LINE) {

                LineTypeEntity lineType = elNet.lineTypeMap.get(arc.getPosition());

                Double resistance = arc.getArcLength() * lineType.getCohesiveUnitResistance();
                Double reactance = arc.getArcLength() * lineType.getCohesiveUnitReactance();
                Double impedance = Math.sqrt(Math.pow(resistance, 2.0) + Math.pow(reactance, 2.0));

                Double resistancePU = resistance / BaseValues.impedanceBase;
                Double reactancePU = reactance / BaseValues.impedanceBase;
                Double impedancePU = impedance / BaseValues.impedanceBase;

                // ustawienie encji
                arc.setResistance(resistance);
                arc.setReactance(reactance);
                arc.setImpedance(impedance);

                arc.setResistancePU(resistancePU);
                arc.setReactancePU(reactancePU);
                arc.setImpedancePU(impedancePU);

            }
        }
    }
    //endregion

    //region calcTrafoImmitance
    public void calcTrafoImmitance() {

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {

                TransformerTypeEntity transformerType =
                        elNet.transformerTypeMap.get(arc.getPosition());

                // power losses in the transformer winding
                Double resistance = (transformerType.getActiveIdleLoss()
                        * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))
                        / Math.pow(transformerType.getNominalPower(), 2.0);

                Double reactance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                Double impedance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                // relative values per Unit
                Double impedancePU = impedance / BaseValues.impedanceBase;
                Double resistancePU = resistance / BaseValues.impedanceBase;
                Double reactancePU = reactance / BaseValues.impedanceBase;

                Double powerPU =
                        transformerType.getNominalPower()
                                / BaseValues.powerBase;

                Double voltageHighPU =
                        transformerType.getNominalUpperVoltage()
                                / BaseValues.voltageBase;

                Double voltageLowPU =
                        transformerType.getNominalLowerVoltage()
                                / BaseValues.voltageBase;

                // setting the entity
                arc.setReactance(reactance);
                arc.setResistance(resistance);
                arc.setImpedance(impedance);

                arc.setImpedancePU(impedancePU);
                arc.setResistancePU(resistancePU);
                arc.setReactancePU(reactancePU);

                arc.setPowerPU(powerPU);
                arc.setVoltageHighPU(voltageHighPU);
                arc.setVoltageLowPU(voltageLowPU);
            }

        }
    }
    //endregion

    //region calcNodeVoltagePu
    public void calcNodeVoltagePu() {
        elNet.nodeMap.forEach((aLong, nodeEntity) ->
                nodeEntity.setVoltagePU(nodeEntity.getVoltage() / BaseValues.voltageBase)
        );
    }
    //endregion

    /**
     * <p>neighboursMap zawiera:</p>
     * <p style="text-indent:20px;">klucz - węzeł początkowy</p>
     * <p style="text-indent:20px;">wartość - to lista sąsiadów tego węzła w postaci</p>
     * <p style="text-intend:20px;">ID rekordu zawierającego unikalny nr węzła końcowego - sąsiada</p>
     */

    //region oblicz prąd początkowy w węzłach mających sąsiadów "z przodu"
    public void calcNodeCurrentPUwithConsequenNodesForZEROiteration() {

        DecimalFormat df = new DecimalFormat("##0.000000");
        DecimalFormat dfv = new DecimalFormat("#0.00");

        elNet.neighborsConsequentMap.forEach((node, nodeNeighborIDList) -> {
                    try {
                        if (node != 0) {

//                            System.out.println("\n\n" + node);

                            double currentPUSum = 0.0;
                            double currentPUforArc = 0.0;

                            double resStartNodeAndHisNeighborPU;
                            long uniqueNeighborNumber;

                            for (Long neighborID : nodeNeighborIDList) { // ID sąsiada!

                                // unikalny nr węzła-sąsiada
                                uniqueNeighborNumber = elNet.arcMap.get(neighborID).getEndNode();

                                // pobranie rezystancji łuku [pu]
                                resStartNodeAndHisNeighborPU =
                                        elNet.arcMap.get(neighborID).getResistancePU();

                                // pobierz napięcie w węźle-sąsiedzie w [PU]
                                double neighborVolPU = elNet.nodeMap
                                        .get(uniqueNeighborNumber)
                                        .getVoltagePU();

                                // oblicz prąd początkowy dla danego węzła, który jest sumą
                                // wszystkich prądów wypływających z niego do węzłów-sąsiadów
                                currentPUforArc = (neighborVolPU
                                        * (1 / resStartNodeAndHisNeighborPU))
                                        / Math.sqrt(3);

                                currentPUSum = currentPUSum + currentPUforArc;

                                /*//region print temporary calculations
                                System.out.printf("%s->", "---");
                                System.out.printf("%3d\t", uniqueNeighborNumber);
                                System.out.printf("R:%.2e\t", resStartNodeAndHisNeighborPU);
                                System.out.printf("V:%.2e\t", neighborVolPU);
                                System.out.printf("I: %.2e", currentPUforArc);
                                System.out.println();
                                //endregion*/
                            }

//                            System.out.printf("Prad dla iter. zerowej dla wezla %d -> I0 = %s %.2e",
//                                    node,
//                                    "Σ",
//                                    currentPUSum);

//                            System.out.println();

                            elNet.nodeMap.get(node).setCurrentInitialPU(currentPUSum);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("!!!! BLAD DLA node = " + node);
                        //TODO poprawa obslugi bledow
                    }
                }
        );

    }
    //endregion

    //region oblicz prąd początkowy w węzłach NIE mających sąsiadów "z przodu"
    public List<Double> calcNodeCurrentPUwithPredecessorsNodesForZEROiteration() {

        Stream<NodeEntity> nodeEntityStream = elNet.nodeList.stream();

        //region Oblicza prąd początkowy dla węzłów nie mających sąsiadów z przodu
        ToDoubleFunction<NodeEntity> initialCurrentPredecessor = value -> {

            List<Long> neighborsIDlist = elNet.neighborsPredecessorMap.get(value.getId());
            List<Long> neighborsNodeID = new LinkedList<>();

            for (Long nbor : neighborsIDlist) {
                neighborsNodeID.add(elNet.arcMap.get(nbor).getStartNode());
            }

            Double currentSum = 0.0;

            for (Long neighborID : neighborsNodeID) {
                currentSum += elNet.nodeMap.get(neighborID).getCurrentInitialPU();
            }

            return currentSum;

        };
        //endregion

        //region ToDoubleFunction
        ToDoubleFunction<NodeEntity> nodeEntityToDoubleFunction = nodeEntity -> {

            if (nodeEntity.getCurrentInitialPU() == null) {
                nodeEntity.setCurrentInitialPU(initialCurrentPredecessor
                        .applyAsDouble(nodeEntity));
            }

            return nodeEntity.getCurrentInitialPU();
        };
        //endregion

        //region current initial [PU]
        return nodeEntityStream
                .mapToDouble(nodeEntityToDoubleFunction)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        //endregion

    }
    //endregion

}





package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

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

                /// ustawienie encji
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

                // straty mocy w uzwojeniu transformatora ActiveIdleLoss
                Double resistance = (transformerType.getActiveIdleLoss()
                        * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))
                        / Math.pow(transformerType.getNominalPower(), 2.0);

                Double reactance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                Double impedance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                //wartosci wzgledne per Unit
                Double impedancePU = impedance / BaseValues.impedanceBase;
                Double resistancePU = resistance / BaseValues.impedanceBase;
                Double reactancePU = reactance / BaseValues.impedanceBase;

                Double powerPU = transformerType.getNominalPower()
                        / BaseValues.powerBase;

                Double voltageHighPU = transformerType.getNominalUpperVoltage()
                        / BaseValues.voltageBase;

                Double voltageLowPU = transformerType.getNominalLowerVoltage()
                        / BaseValues.voltageBase;

                //ustawienie encji
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

        elNet.neighborsConsequentMap.forEach((node, nodeNeighborIDList) -> {
                    try {
                        if (node != 0) {

                            double currentPUSum = 0.0;

                            double resistanceBetweenStartNodeAndHisNeighborPU;
                            long uniqueNeighborNumber;
//                            long startNode;

                            for (Long neighborID : nodeNeighborIDList) { // ID sąsiada!
//                                startNode = elNet.arcMap.get(neighborID).getStartNode();
//                                double startNodeVolPU = elNet.nodeMap.get(node).getVoltagePU();

                                // unikalny nr węzła-sąsiada
                                uniqueNeighborNumber = elNet.arcMap
                                        .get(neighborID)
                                        .getEndNode();

                                if (elNet.arcMap.get(neighborID).getType() == ArcType.LINE) {
                                    resistanceBetweenStartNodeAndHisNeighborPU =
                                            elNet.arcMap.get(neighborID).getResistancePU();
                                } else {
                                    // Dlaczego high a nie low?
                                    resistanceBetweenStartNodeAndHisNeighborPU =
                                            elNet.arcMap.get(neighborID).getVoltageHighPU();
                                }

                                // pobierz napięcie w węźle-sąsiedzie w [PU]
                                double neighborVolPU = elNet.nodeMap
                                        .get(uniqueNeighborNumber)
                                        .getVoltagePU();

                                // pobierz rezystancję łuku łączącego węzeł z jego sąsiadem
                                resistanceBetweenStartNodeAndHisNeighborPU = elNet.arcMap
                                        .get(neighborID)
                                        .getResistancePU();

                                // oblicz prąd początkowy dla danego węzła, który jest sumą
                                // wszystkich prądów wypływających z niego do węzłów-sąsiadów
                                currentPUSum += (neighborVolPU
                                        * (1 / resistanceBetweenStartNodeAndHisNeighborPU))
                                        / Math.sqrt(3);
                            }

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

            Double currentSum = 0.1;

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

        // current initial [PU]
        return nodeEntityStream
                .mapToDouble(nodeEntityToDoubleFunction)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    }
    //endregion

}





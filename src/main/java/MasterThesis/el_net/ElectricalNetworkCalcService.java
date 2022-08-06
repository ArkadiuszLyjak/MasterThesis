package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.base.entity.BaseEntity;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
    public void calcNodeCurrentPU__FORWARD__nodesForZEROiteration() {

        elNet.neighbors__FORWARD__Map.forEach((node, nodeNeighborIDList) -> {
                    try {
                        if (node != 0) {

                            /*//region print temporary calculations "0"
                            System.out.println("\n\n" + node);
                            //endregion*/

                            double currentPUSum = 0.0;
                            double currentPUforArc = 0.0;

                            double resStartNodeAndHisNeighborPU;
                            long endNodeNeighborNumber;

                            for (Long neighborID : nodeNeighborIDList) { // ID sąsiada!

                                // unikalny nr węzła-sąsiada
                                endNodeNeighborNumber = elNet.arcMap.get(neighborID).getEndNode();

                                // pobranie rezystancji łuku [pu]
                                resStartNodeAndHisNeighborPU =
                                        elNet.arcMap.get(neighborID).getResistancePU();

                                // pobierz napięcie w węźle-sąsiedzie w [PU]
                                double neighborVolPU = elNet.nodeMap
                                        .get(endNodeNeighborNumber)
                                        .getVoltagePU();

                                // oblicz prąd początkowy dla danego węzła, który jest sumą
                                // wszystkich prądów wypływających z niego do węzłów-sąsiadów
                                currentPUforArc = (neighborVolPU
                                        * (1 / resStartNodeAndHisNeighborPU))
                                        / Math.sqrt(3);

                                currentPUSum = currentPUSum + currentPUforArc;

                                /*//region print temporary calculations "1"
                                System.out.printf("%s->", "---");
                                System.out.printf("%3d\t", endNodeNeighborNumber);
                                System.out.printf("R:%.2e\t", resStartNodeAndHisNeighborPU);
                                System.out.printf("V:%.2e\t", neighborVolPU);
                                System.out.printf("I: %.2e", currentPUforArc);
                                System.out.println();
                                //endregion*/
                            }

                            /*//region print temporary calculations "2"
                            System.out.printf("Prad dla iter. zerowej dla wezla %d -> I0 = %s %.2e",
                                    node,
                                    "Σ",
                                    currentPUSum);

                            System.out.println();
                            //endregion*/

                            elNet.nodeMap.get(node).setCurrentInitialPU(currentPUSum);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("BLAD DLA node = " + node);
                        //TODO poprawa obslugi bledow
                    }
                }
        );

    }
    //endregion

    //region oblicz prąd początkowy w węzłach NIE mających sąsiadów "z przodu"
    public void calcNodeCurrentPU__REVERSE__nodesForZEROiteration() {

        //region filtracja węzłów, które nia mają policzonego prądu "0"
        Stream<NodeEntity> nodeEntityStream = elNet.nodeList.stream();

        Stream<NodeEntity> nodeZeroCurrent = nodeEntityStream.filter(nodeEntity ->
                nodeEntity.getCurrentInitialPU() == null);

        Stream<Long> nodesZero = nodeZeroCurrent.map(BaseEntity::getId);
        List<Long> listNodesZeroCurrent = nodesZero.collect(Collectors.toList());
        //endregion

        try {
            elNet.neighbors__REVERSE__Map.forEach((node, nodeNeighborIDList) -> {
                if (listNodesZeroCurrent.contains(node)) {

                    //region obliczenie pradu "0" na podst. sąsiadów "wstecz"
                    // rezystancja łuku między węzłem a jego sąsiadem
                    // napięcie w węźle startowym
                    //endregion

                    //region wyświetlenie obliczonych prądów
                    Formatter fmt = new Formatter();
                    System.out.println(fmt.format("%d\tI0: (%.4e)",
                            node,
                            elNet.nodeMap.get(node).getCurrentInitialPU()));
                    //endregion
                }

            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    //endregion

}





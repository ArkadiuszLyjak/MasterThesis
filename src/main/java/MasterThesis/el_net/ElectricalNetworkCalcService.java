package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.base.entity.BaseEntity;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.lineType.LineTypeEntity;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ElectricalNetworkCalcService {

    //region getInstance
    private static ElectricalNetworkCalcService instance;
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();

    private ElectricalNetworkCalcService() {
    }

    public static ElectricalNetworkCalcService getInstance() {
        if (instance == null) {
            instance = new ElectricalNetworkCalcService();
        }
        return instance;
    }
    //endregion

    //region calcLineImmitance
    public void calcLineImmitance() {

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.LINE) {

//                if (!(Double.compare(arc.getArcLength(), 0.0) == 0)) {
                LineTypeEntity lineType = elNet.lineTypeMap.get(arc.getPosition());

                // Parametry immitancji linii liczone dla km stąd dzielenie przez 1000
                // CohesiveUnitResistance dla 7000m 0.191
                Double resistance = (arc.getArcLength() / 1000) * lineType.getCohesiveUnitResistance();   // [km]
//                System.out.print(arc.getArcLength() + " ");
//                System.out.print(lineType.getCohesiveUnitResistance() + " ");
                Double reactance = (arc.getArcLength() / 1000) * lineType.getCohesiveUnitReactance();     // [km]
//                System.out.println(lineType.getCohesiveUnitReactance());
                Double impedance = Math.sqrt(Math.pow(resistance, 2.0) + Math.pow(reactance, 2.0));

                Double resistancePU = resistance / BaseValues.impedanceBase;
                Double reactancePU = reactance / BaseValues.impedanceBase;
                Double impedancePU = impedance / BaseValues.impedanceBase;

                //region ustawienie encji
                arc.setResistance(resistance);
                arc.setReactance(reactance);
                arc.setImpedance(impedance);

                arc.setResistancePU(resistancePU);
                arc.setReactancePU(reactancePU);
                arc.setImpedancePU(impedancePU);
                //endregion

                //region temporary print
                formatter.format("%3d->%3d ", arc.getStartNode(), arc.getEndNode());
//                formatter.format("%-8s ", arc.getType());
                formatter.format("L:%.4f[km] ", arc.getArcLength() / 1000);
//                formatter.format("%-8s ", lineType.getKind().toString());
                formatter.format("R:%-7.4f[Ω] ", reactance);
                formatter.format("X:%-7.4f \u2126", reactance);
                formatter.format("Z:%-7.4f ", impedance);
//                System.out.println(stringBuilder);
                stringBuilder.setLength(0);

                //endregion

//                }

            }
        }
    }
    //endregion

    //region calcTrafoImmitance
    public void calcTrafoImmitance() {

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {

                TransformerTypeEntity transformerType = elNet.transformerTypeMap.get(arc.getPosition());

                /*//region power losses in the transformer winding
                Double resistance = ((transformerType.getActiveIdleLoss() / 1000)       // [MW]
                        * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))      // [kV]
                        / Math.pow((transformerType.getNominalPower() / 1000), 2.0);    // [MVA]

                Double reactance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                Double impedance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)
                        / transformerType.getNominalPower());

                //endregion*/

                double resistance = 0;
                double reactance = 0;
                double impedance = 0;

                //region power losses in the transformer winding
                if ((transformerType.getNominalPower() / 1000) < 2.5) {

                    resistance = ((transformerType.getActiveIdleLoss() / 1000)              // [MW]     OK
                            * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))      // [kV]     OK
                            / Math.pow((transformerType.getNominalPower() / 1000), 2.0);    // [MVA]    OK

                    impedance = (transformerType.getShortingVoltage()                       // [%]      OK
                            * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0)))    // [kV]     OK
                            / ((transformerType.getNominalPower() / 1000) * 100);           // [MVA]    OK

                    // TODO Obliczyc reaktancje na podst. wzoru ze str. 9
                    reactance = Math.sqrt(Math.pow(impedance, 2) - Math.pow(resistance, 2));


                } else { // a moze else-if?
                    System.out.println("Transformator o mocy wyższej niż 2.5 MWA");
                }

                //endregion

                //region relative values per Unit
                Double impedancePU = impedance / BaseValues.impedanceBase;
                Double resistancePU = resistance / BaseValues.impedanceBase;
                Double reactancePU = reactance / BaseValues.impedanceBase;

                Double powerPU = transformerType.getNominalPower() / BaseValues.powerBase;
                Double voltageHighPU = transformerType.getNominalUpperVoltage() / BaseValues.voltageBase;
                Double voltageLowPU = transformerType.getNominalLowerVoltage() / BaseValues.voltageBase;
                //endregion

                //region setting the entity
                arc.setReactance(reactance);
                arc.setResistance(resistance);
                arc.setImpedance(impedance);

                arc.setImpedancePU(impedancePU);
                arc.setResistancePU(resistancePU);
                arc.setReactancePU(reactancePU);

                arc.setPowerPU(powerPU);
                arc.setVoltageHighPU(voltageHighPU);
                arc.setVoltageLowPU(voltageLowPU);
                //endregion
            }

        }
    }
    //endregion

    //region calcNodeVoltagePu
    public void calcNodeVoltagePu() {
        elNet.nodeMap.forEach((aLong, nodeEntity) -> nodeEntity
                .setVoltagePU(nodeEntity.getNominalVoltage() / BaseValues.voltageBase));
    }
    //endregion

    /**
     * <p>neighboursMap zawiera:</p>
     * <p style="text-indent:20px;">klucz - węzeł początkowy</p>
     * <p style="text-indent:20px;">wartość - to lista sąsiadów tego węzła w postaci</p>
     * <p style="text-intend:20px;">ID rekordu zawierającego unikalny nr węzła końcowego - sąsiada</p>
     */

    //region oblicz prąd początkowy w węzłach mających sąsiadów "z przodu"
    public void calcNodeCurrentPUforwardNodesForZeroIter() {

        elNet.neighborsForwardMap.forEach((node, nodeNeighborIDList) -> {
            try {
                if (node != 0) {

                            /*//region print temporary calculations "1/3"
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
                        resStartNodeAndHisNeighborPU = elNet.arcMap.get(neighborID).getResistancePU();

                        // pobierz napięcie w węźle-sąsiedzie w [PU]
                        double neighborVolPU = elNet.nodeMap.get(endNodeNeighborNumber).getVoltagePU();

                        // oblicz prąd początkowy dla danego węzła, który jest sumą
                        // wszystkich prądów wypływających z niego do węzłów-sąsiadów
                        currentPUforArc = (neighborVolPU * (1 / resStartNodeAndHisNeighborPU)) / Math.sqrt(3);

                        currentPUSum = currentPUSum + currentPUforArc;

                                /*//region print temporary calculations "2/3"
                                System.out.printf("%s->", "---");
                                System.out.printf("%3d\t", endNodeNeighborNumber);
                                System.out.printf("R:%.2e\t", resStartNodeAndHisNeighborPU);
                                System.out.printf("V:%.2e\t", neighborVolPU);
                                System.out.printf("I: %.2e", currentPUforArc);
                                System.out.println();
                                //endregion*/
                    }

                            /*//region print temporary calculations "3/3"
                            System.out.printf("Prad dla iter. zerowej dla wezla %d -> I0 = %s %.2e",
                                    node,
                                    "Σ",
                                    currentPUSum);

                            System.out.println();
                            //endregion*/

                    elNet.nodeMap.get(node).setCurrentInitialPU(currentPUSum);
                    elNet.nodeMap.get(node).setCurrentPU(currentPUSum);

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("BLAD DLA node = " + node);
                //TODO poprawa obslugi bledow
            }
        });

    }
    //endregion

    //region oblicz prąd początkowy w węzłach NIE mających sąsiadów "z przodu"
    public void calcNodeCurrentPUreverseNodesForZeroIter() {

        //region filtracja węzłów, które nia mają policzonego prądu "0"
        Stream<NodeEntity> nodeEntityStream = elNet.nodeList.stream();

        Stream<NodeEntity> nodeZeroCurrent = nodeEntityStream.filter(nodeEntity ->
                nodeEntity.getCurrentInitialPU() == null);

        // Strumien zawierający ID węzła bez obl. prądu iteracji "0"
        Stream<Long> nodesZero = nodeZeroCurrent.map(BaseEntity::getId);

        // Utw. kolekcje ze strumienia z ID bez obl. prądu j.w
        List<Long> listNodesZeroCurrent = nodesZero.collect(Collectors.toList());
        //endregion

        try {
            elNet.neighborsReverseMap.forEach((node, nodeNeighborIDList) -> {

                if (listNodesZeroCurrent.contains(node)) {

                    double currentNode = 0;

                    for (Long IDs : nodeNeighborIDList) {
                        double conductanceArcPU = 1 / elNet.arcMap.get(IDs).getResistancePU();

                        double voltagePU = elNet.nodeMap
                                .get(elNet.arcMap.get(IDs).getStartNode())
                                .getVoltagePU();

                        double currentNodeTemporary = (conductanceArcPU * voltagePU) / Math.sqrt(3.0);
                        currentNode += currentNodeTemporary;
                        elNet.nodeMap.get(node).setCurrentInitialPU(currentNode);
                        elNet.nodeMap.get(node).setCurrentPU(currentNode);
                    }
                    //endregion
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
//endregion

}





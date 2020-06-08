package MasterThesis.el_net;

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.line_type.LineType;
import MasterThesis.transformer_type.TransformerTypeEntity;

public class ElectricalNetworkCalcService {

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

    public void calcLineImmitance() {
        for (ArcEntity arc : elNet.arcList) {

            if (arc.getType() == ArcType.LINE) {
                LineType lineType = elNet.lineTypeMap.get(arc.getPosition());

                Double reactance    = arc.getArcLength() * lineType.getCohesiveUnitReactance();
                Double resistance   = arc.getArcLength() * lineType.getCohesiveUnitResistance();
                Double impedance    = Math.sqrt(Math.pow(resistance, 2.0) + Math.pow(reactance, 2.0));
                Double impedancePU  = impedance / BaseValues.impedance_base;
                Double resistancePU = resistance / BaseValues.impedance_base;
                Double reactancePU  = reactance / BaseValues.impedance_base;

                /// ustawienie encji
                arc.setReactance(reactance);
                arc.setResistance(resistance);
                arc.setImpedance(impedance);

                arc.setImpedancePU(impedancePU);
                arc.setResistancePU(resistancePU);
                arc.setReactancePU(reactancePU);

            }
        }
    }

    public void calcTrafoImmitance() {

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {
                //-------
                TransformerTypeEntity transformerType = elNet.transformerTypeMap.get(arc.getPosition());

                Double resistance = (transformerType.getActiveIdleLoss() * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))
                        / Math.pow(transformerType.getNominalPower(), 2.0);

                Double reactance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0) / transformerType.getNominalPower());

                Double impedance = (transformerType.getShortingVoltage() / 100)
                        * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0) / transformerType.getNominalPower());
                //wartosci wzgledne per Unit
                Double impedancePU = impedance / BaseValues.impedance_base;
                Double resistancePU = resistance / BaseValues.impedance_base;
                Double reactancePU = reactance / BaseValues.impedance_base;

                Double powerPU = transformerType.getNominalPower() / BaseValues.power_base;
                Double voltageHighPU = transformerType.getNominalUpperVoltage() / BaseValues.voltage_base;
                Double voltageLowPU = transformerType.getNominalLowerVoltage() / BaseValues.voltage_base;

                //-------------
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

    public void calcNodeVoltagePu() {
        elNet.nodeMap.forEach((aLong, nodeEntity) -> nodeEntity.setVoltagePU(
                nodeEntity.getVoltage() / BaseValues.voltage_base
                )
        );
    }

    public void calcNodeCurrentPU_Iter0(){
        elNet.nodeArcList_Map.forEach((nodeId, arcIdList) -> {
                    try {

                        if (nodeId != 0) {
                            Double currentPUSum = 0.0;
                            for (Long arcId : arcIdList) {
                                Double resPU;
                                Long   endNodeId = elNet.arcMap.get(arcId).getEndNodeId();
                                Double volPU = elNet.nodeMap.get(endNodeId).getVoltagePU();
                                if (elNet.arcMap.get(arcId).getType() == ArcType.LINE) {
                                    resPU = elNet.arcMap.get(arcId).getResistancePU();
                                } else {
                                    resPU = elNet.arcMap.get(arcId).getVoltageHighPU();
                                }
                                currentPUSum += volPU * (1 / resPU);
                            }
                            elNet.nodeMap.get(nodeId).setCurrentPU(currentPUSum);
                        }
                    }
                    catch (Exception e){
                        System.out.println("!!!! BLAD DLA nodeId="+nodeId);
                        //TODO poprawa obslugi bledow
                    }
                }
        );

    }


}

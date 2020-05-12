package MasterThesis.el_net;


import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;

import java.text.DecimalFormat;

public class ElectricalNetworkOutPrinter {

    private static ElectricalNetworkOutPrinter instance;
    ElectricalNetwork elNet = ElectricalNetwork.getInstance();
    private static DecimalFormat dfLine = new DecimalFormat("0.0000");
    private static DecimalFormat dfTrafo = new DecimalFormat("0.00000000");
    private static DecimalFormat dfNodeVolPU = new DecimalFormat("0.00000000");


    private ElectricalNetworkOutPrinter() {

    }

    public static ElectricalNetworkOutPrinter getInstance() {

        if (instance == null) {
            instance = new ElectricalNetworkOutPrinter();
        }
        return instance;
    }


    public void printNetQuantity() {
        System.out.println("-------------------------------------------------------");
        System.out.println("ILOSC typow Trafo : " + elNet.transformerTypeMap.size());
        System.out.println("ILOSC typow lini : " + elNet.lineTypeMap.size());
        System.out.println("ILOSC WEZLOW : " + elNet.nodeMap.size());
        System.out.println("ILOSC LUKOW : " + elNet.arcMap.size());
        System.out.println("---");
    }


    public void printLineImmitance() {

        System.out.println("------- Line  Immitance -------------");

        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.LINE) {
                System.out.println(arc.getId() + " | " +
                        "L: " + arc.getArcLength() + " | " +
                        "X: " + dfLine.format(arc.getReactance()) + " | " +
                        "R: " + dfLine.format(arc.getResistance()) + " | " +
                        "Z: " + dfLine.format(arc.getImpedance())

                );

            }
        }
    }


    public void printTrafoImmitance() {
        System.out.println("------- Trafo Immitance -------------");
        for (ArcEntity arc : elNet.arcList) {
            if (arc.getType() == ArcType.TRANSFORMER) {

                System.out.println(arc.getId() + " | " +
                        "R: " + dfTrafo.format(arc.getResistancePU()) + " | " +
                        "X: " + dfTrafo.format(arc.getReactancePU()) + " | " +
                        "Z: " + dfTrafo.format(arc.getImpedancePU()) + " | " +
                        "H: " + dfTrafo.format(arc.getVoltageHighPU()) + " | " +
                        "L: " + dfTrafo.format(arc.getVoltageLowPU())
                );

            }
        }
    }

    public void printNodeVoltagePu() {
        System.out.println("------- Node VoltagePu-------------");
        elNet.nodeList.forEach( nodeEntity ->
                System.out.println(" ID: " + nodeEntity.getId() + " volPu : " + dfNodeVolPU.format(nodeEntity.getVoltagePU()))
        );

    }


    public void printNodeNeighbors() {
        System.out.println("------- Sasiedzi wezlow -------------");
        elNet.nodeArcList_Map.forEach((nodeId, arcIdList) -> {

                    System.out.print(" [" + nodeId + "] => {");
                    arcIdList.forEach(arcId -> {
                        System.out.print(elNet.arcMap.get(arcId).getEndNodeId() + ",");
                    });
                    System.out.println("} ");
                }

        );
    }




}

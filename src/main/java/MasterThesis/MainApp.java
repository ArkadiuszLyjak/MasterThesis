package MasterThesis;
//TODO Encje naglowkow plikow wynikowych
//TODO Encje artosci wyliczen do plikow wynikowych
//Zmiana Node na ArcNode
//Jednoliczta obsluga błędow
// odczytu pliku
// bledow w pliku

/*
 poranie praramretrow
 jezeli potrzeba w zależności od paramerow to czytaj wszystkie dane z plikow
 zbuduj listy wszystkicj plikow
 zbuduj mape sieci
 wykoneja przejści i obliczenia

 w zależności od parametrow zapisz obliczenia

* */

import MasterThesis.arc.ArcEntity;
import MasterThesis.arc.ArcType;
import MasterThesis.arc_file_tools.FileDataService;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.bfs.BfsAlgorithm;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.ElectricalNetworkService;
import MasterThesis.line_type.LineType;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.text.DecimalFormat;

public class MainApp {

    private static DecimalFormat dfLine = new DecimalFormat("0.0000");
    private static DecimalFormat dfTrafo = new DecimalFormat("0.00000000");


    public static void main(String[] args) {
        try {


            AppParametersService paramsService = AppParametersService.getInstance();
            paramsService.setParametersFromArgs(args);


            ElectricalNetwork elNet = ElectricalNetwork.getInstance();
            ElectricalNetworkService elNetService = ElectricalNetworkService.getInstance();
            BfsAlgorithm bfsAlgorithm = BfsAlgorithm.getInstance();
            FileDataService fileDataService = FileDataService.getInstance();
            //   NetStatistics netStatistics =  NetStatistics.getInstace();


            //Read data files
            fileDataService.readLineTypeFiles();
            fileDataService.readNodeFiles();
            fileDataService.readTransformerTypeFiles();
            fileDataService.readArcFiles();

            //Generate neighbors map
            elNetService.nodeArcListBuild();

            //Generate visit order
            bfsAlgorithm.generateLevelsOrder();

            //-------------------

            System.out.println("---");
            System.out.println("ILOSC typow Trafo : " + elNet.transformerTypeMap.size());
            System.out.println("ILOSC typow lini : " + elNet.lineTypeMap.size());
            System.out.println("ILOSC WEZLOW : " + elNet.nodeMap.size());
            System.out.println("ILOSC LUKOW : " + elNet.arcMap.size());
            System.out.println("---");
            //-------------------
            elNet.lineTypeMap.forEach((aLong, lineType) -> {
                System.out.println(aLong + "> " + lineType.getCohesiveUnitReactance());

            });
            System.out.println("TEST>>>" + elNet.lineTypeMap.get(3L).getCohesiveUnitReactance());

            for (ArcEntity arc : elNet.arcList) {
                if (arc.getType() == ArcType.LINE) {
                    LineType lineType = elNet.lineTypeMap.get(arc.getPosition());

                    Double reactance ;
                    Double resistance;
                    Double impedance ;
                    Double impedancePU;
                    Double resistancePU;
                    Double reactancePU;

                    //----------
                    reactance    = arc.getArcLength() * lineType.getCohesiveUnitReactance();
                    resistance   = arc.getArcLength() * lineType.getCohesiveUnitResistance();
                    impedance    = Math.sqrt(Math.pow(resistance, 2.0) + Math.pow(reactance, 2.0));
                    impedancePU  = impedance  / BaseValues.impedance_base;
                    resistancePU = resistance / BaseValues.impedance_base;
                    reactancePU  = reactance  / BaseValues.impedance_base;


                    /// ustawienie encji
                    arc.setReactance(reactance);
                    arc.setResistance(resistance);
                    arc.setImpedance(impedance);

                    arc.setImpedancePU(impedancePU);
                    arc.setResistance(resistancePU);
                    arc.setReactancePU(reactancePU);


                    System.out.println(arc.getId() + " | " +
                            "L: " + arc.getArcLength() + " | " +
                            "X: " + dfLine.format(arc.getReactance()) + " | " +
                            "R: " + dfLine.format(arc.getResistance()) + " | " +
                            "Z: " + dfLine.format(arc.getImpedance())

                    );

                }
            }


            System.out.println("..");
            System.out.println("------- TRAFO -------------");


            for (ArcEntity arc : elNet.arcList) {
                if (arc.getType() == ArcType.TRANSFORMER) {
                    //-------
                    TransformerTypeEntity transformerType = elNet.transformerTypeMap.get(arc.getPosition());
                    Double reactance;
                    Double resistance;
                    Double impedance ;
                    Double impedancePU;
                    Double resistancePU;
                    Double reactancePU;

                    Double powerPU;
                    Double voltageHighPU ;
                    Double voltageLowPU;
                    Double currentPU;


                    resistance =  (transformerType.getActiveIdleLoss() * Math.pow(transformerType.getNominalUpperVoltage(), 2.0))
                                 / Math.pow(transformerType.getNominalPower(), 2.0);

                    reactance =  (transformerType.getShortingVoltage() / 100)
                               * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0) / transformerType.getNominalPower());

                    impedance =   (transformerType.getShortingVoltage() / 100)
                                * (Math.pow(transformerType.getNominalUpperVoltage(), 2.0) / transformerType.getNominalPower());
                    //wartosci wzgledne per Unit
                    impedancePU  = impedance /BaseValues.impedance_base;
                    resistancePU = resistance /BaseValues.impedance_base;
                    reactancePU  = reactance /BaseValues.impedance_base;

                     powerPU       = transformerType.getNominalPower() / BaseValues.power_base;
                     voltageHighPU = transformerType.getNominalUpperVoltage() / BaseValues.voltage_base;
                     voltageLowPU  = transformerType.getNominalLowerVoltage() / BaseValues.voltage_base;

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

//                    System.out.println(arc.getId() + " | " +
//                            "R: " + arc.getResistancePU() + " | " +
//                            "X: " + arc.getReactancePU() + " | " +
//                            "Z: " + arc.getImpedancePU());


                    System.out.println(arc.getId() + " | " +
                            "R: " + dfTrafo.format(arc.getResistancePU()) + " | " +
                            "X: " + dfTrafo.format(arc.getReactancePU()) + " | " +
                            "Z: " + dfTrafo.format(arc.getImpedancePU()));

                }
            }


            //--------------------
            // Tylko Ci co maja sasiadow
            elNet.nodeArcList_Map.forEach((nodeId, arcIdList) -> {

                        System.out.print(" [" + nodeId + "] => {");
                        arcIdList.forEach(arcId -> {
                            System.out.print(elNet.arcMap.get(arcId).getEndNodeId() + ",");
                        });
                        System.out.println("} ");
                    }

            );
            //-----------------------
            // Kolejnosc odwiedzin
            for (Long i = 0L; i <= bfsAlgorithm.getNetLevel(); i++) {
                bfsAlgorithm.arcLevelsMap.get(i).forEach(nodeArcVO ->

                                System.out.println("LEVEL " + nodeArcVO.netLevel + "  > " + nodeArcVO.nodeId + "->" +
                                        nodeArcVO.neighborNodeId)
                        //        elNet.arcMap.get(nodeArcVO.arcId).getEndNodeId())
                );


            }
        } catch (Exception e) {
            System.out.println("**********************************************************");
            System.out.println("*   jakiś błędzik");
            System.out.println("* >>" + e.getMessage());
            System.out.println("* ");
            System.out.println("**********************************************************");
            e.printStackTrace();
        }
    }

}

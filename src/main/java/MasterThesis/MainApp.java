package MasterThesis;

//TODO Encje naglowkow plikow wynikowych
//TODO Encje wartosci wyliczen do plikow wynikowych

// Zmiana Node na ArcNode
// Jednoliczta obsluga błędow
// odczytu pliku
// bledow w pliku

/*
 podanie parametrow
 jezeli potrzeba w zależności od parametrow to czytaj wszystkie dane z plikow
 zbuduj listy wszystkich plikow
 zbuduj mape sieci
 wykonaj przejścia i obliczenia

 w zależności od parametrow zapisz obliczenia

* */

import MasterThesis.arc_file_tools.FileDataService;
import MasterThesis.base.parameters.AppParameters;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.bfs.BfsAlgorithm;
import MasterThesis.bfs.BfsAlgorithmOutPrinter;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.ElectricalNetworkCalcService;
import MasterThesis.el_net.ElectricalNetworkOutPrinter;
import MasterThesis.el_net.ElectricalNetworkService;
import MasterThesis.node.NodeEntity;

import java.util.Formatter;

public class MainApp {

    public static void main(String[] args) {

        try {

            //region Instances
            AppParametersService paramsService = AppParametersService.getInstance();
            ElectricalNetwork elNet = ElectricalNetwork.getInstance();
            ElectricalNetworkService el_Net_Service = ElectricalNetworkService.getInstance();
            BfsAlgorithm bfsAlgorithm = BfsAlgorithm.getInstance();
            FileDataService file_Data_Service = FileDataService.getInstance();
            ElectricalNetworkCalcService elNetCalcService = ElectricalNetworkCalcService.getInstance();
            ElectricalNetworkOutPrinter elNetPrinter = ElectricalNetworkOutPrinter.getInstance();
            BfsAlgorithmOutPrinter bfsAlgPrinter = BfsAlgorithmOutPrinter.getInstance();
            //endregion

            /*//region NetStatistics
            NetStatistics netStatistics = NetStatistics.getInstance();
            //endregion*/

            //region Print aplication parameters
//            System.out.println(AppParameters.getInstance().toString());
            //endregion

            //region setParametersFromArgs
            paramsService.setParametersFromArgs(args);
            //endregion

            //region Read data files
            file_Data_Service.readArcFile();
            file_Data_Service.readLineTypeFile();
            file_Data_Service.readNodeFile();
            file_Data_Service.readTransformerTypeFile();
            //endregion

            //region Generate neighbors map
            el_Net_Service.nodeNeighborsFollowingListBuild();   // następnik
//            elNetPrinter.printNodeNeighborsWithDirection(
//                    ElectricalNetworkOutPrinter
//                            .DIRECTION.FOLLOWING);

            el_Net_Service.nodeNeighborsPredecessorListBuild(); // poprzednik
//            elNetPrinter.printNodeNeighborsWithDirection(
//                    ElectricalNetworkOutPrinter
//                            .DIRECTION.PREDECESSOR);
            //endregion

            //region Generate visit order
            bfsAlgorithm.generateLevelsOrder();
            //endregion

            //region Print NetQuantity
//            elNetPrinter.printNetQuantity();
            //endregion

            //region Calculation Immitance
            // Calculation Immitance for Line
            elNetCalcService.calcLineImmitance();
//            elNetPrinter.printLineImmitance();

            // Calculation Immitance for Trafo
            elNetCalcService.calcTrafoImmitance();
//            elNetPrinter.printTrafoImmitance();

            // Calculation Per Unit for Nodes
            elNetCalcService.calcNodeVoltagePu();
//            elNetPrinter.printNodeVoltagePu();

            // Calculation initial current iteration zero
            elNetCalcService.calcNodeCurrentPUwithConsequenNodesForZEROiteration();
            elNetCalcService.calcNodeCurrentPUwithPredecessorsNodesForZEROiteration();
//            elNetPrinter.printNodeCurrentPUIter0();
//            bfsAlgPrinter.printNodeVisitedOrder();
            //endregion

            //region general testing
//            el_Net_Service.nodeNeighborsFollowingListBuild();
            //endregion

            /*//region NODE POWER TRANSMIT
            System.out.println("\n-------------------------------------------------------");
            System.out.println("------------------ NODE POWER TRANSMIT ----------------");
            System.out.println("-------------------------------------------------------");
            int k = 0;
            int a = 0;
            int i = 1;
            int m = 2; // ilosc wezlow zasilowych
            int n = elNet.nodeList.size();

            double currentIter = 0.0;
            double deltaCurrentIter = 0.0;
            double voltageIter = 0.0;
            Double deltaVoltageIter = 0.0;
            //endregion*/

            /*//region do loop
            StringBuilder sb = new StringBuilder();
            Formatter formatter = new Formatter(sb);

            do {
                int iterNumber = 0;
                k++;
                // a = 0;
                for (NodeEntity node : elNet.nodeList) {
                    formatter.format("i:(%02d) ", iterNumber++);
                    currentIter = 0.0;
                    deltaCurrentIter = 0.0;
                    voltageIter = 0.0;
                    deltaVoltageIter = 0.0;

                    if (el_Net_Service.isDistributeNode(node)) {
                        //6A
                        deltaVoltageIter = node.getVoltagePU();
                        //7A
                        //8A
                    } else {
                        //6B
                        // jakis bład. ..

                        deltaCurrentIter = node.getCurrentRealPU() - (node.getVoltageRealPU())
                                / Math.sqrt(3.0);
                        //7B
                        //8B
                    }

                    // if (BaseValues.epsilon < a) {}

                    formatter.format("node: (%03d) ", node.getId());
                    formatter.format("Current: %.2e ", deltaCurrentIter);
                    formatter.format("Current: (%3f) ", node.getCurrentRealPU());
                    formatter.format("Voltage: (%3f) ", node.getVoltageRealPU());
                    System.out.println(sb);
                    sb.setLength(0);

                }

            } while (a > 0);
            //endregion*/

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n********************************************");
            System.out.println("************ error calculation *************");
            System.out.println("*********************************************");
        }

    }

}

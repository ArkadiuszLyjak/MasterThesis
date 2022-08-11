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

            //region Print NetQuantity
//            elNetPrinter.printNetQuantity();
            //endregion

            //region Generate neighbors map
            el_Net_Service.nodeNeighbors_FOLLOWING_listBuild();   // następnik
//            elNetPrinter.printNodeNeighborsWithDirection(
//                    ElectricalNetworkOutPrinter.DIRECTION.FORWARD);

            el_Net_Service.nodeNeighbors_REVERSE_ListBuild(); // poprzednik
//            elNetPrinter.printNodeNeighborsWithDirection(
//                    ElectricalNetworkOutPrinter.DIRECTION.REVERSE);
            //endregion

            //region Generate visit order
            bfsAlgorithm.generateLevelsOrder();
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

            // Calculation initial current iteration zero
            elNetCalcService.calcNodeCurrentPU_FORWARD_nodesForZEROiteration();
            elNetCalcService.calcNodeCurrentPU_REVERSE_nodesForZEROiteration();
//            elNetPrinter.printNodeCurrentPUIter0();
            //endregion

            //region kolejność odwiedzin wg algo BFS
//            bfsAlgPrinter.printNodeVisitedOrder();
            //endregion

            //region Nodes informations
//            elNetPrinter.printNodeInfo();
            //endregion

            //region Arcs informations
//            elNetPrinter.printLineType();
            //endregion

            //region NODE POWER TRANSMIT
            System.out.println("\n-------------------------------------------------------");
            System.out.println("------------------ NODE POWER TRANSMIT ----------------");
            System.out.println("-------------------------------------------------------");

            int k = 0; // iteracja zerowa / zero iteration
            int a = 0; // wskaźnik błędu / error indicator
            int i = 1; // liczba węzłów / number of nodes ???
            int m = 2; // ilosc wezlow zasilowych / number of power nodes
            int n = elNet.nodeList.size(); // number of nodes

            double currentIter = 0.0;
            double deltaCurrentIter = 0.0;
            double voltageIter = 0.0;
            Double deltaVoltageIter = 0.0;
            //endregion

            //region do loop
            StringBuilder sb = new StringBuilder();
            Formatter formatter = new Formatter(sb);

            do {
                int iterNumber = 0;
                k++;
                // a = 0;
                for (NodeEntity node : elNet.nodeList) {
                    formatter.format("iter: %d\n", iterNumber++);
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

                        deltaCurrentIter = node.getCurrentRealPU() - (node.getVoltageRealPU()) / Math.sqrt(3.0);
                        //7B
                        //8B
                    }

                    // if (BaseValues.epsilon < a) {}

                    formatter.format("Node: %03d\n", node.getId());
                    formatter.format("I1: %12.4e\n", deltaCurrentIter);
                    formatter.format("I2: %6.4e\n", node.getCurrentRealPU());
                    formatter.format("V:  %6.4e\n", node.getVoltageRealPU());
                    System.out.println(sb);
                    sb.setLength(0);

                }

            } while (a > 0.0001);
            //endregion

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n********************************************");
            System.out.println("************ error calculation *************");
            System.out.println("*********************************************");
        }

    }

}

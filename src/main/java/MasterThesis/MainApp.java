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

import java.nio.charset.Charset;

public class MainApp {

    public static void main(String[] args) {
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("default Charset:" + defaultCharset);

        try {

            //region Instances
            AppParametersService appParametersService = AppParametersService.getInstance();
            ElectricalNetwork elNet = ElectricalNetwork.getInstance();
            ElectricalNetworkService elNetService = ElectricalNetworkService.getInstance();
            BfsAlgorithm bfsAlgo = BfsAlgorithm.getInstance();
            FileDataService fileDataService = FileDataService.getInstance();
            ElectricalNetworkCalcService elNetCalcService = ElectricalNetworkCalcService.getInstance();
            ElectricalNetworkOutPrinter elNetOutPrinter = ElectricalNetworkOutPrinter.getInstance();
            BfsAlgorithmOutPrinter bfsAlgOutPrinter = BfsAlgorithmOutPrinter.getInstance();
            //endregion

            /*//region NetStatistics
            NetStatistics netStatistics = NetStatistics.getInstance();
            //endregion*/

            //region Print application parameters
//            System.out.println(AppParameters.getInstance().toString());
            //endregion

            //region set parameters from args
            appParametersService.setParametersFromArgs(args);
            //endregion

            //region Read data files
            fileDataService.readArcFile();
            fileDataService.readLineTypeFile();
            fileDataService.readNodeFile();
            fileDataService.readTransformerTypeFile();
            //endregion

            //region Print NetQuantity
//            elNetOutPrinter.printNetQuantity();
            //endregion

            //region generate front and back neighbors maps
            elNetService.nodeNbrsFwdListBuild();   // następnik
//            elNetOutPrinter.printNodeNBRSdirection(ElectricalNetworkOutPrinter.DIRECTION.FWD);

            elNetService.nodeNbrsRevListBuild();     // poprzednik
//            elNetOutPrinter.printNodeNBRSdirection(ElectricalNetworkOutPrinter.DIRECTION.REVERSE);
            //endregion

            //region Generate visit order
            bfsAlgo.generateLevelsOrder();
            //endregion

            //region calculations for power grid elements
            //region Calculation Immitance for Line
            elNetCalcService.calcLineImmitance();
//            elNetOutPrinter.printLineImmitance();
            //endregion

            //region Calculation Immitance for Trafo
            elNetCalcService.calcTrafoImmitance();
//            elNetOutPrinter.printTrafoImmitance();
            //endregion

            //region Calculation Per Unit for Nodes
            elNetCalcService.calcNodeVoltagePu();
            //endregion

            //region Calculation initial current iteration zero and printing
            elNetCalcService.calcNodeCurrPUFWDnodesForZeroIter();
            elNetCalcService.calcNodeCurrentPUrevNodesForZeroIter();
//            elNetOutPrinter.printNodeCurrPUIterZero();
            //endregion
            //endregion

            //region order of visits according to algo BFS
//            bfsAlgOutPrinter.printNodeVisitedOrder();
            //endregion

            //region Nodes informations printer
//            elNetOutPrinter.printNodeInfo();
            //endregion

            //region arcs informations
//            elNetOutPrinter.printLineType();
            //endregion

            //region self conductance of the node
            elNetService.calcNodeSelfCond();
//            elNetOutPrinter.printNodesNbrsFwdRevMap();
//            elNetOutPrinter.printSelfConductance();
            //endregion

            //region Print distributed nodes
//            elNetOutPrinter.printDistributedNodes(NodeType.OTHER_NODE);
            //endregion

            //region create nodes with no neighbors in front
            elNetService.createNoNBRSnodesList();
//            elNetOutPrinter.printNDSwithNoNBRSinFront();
            //endregion

            /*//region NODE POWER TRANSMIT
            System.out.println("\n-------------------------------------------------------");
            System.out.println("------------------ NODE POWER TRANSMIT ----------------");
            System.out.println("-------------------------------------------------------");

            int k = 0; // the number of iterations is undefined and depends on the error rate
            int a = 0; // error indicator
            int i = 0; // number to iterate nodes
            int m = 2; // ilosc wezlow zasilowych / number of power nodes
            int n = elNet.nodeList.size(); // number of nodes at all

            double currentIter = 0.0;
            double deltaCurrentThisIterPresentNode = 0.0;
            double voltageThisIterPresentNode = 0.0;
            double voltagePreviouseIter = 0.0;
            double deltaVoltageThisIterPresentNode = 0.0;
            double eps = 23.001;
            //endregion*/

            /*//region do loop
            StringBuilder sb = new StringBuilder();
            Formatter formatter = new Formatter(sb);

            do {
                k++;
                int iterNumber = 0;
                double currentPU = 0;

                for (NodeEntity node : elNet.nodeList) {

                    formatter.format("i:%3d ", iterNumber++);

                    if (n > m) {
                        if (!elNetService.isDistributeNode(node)) {
                            formatter.format("n:%3d ", node.getId());

                            //region obliczenie zmiany prądu dla obecnego węzła dla iteracji 'k'
                            if (k == 1) {
                                currentPU = node.getCurrentInitialPU();         // prąd w węźle
                            } else if (k > 1) {
                                currentPU = node.getCurrentPU();
                            }

                            double v_i = node.getVoltagePU();                   // napięcie w wężle liczonym
                            double g_ii = node.getSelfConductance();            // konduktancja własna węzła

                            // napięcia w węzłach-sąsiadach dla iter. poprzedniej
                            // konduktancja między liczonym węzłem a jego sąsiadem
                            double item_Ii = currentPU;
                            formatter.format("I:%.2e ", item_Ii);

                            double item_Ui_Gii = v_i * g_ii;
                            formatter.format("Ui*Gii:%.2e ", item_Ui_Gii);

                            // obliczenia tej j*banej sumy po sąsiedzku na przedzie
                            double item_sum = 0;
                            List<Long> neighborsID = elNet.neighborsFORWARDmap.get(node.getId());

                            formatter.format("∑%.2e ", item_sum);

                            double item_sqrt = Math.sqrt(3.00);

                            deltaCurrentThisIterPresentNode = item_Ii;  // obliczenia główne
                            formatter.format("ΔI:%.2e ", deltaCurrentThisIterPresentNode);
                            //endregion

                            //region obliczenie zmiany napięcia dla obecnego węzła i obecnej iteracji 'k'
                            // j.w poprawka prądu wyliczona powyżej
                            // konduktancja własna węzła
//                            deltaVoltageThisIterPresentNode = 0.2;
                            //endregion

                            //region napięcie w węźle dla obecnej iteracji i węzła
                            // napięcie w węźle w poprzedniej iteracji
                            // zmiana napięcia wyliczona powyżej - deltaVoltageThisIterPresentNode
//                            voltageThisIterPresentNode = 3.3;
                            //endregion

                            if (deltaCurrentThisIterPresentNode >= eps) a = 1;
                        }

//                        System.out.println(sb);
                        sb.setLength(0);
//                        System.out.println();

                    } else {
                        System.out.println("algorytm LEWY - 5A");
                    }
                }

                System.out.println("a = " + a);

            }
            while (a != 0);
            //endregion*/

        } catch (
                Exception e) {
            e.printStackTrace();
            System.out.println("\n********************************************");
            System.out.println("************ error calculation *************");
            System.out.println("*********************************************");
        }

    }

}

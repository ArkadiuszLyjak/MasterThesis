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
//import MasterThesis.bfs.BfsAlgorithm;
//import MasterThesis.bfs.BfsAlgorithmOutPrinter;
import MasterThesis.bfs.BfsAlgorithm;
import MasterThesis.bfs.BfsAlgorithmOutPrinter;
import MasterThesis.el_net.*;
import MasterThesis.node.NodeType;
import MasterThesis.tools.NetStatistics;

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
            DirectMethodAlgorithm directMethodAlgorithm = DirectMethodAlgorithm.getInstance();
            //endregion

            //region NetStatistics
            NetStatistics netStatistics = NetStatistics.getInstance();
            //endregion

            //region Print application parameters
//            System.out.println(AppParameters.getInstance().toString());
            //endregion

            //region set parameters from args
            appParametersService.setParametersFromArgs(args);
            //endregion

            //region Read data files
            fileDataService.readArcFile();
//            elNetOutPrinter.arcEntityPrinter(elNet.arcMap);

            fileDataService.readLineTypeFile();
//            elNetOutPrinter.lineTypeEntityPrinter(elNet.lineTypeMap);

            fileDataService.readNodeFile();
//            elNetOutPrinter.nodeEntityPrinter(elNet.nodeMap);

            fileDataService.readTransformerTypeFile();
//            elNetOutPrinter.transformerTypeEntityPrinter(elNet.transformerTypeMap);
            //endregion

            //region Print NetQuantity
//            elNetOutPrinter.printNetQuantity();
            //endregion

            //region generate front and back neighbors maps
            elNetService.nodeNeighborsForwardListBuild();   // następnik
//            elNetOutPrinter.printNodeNeighborsDirection(ElectricalNetworkOutPrinter.DIRECTION.FWD);

            elNetService.nodeNeighborsReverseListBuild();     // poprzednik
//            elNetOutPrinter.printNodeNeighborsDirection(ElectricalNetworkOutPrinter.DIRECTION.REV);
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
//            elNetOutPrinter.printTrafoImmitance();          // [PU]
//            elNetOutPrinter.printTrafoImmitance(true); // [Ω]
            //endregion

            //region Calculation Per Unit for Nodes
            elNetCalcService.calcNodeVoltagePu();
            //endregion

            //region Calculation initial current iteration zero and printing
            elNetCalcService.calcNodeCurrentPUforwardNodesForZeroIter();
            elNetCalcService.calcNodeCurrentPUreverseNodesForZeroIter();
//            elNetOutPrinter.printNodeCurrentPUIterZero();
            //endregion
            //endregion

            //region order of visits according to algo BFS
//            bfsAlgOutPrinter.printNodeVisitedOrder();
            //endregion

            //region arcs informations
//            elNetOutPrinter.printLineType();
            //endregion

            //region self conductance of the node
            elNetService.calcNodeSelfCond();
//            elNetOutPrinter.printSelfConductance();
            //endregion

            //region print nodes neighbors forward reverse map
//            elNetOutPrinter.printNodesNeighborsForwardReverseMap();
            //endregion

            //region Print distributed nodes
//            elNetOutPrinter.printDistributedNodes(NodeType.OTHER_NODE);
            //endregion

            //region create nodes with no neighbors in front
            elNetService.createNoNeighborsNodesList();
//            elNetOutPrinter.printNodesWithNoNeighborsInFront();
            //endregion

            //region Main algorithm for direct current calculation method
            int nodesQuantity = elNet.nodeList.size(); // number of nodes at all
            int powerNodesQuantity = 2; // ilosc wezlow zasilowych / number of power nodes
            int iterateMax = 0; // the number of iterations is undefined and depends on the error rate
            directMethodAlgorithm.calculateDirectMethod(nodesQuantity, powerNodesQuantity, iterateMax);
            //endregion

            //region  active power flow calculation
//            directMethodAlgorithm.activePowerFlow();
            //endregion


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n********************************************");
            System.out.println("************ error calculation *************");
            System.out.println("*********************************************");
        }

    }

}

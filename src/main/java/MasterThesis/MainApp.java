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
import MasterThesis.el_net.*;
import MasterThesis.node.NodeType;

import java.nio.charset.Charset;

public class MainApp {

    public static void main(String[] args) {

        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("default Charset:" + defaultCharset);

        try {

            //region Instances
            AppParametersService appParametersService = AppParametersService.getInstance();
            FileDataService fileDataService = FileDataService.getInstance();
            ElectricalNetwork elNet = ElectricalNetwork.getInstance();
            ElectricalNetworkService elNetService = ElectricalNetworkService.getInstance();
            BfsAlgorithm bfsAlgo = BfsAlgorithm.getInstance();
            ElectricalNetworkCalcService elNetCalcService = ElectricalNetworkCalcService.getInstance();
            ElectricalNetworkOutPrinter elNetOutPrinter = ElectricalNetworkOutPrinter.getInstance();
            BfsAlgorithmOutPrinter bfsAlgOutPrinter = BfsAlgorithmOutPrinter.getInstance();
            DirectMethodAlgorithm directMethodAlgorithm = DirectMethodAlgorithm.getInstance();
            // endregion

            //region NetStatistics
//            NetStatistics netStatistics = NetStatistics.getInstance();
            //endregion

            //region set parameters from args
            appParametersService.setParametersFromArgs(args);
            //endregion

            //region Read data files
            fileDataService.readArcFile();
            fileDataService.readLineTypeFile();
            fileDataService.readNodeFile();
            fileDataService.readTransformerTypeFile();

            elNetOutPrinter.printLineType(); // print line type
            //endregion

            //region print app parameters and net quantity
            System.out.println(AppParameters.getInstance().toString());
            elNetOutPrinter.printNetQuantity(); // print net quantity
            //endregion

            //region generate front and back neighbors maps
            elNetService.nodeNeighborsForwardMapBuild();   // następnik
            elNetOutPrinter.printNodeNeighborsDirection(ElectricalNetworkOutPrinter.DIRECTION.FWD);

            elNetService.nodeNeighborsReverseMapBuild();     // poprzednik
            elNetOutPrinter.printNodeNeighborsDirection(ElectricalNetworkOutPrinter.DIRECTION.REV);

            elNetService.nodeNeighborsForwardReverseListBuild();
            elNetOutPrinter.printNodesNeighborsForwardReverseMap(); // print nodes neighbors [id] forward reverse map
            //endregion

            //region Generate visit order
            bfsAlgo.generateLevelsOrder();
            bfsAlgOutPrinter.printNodeVisitedOrder(); // print node visited order
            //endregion

            //region calculations for power grid elements
            //region Calculation Immitance for Line
            elNetCalcService.calcLineImmitance();
            elNetOutPrinter.printLineImmitance();
            //endregion

            //region Calculation Immitance for Trafo
            elNetCalcService.calcTrafoImmitance();
            elNetOutPrinter.printTrafoImmitance();          // print trafo immitance [PU]
            elNetOutPrinter.printTrafoImmitance(true); // print trafo immitance [Ω]
            //endregion

            //region Calculation Per Unit for Nodes
            elNetCalcService.calcNodeVoltagePu();
            //endregion

            //region Calculation initial current iteration zero and printing
            elNetCalcService.calcNodeCurrentPUAllNodes();
            elNetOutPrinter.printNodeCurrentPUIterZero(); // print node current PU iter zero
            //endregion
            //endregion

            //region self conductance of the node
            elNetService.calcNodeSelfCond();
            elNetOutPrinter.printSelfConductance(); // print self conductance
            //endregion

            //region create nodes with no neighbors in front
            elNetService.createNoFrontNeighborsNodesList();
            elNetOutPrinter.printNodesWithNoNeighborsInFront(); // print nodes with no front neighbors
            //endregion

            //region create and print power nodes list
            elNetService.createNoBackNeighborsNodesList();
            elNetOutPrinter.printNodesWithNoNeighborsAtBack(); // print nodes with no back neighbors
            //endregion

            //region Main algorithm for direct current calculation method
            directMethodAlgorithm.calculateDirectMethod();

            //region  active power flow calculation
            directMethodAlgorithm.activePowerFlow();
            //endregion
            //endregion

            //region print results to files
            fileDataService.writeNodeResultsToFile();
            fileDataService.writeArcResultsToFile();
            //endregion

            elNetOutPrinter.printNodeValues(ElectricalNetworkOutPrinter.LEVELPRINT.VERTICAL);
            elNetOutPrinter.printNodeValues(ElectricalNetworkOutPrinter.LEVELPRINT.HORIZONTAL);
            elNetOutPrinter.printDistributedNodes(NodeType.OTHER_NODE); // print distributed nodes
            //endregion

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n********************************************");
            System.out.println("************ error calculation *************");
            System.out.println("********************************************");
        }

    }

}

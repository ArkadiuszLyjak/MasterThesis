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
import MasterThesis.bfs.BfsAlgorithmOutPrinter;
import MasterThesis.data_calc.BaseValues;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.ElectricalNetworkCalcService;
import MasterThesis.el_net.ElectricalNetworkOutPrinter;
import MasterThesis.el_net.ElectricalNetworkService;
import MasterThesis.line_type.LineType;
import MasterThesis.node.NodeEntity;
import MasterThesis.transformer_type.TransformerTypeEntity;

import java.text.DecimalFormat;
import java.util.List;

public class MainApp {


    public static void main(String[] args) {
        try {
            AppParametersService         paramsService = AppParametersService.getInstance();
            ElectricalNetwork            elNet = ElectricalNetwork.getInstance();
            ElectricalNetworkService     elNetService = ElectricalNetworkService.getInstance();
            BfsAlgorithm                 bfsAlgorithm = BfsAlgorithm.getInstance();
            FileDataService              fileDataService = FileDataService.getInstance();
            ElectricalNetworkCalcService elNetCalcService = ElectricalNetworkCalcService.getInstance();
            ElectricalNetworkOutPrinter  elNetPrinter = ElectricalNetworkOutPrinter.getInstance();
            BfsAlgorithmOutPrinter       bfsAlgPrinter = BfsAlgorithmOutPrinter.getInstance();
            //NetStatistics netStatistics =  NetStatistics.getInstace();

            //--------------------------------------------------------------
            paramsService.setParametersFromArgs(args);


            //Read data files
            fileDataService.readLineTypeFiles();
            fileDataService.readTransformerTypeFiles();

            fileDataService.readNodeFiles();
            fileDataService.readArcFiles();
            //-------------------
            //Generate neighbors map
            elNetService.nodeArcListBuild();

            //Generate visit order
            bfsAlgorithm.generateLevelsOrder();

            //-------------------
            //Print
            elNetPrinter.printNetQuantity();

            //-------------------
            // Calculation Immitance for Line
            elNetCalcService.calcLineImmitance();
            elNetPrinter.printLineImmitance();


            // -------------------------------
            // Calculation Immitance for Trafo
            elNetCalcService.calcTrafoImmitance();
            elNetPrinter.printTrafoImmitance();
            //---------------------

            // Calculation Per Unit for Nodes
            elNetCalcService.calcNodeVoltagePu();



            elNetPrinter.printNodeVoltagePu();

            elNetPrinter.printNodeNeighbors();
            //--------------------------------
            ///->>>>
            System.out.println("------------------ NODE CurrentPU ----------------");
            elNetCalcService.calcNodeCurrentPU_Iter0();
            elNetPrinter.printNodeCurrentPU_Iter0();



            bfsAlgPrinter.printNodeVisitedOrder();

            //----------------------------------------
            System.out.println("------------------ NODE POWER TRANSMIT ----------------");
            int k = 0;
            int a = 0;
            int i = 1;
            int m = 2; // ilosc wezlow zasilowych
            int n = elNet.nodeList.size() ;

            Double   currentIter = 0.0;
            Double   deltaCurrentIter = 0.0;
            Double   voltageIter = 0.0;
            Double   deltaVoltageIter = 0.0;

            do {
                k++;
                a=0;
                for (NodeEntity node : elNet.nodeList) {
                    currentIter = 0.0;
                    deltaCurrentIter = 0.0;
                    voltageIter = 0.0;
                    deltaVoltageIter = 0.0;
                    //---------------------
                    if (elNetService.isDistributeNode(node)){
                     //6A
                      deltaVoltageIter = node.getVoltagePU();

                     //7A

                     //8A
                    }
                    else {
                        //6B
                        deltaCurrentIter = node.getCurrentPU() -
                                                (node.getVoltagePU() )
                                                /Math.sqrt(3);
                        //7B
                        //8B
                    }

//                    if (BaseValues.epsilon){
//
//
//                    }



                    System.out.println("[" + node.getId() + "] = "
                            + node.getCurrentPU_Real() + " <"
                            + node.getVoltagePU_Real() + " <"
                    );
                }

            } while (a > 0) ;


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

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

import MasterThesis.arc_file_tools.FileDataService;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.bfs.BfsAlgorithm;
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.ElectricalNetworkService;
import MasterThesis.el_net.NodeArcVO;

public class MainApp {



    public static void main(String[] args) {
        //TODO odczyt parametrow wywołania args
        AppParametersService paramsService =  AppParametersService.getInstance();
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
        System.out.println("ILOSC typow Trafo : "+elNet.transformerTypeMap.size());
        System.out.println("ILOSC typow lini : "+elNet.lineTypeMap.size());
        System.out.println("ILOSC WEZLOW : "+elNet.nodeMap.size());
        System.out.println("ILOSC LUKOW : "+elNet.arcMap.size());
        System.out.println("---");
        //-------------------


        //--------------------
        // Tylko Ci co maja sasiadow
        elNet.nodeArcList_Map.forEach((nodeId, arcIdList) -> {
             System.out.print(" ["+nodeId+ "] => {");
             arcIdList.forEach(arcId -> {
                  System.out.print(elNet.arcMap.get(arcId).getEndNodeId()+",");
             });
             System.out.println("} ");
            }
          );

        for (Long i=0L ;i  <= bfsAlgorithm.getNetLevel(); i++){
            bfsAlgorithm.arcLevelsMap.get(i).forEach(nodeArcVO ->

                    System.out.println("LEVEL "+ nodeArcVO.netLevel+"  > "+ nodeArcVO.nodeId + "->"+
                                    nodeArcVO.neighborNodeId)
                            //        elNet.arcMap.get(nodeArcVO.arcId).getEndNodeId())
            );


        }


    }
}

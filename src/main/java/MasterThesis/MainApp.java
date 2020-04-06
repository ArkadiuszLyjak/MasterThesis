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
import MasterThesis.el_net.ElectricalNetwork;
import MasterThesis.el_net.ElectricalNetworkService;
import MasterThesis.line_type.LineType;

import java.text.DecimalFormat;

public class MainApp {

    private static DecimalFormat df = new DecimalFormat("0.00");


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
                    Double lineReactance = 0.0;
                    LineType lineType = elNet.lineTypeMap.get(arc.getPosition());
                    lineReactance = arc.getArcLength() *  lineType.getCohesiveUnitResistance();
                 System.out.println(arc.getId() + " " +
                               arc.getArcLength()+ " " +
                         df.format(lineReactance)  );

                }
            }

//        elNet.arcMap.forEach((aLong, arcEntity) ->
//                {     ElectricalNetwork eelNet = ElectricalNetwork.getInstance();
//                    if (arcEntity.getType() == ArcType.LINE ) {
//                        Double lineReactance = 0.0;
//                        LineType lineType =  eelNet.lineTypeMap.get(arcEntity.getPosition());
//                        System.out.println(" size LT="+lineType.getCohesiveUnitReactance() );
//
//                        // lineReactance = arcEntity.getArcLength() * lineType.getCohesiveUnitReactance();
//                        //System.out.println(lineType.getCohesiveUnitReactance());
//                        System.out.println(arcEntity.getId() + " "+arcEntity.getArcLength()+" "+ lineReactance +" "+arcEntity.getPosition()+ ">>" +eelNet.);
//
//                    }
//                }
//        );


//        //--------------------
//        // Tylko Ci co maja sasiadow
//        elNet.nodeArcList_Map.forEach((nodeId, arcIdList) -> {
//
//                    System.out.print(" [" + nodeId + "] => {");
//                    arcIdList.forEach(arcId -> {
//                        System.out.print(elNet.arcMap.get(arcId).getEndNodeId() + ",");
//                    });
//                    System.out.println("} ");
//                }
//
//        );
//
//        for (Long i = 0L; i <= bfsAlgorithm.getNetLevel(); i++) {
//            bfsAlgorithm.arcLevelsMap.get(i).forEach(nodeArcVO ->
//
//                            System.out.println("LEVEL " + nodeArcVO.netLevel + "  > " + nodeArcVO.nodeId + "->" +
//                                    nodeArcVO.neighborNodeId)
//                    //        elNet.arcMap.get(nodeArcVO.arcId).getEndNodeId())
//            );
//
//
//        }
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

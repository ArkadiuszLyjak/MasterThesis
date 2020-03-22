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
import MasterThesis.arc.ArcFactory;
import MasterThesis.base.parameters.AppParametersService;
import MasterThesis.bfs.ElectricalNetwork;
import MasterThesis.bfs.FileDataReader;
import MasterThesis.line_type.LineType;
import MasterThesis.line_type.LineTypeFactory;
import MasterThesis.node.NodeEntity;
import MasterThesis.node.NodeFactory;
import MasterThesis.transformer_type.TransformerTypeEntity;
import MasterThesis.transformer_type.TransformerTypeFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) throws FileNotFoundException {
        //TODO odczyt parametrow wywołania args
        AppParametersService paramsService =  AppParametersService.getInstance();
        ElectricalNetwork elNet = ElectricalNetwork.getInstance();


        FileDataReader.netFileRead(paramsService.getNodeFileFullPath(),
                s -> { NodeEntity node = NodeFactory.prepareFromString(s);
                       elNet.nodeMap.put(node.getId(),node);
                });



        FileDataReader.netFileRead(paramsService.getLineTypeFullFileName(),
                s ->  { LineType lineType =  LineTypeFactory.prepareFromString(s);
                         elNet.lineTypeMap.put(lineType.getId(), lineType);
                      }
        );

        FileDataReader.netFileRead(paramsService.getTransformerTypeFullFileName(),
                s -> {
                    TransformerTypeEntity transformerTypeEntity = TransformerTypeFactory.prepareFromString(s);
                    elNet.transformerTypeMap.put(transformerTypeEntity.getId(), transformerTypeEntity);
                }
        );

        FileDataReader.netFileRead(paramsService.getArcFullFileName(),
                s -> { ArcEntity arc = ArcFactory.prepareFromString(s);
                    elNet.arcMap.put(arc.getId(),arc);
                }
        );
        //-------------------
        System.out.println("---");
        System.out.println("ILOSC WEZLOW mapa: "+elNet.nodeMap.size());
        System.out.println("ILOSC Lukow : "+elNet.arcMap.size());
        System.out.println("ILOSC Typow Trafo : "+elNet.transformerTypeMap.size());
        System.out.println("ILOSC tyow lini : "+elNet.lineTypeMap.size());
        System.out.println("---");
        //-------------------



        // Mapa WEZlowW Lista lukow
        Map<Long,List<Long>> arcBfsMap = new HashMap<>();

        elNet.arcMap.forEach((aLong, arcEntity) ->  {
            if (!arcBfsMap.containsKey(arcEntity.getStartNodeId())) {
                arcBfsMap.put(arcEntity.getStartNodeId(), new ArrayList<>());
            }
            arcBfsMap.get(arcEntity.getStartNodeId()).add(arcEntity.getId());
        });


        arcBfsMap.get(100L).forEach(aLong -> System.out.println("xSTART ARC : "+aLong));
        arcBfsMap.get(3L).forEach(aLong -> System.out.println("xSTART ARC : "+aLong));

    }
}

package MasterThesis.node;
/// Przykład z pliku Node.txt
//1|4|0|0|0|0|15
//2|1|0|0|0|0|15
//3|1|0|0|0|0|15
//104|1|40|47.058|0|0|0.4
//105|1|40|47.058|0|0|0.4
//106|1|40|47.058|0|0|0.4
//107|1|17|20|0|0|0.4
//201|1|50|62.5|0|0|0.4

//1    NodeType type ;             // Typ
//2    Integer  activePower;       // Moc czynna
//3    Double   reactivePower ;    // Moc bierna
//4    Integer  activePowerDev;    // Odchylenie moc czynnej
//5    Integer  reactivePowerDev;  // Odchylenie mocy biernej
//6    Double   voltage;           // Napiecie

import MasterThesis.base.parameters.AppParameters;
import MasterThesis.base.parameters.AppParametersService;

public class NodeFactory {

    public NodeFactory(){}

    public static NodeEntity prepareFromString(String nodeStr){

        String[] nodeArray =  nodeStr.split ( AppParametersService.getInstance().getRegex());

        NodeEntity entity = new NodeEntity(Long.decode(nodeArray[0]));
        entity.setType( NodeType.valueOf(Integer.valueOf(nodeArray[1])));
        entity.setActivePower(Integer.valueOf(nodeArray[2]));
        entity.setReactivePower(Double.valueOf(nodeArray[3]));
        entity.setActivePowerDev(Integer.valueOf(nodeArray[4]));
        entity.setReactivePowerDev(Integer.valueOf(nodeArray[5]));
        entity.setVoltage(Double.valueOf(nodeArray[6]));
        return entity;
    }

}

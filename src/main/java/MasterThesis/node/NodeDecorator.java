package MasterThesis.node;

import MasterThesis.base.entity.EntityDecorator;

public class NodeDecorator implements EntityDecorator<NodeEntity> {

    @Override
    public  String getLabel(NodeEntity node) {
        StringBuilder labelBuilder = new StringBuilder();

        labelBuilder.append("NUMBER").append(":").append(node.getId()).append("\n");
        labelBuilder.append("TYPE").append(":").append(node.getType().toString()).append("\n");
        labelBuilder.append("ACTIVE POWER").append(":").append(node.getActivePower()).append("\n")  ;       // Moc czynna
        labelBuilder.append("REACTIVE POWER").append(":").append(node.getReactivePower()).append("\n")  ;    // Moc bierna
        labelBuilder.append("ACTIVE POWER DEV").append(":").append(node.getActivePowerDev()).append("\n")  ;    // Odchylenie moc czynnej
        labelBuilder.append("REACTIVE POWER DEV").append(":").append(node.getReactivePowerDev()).append("\n")  ;  // Odchylenie mocy biernej
        labelBuilder.append("VOLTAGE").append(":").append(node.getVoltage()).append("\n")  ;            // Napiecie

        return labelBuilder.toString();
    }
}

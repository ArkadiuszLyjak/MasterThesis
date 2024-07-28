package MasterThesis.node;

import MasterThesis.base.entity.EntityDecorator;

public class NodeDecorator implements EntityDecorator<NodeEntity> {

    //region getLabel
    @Override
    public String getLabel(NodeEntity node) {

        StringBuilder labelBuilder = new StringBuilder();

        labelBuilder
                .append("NUMBER")
                .append(":")
                .append(node.getId())
                .append("\n");

        labelBuilder
                .append("TYPE")
                .append(":")
                .append(node.getNodeType().toString())
                .append("\n");

        // Moc czynna
        labelBuilder
                .append("ACTIVE POWER")
                .append(":")
                .append(node.getActivePower())
                .append("\n");

        // Moc bierna
        labelBuilder
                .append("REACTIVE POWER")
                .append(":")
                .append(node.getReactivePower())
                .append("\n");

        // Odchylenie moc czynnej
        labelBuilder
                .append("ACTIVE POWER DEV")
                .append(":")
                .append(node.getActivePowerDeviation())
                .append("\n");

        // Odchylenie mocy biernej
        labelBuilder
                .append("REACTIVE POWER DEV")
                .append(":")
                .append(node.getReactivePowerDeviation())
                .append("\n");

        // Napiecie
        labelBuilder
                .append("VOLTAGE").append(":")
                .append(node.getNominalVoltage())
                .append("\n");

        return labelBuilder.toString();
    }
    //endregion
}

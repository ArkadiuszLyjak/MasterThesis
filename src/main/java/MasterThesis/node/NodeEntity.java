package MasterThesis.node;

import MasterThesis.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeEntity extends BaseEntity {

    NodeType type ;             // Typ
    Integer  activePower;       // Moc czynna
    Double   reactivePower ;    // Moc bierna
    Integer  activePowerDev;    // Odchylenie moc czynnej
    Integer  reactivePowerDev;  // Odchylenie mocy biernej
    Double   voltage;           // Napiecie
    Double   voltagePU;           // Napiecie


    public NodeEntity(Long id) {
        super(id);
    }
}


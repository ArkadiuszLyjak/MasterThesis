package MasterThesis.node;

import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

@Getter
@Setter
public class NodeEntity extends BaseEntity {

    //region node arguments
    // Typ – jeden węzeł o typie 4 (bilansujacy), pozostałe – typ = 1
    NodeType nodeType;

    // Moc czynna – moc czynna odbierana w węźle [kW]
    Double activePower;

    // Moc bierna – moc bierna odbierana w węźle
    // (również moc baterii kondensatorów) [kVar]
    Double reactivePower;

    // Odchylenie moc czynnej = 0
    Integer activePowerDeviation;

    // Odchylenie mocy biernej = 0
    Integer reactivePowerDeviation;

    // Napiecie – napięcie znamionowe węzła sieci [kV]
    Double nominalVoltage;
    //endregion

    //region quantities calculated in relative units [pu]
    Double voltagePU;           // relative voltage value
    Double currentInitialPU;    // the relative value of the current for the null iteration
    Double currentPU;           // relative value of the current

    Double selfConductance;     // self conductance of the node

    // backward computed values from relative values
//    Double voltageRealPU;       // Napiecie
//    Double currentRealPU;       // Prad w wezle
//    Double powerRealPU;

    //endregion

    public NodeEntity(Long id) {
        super(id);
    }

    //region toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        formatter.format("%-21s %d%n", "id:", this.getId());
        formatter.format("%-21s %s%n", "node type:", NodeType.valueOf(nodeType.id));
        formatter.format("%-21s %.2f [kW]%n", "active power:", activePower);
        formatter.format("%-21s %.2f [kVar]%n", "reactive power:", reactivePower);
        formatter.format("%-21s %.2f [kV]%n", "voltage:", nominalVoltage);

        // jednostki [pu]
        formatter.format("%-21s %.2e [PU]%n", "voltage:", voltagePU);
        formatter.format("%-21s %.2e [PU]%n", "current iter '0':", currentInitialPU);
        formatter.format("%-21s %.4e [PU]%n", "current:", currentPU);
//        formatter.format("%-21s %.4e [PU]%n", "voltage real:", voltageRealPU);
//        formatter.format("%-21s %.4e [PU]%n", "current real:", currentRealPU);
//        formatter.format("%-21s %.4e [PU]%n", "power real:", powerRealPU);

        return formatter.toString();
    }
    //endregion
}


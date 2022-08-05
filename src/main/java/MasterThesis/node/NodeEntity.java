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

    // Napiecie – napięcie znamionowe węzła sieci
    Double voltage;
    //endregion

    //region Calculated values
    Double voltagePU;           // Napiecie znamionowe
    Double currentInitialPU;    // prąd w iteracji "0"
    Double currentPU;           // Prad w wezle
    Double voltageRealPU;       // Napiecie
    Double currentRealPU;       // Prad w wezle
    Double powerRealPU;
    //endregion

    public NodeEntity(Long id) {
        super(id);
    }

    //region toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        formatter.format(">--------------------------\n");
        formatter.format("%-21s %d%n", "id:", this.getId());
        formatter.format("%-21s %s%n", "node type:", NodeType.valueOf(nodeType.id));
        formatter.format("%-21s %.2f%n", "active power:", activePower);
        formatter.format("%-21s %.2f%n", "reactive power:", reactivePower);
        formatter.format("%-21s %.2f%n", "voltage:", voltage);
        formatter.format("%-21s %.6f%n", "voltage [PU]:", voltagePU);
        formatter.format("%-21s %f%n", "current initial [PU]:", currentInitialPU);
        formatter.format("%-21s %f%n", "current [PU]:", currentPU);
        formatter.format("%-21s %f%n", "voltage real [PU]:", voltageRealPU);
        formatter.format("%-21s %f%n", "current real [PU]:", currentRealPU);
        formatter.format("%-21s %f%n", "power real [PU]:", powerRealPU);
        formatter.format("--------------------------<\n");

        return formatter.toString();
    }
    //endregion
}


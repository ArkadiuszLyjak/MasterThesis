package MasterThesis.node;

import MasterThesis.base.entity.BaseEntity;
import MasterThesis.base.parameters.AppParameters;
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
    Double selfConductancePU;     // self conductance of the node

    // backward computed values from relative values
    Double voltageReal;       // voltage
    Double currentReal;       // node current
    Double powerReal;         // active power flow in nominated units
    //endregion

    //region Node Entity
    public NodeEntity(Long id) {
        super(id);
    }
    //endregion

    //region toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        String del = AppParameters.getInstance().getDelimiter();

        formatter.format("%d%s", this.getId(), del);
        formatter.format("%d%s", nodeType.getId(), del);
        formatter.format("%.0f%s", activePower, del);
        formatter.format("%.2f%s", reactivePower, del);
        formatter.format("%d%s", activePowerDeviation, del);
        formatter.format("%d%s", reactivePowerDeviation, del);
        formatter.format("%.1f", nominalVoltage);

        return formatter.toString();
    }
    //endregion


}


package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

@Getter
@Setter
public class ArcEntity extends BaseEntity {

    //region arcs arguments
    /**
     * <p>Numer wężła początkowego łuku.</p>
     * <p>Luk oznaczony jako nr węzła początkowego -- nr węzła końcowego.</p>
     *
     * @see #endNode
     */
    Long startNode;

    /**
     * <p>Numer węzła końcowego łuku.</p>
     * <p>Luk oznaczony jako nr węzła początkowego -- nr węzła końcowego.</p>
     *
     * @see #startNode
     */
    Long endNode;

    /**
     * <p>Typ łuku - linia, transformator lub swotch.</p>
     */
    ArcType type;

    /**
     * <p>identyfikator pozycji łuku w odpowiednim katalogu</p>
     */
    Long position;

    /**
     * <p>długość łuku w [m], dla TRANSFORMER i SWITCH = 0</p>
     */
    Double arcLength;

    /**
     * <p>liczba torów równoległych, dla TRANSFORMER i SWITCH = 0</p>
     */
    Integer tracks;

    /**
     * <p>Stan łuku: 1 – czynny, 0 – nieczynny</p>
     */
    Integer condition;

    // wpisujemy wartość 2
    Integer change;
    //endregion

    //region Computed data
    Double resistance;
    Double reactance;
    Double impedance;
    //endregion

    //region Line Values [pu]
    Double resistancePU;
    Double reactancePU;
    Double impedancePU;
    //endregion

    //region Trafo Values [pu]
    Double voltageLowPU;
    Double voltageHighPU;
    Double powerPU;
    double activePowerFlowPU;     // active power flow
    //endregion

    //  Double currentPU;

    public ArcEntity(Long id) {
        super(id);
    }

    //region toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        fmt.format("[%3d] ", super.id);         // ID
        fmt.format("[%3d > ", startNode);       // ID
        fmt.format("%3d] ", endNode);           // ID
        fmt.format("%-11s ", type);             // ID
        fmt.format("%-2d ", position);          // ID
        fmt.format("%7.2f ", arcLength);        // ID
        fmt.format("%d ", tracks);              // ID
        fmt.format("%d ", condition);           // ID
        fmt.format("%d ", change);              // ID
        fmt.format("%.4f ", activePowerFlowPU); // active power flow [PU]

        return sb.toString();
    }
    //endregion

}

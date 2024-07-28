package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import MasterThesis.base.parameters.AppParameters;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

@Getter
@Setter
public class ArcEntity extends BaseEntity {

    AppParameters appParameters = AppParameters.getInstance();

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
    //endregion

    Double powerFlowReal;         // active power flow in nominated units
    Double activePowerFlowPU;     // active power flow

    //region ArcEntity Constructor
    public ArcEntity(Long id) {
        super(id);
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        String del = appParameters.getDelimiter();

        fmt.format("%d%s", super.id, del);              // ID
        fmt.format("%d%s", startNode, del);             // ID
        fmt.format("%d%s", endNode, del);               // ID
        fmt.format("%s%s", type, del);                  // ID
        fmt.format("%d%s", position, del);              // ID
        fmt.format("%.2f%s", arcLength, del);           // ID
        fmt.format("%d%s", tracks, del);                // ID
        fmt.format("%d%s", condition, del);             // ID
        fmt.format("%d%s", change, del);                // ID

        return sb.toString();
    }
    //endregion


}

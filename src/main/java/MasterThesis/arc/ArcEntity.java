package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArcEntity extends BaseEntity {

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

    // Computed data
    Double resistance;
    Double reactance;
    Double impedance;

    // Line Values [pu]
    Double resistancePU;
    Double reactancePU;
    Double impedancePU;

    // Trafo Values [pu]
    Double voltageLowPU;
    Double voltageHighPU;
    Double powerPU;

    //  Double currentPU;

    public ArcEntity(Long id) {
        super(id);
    }

}

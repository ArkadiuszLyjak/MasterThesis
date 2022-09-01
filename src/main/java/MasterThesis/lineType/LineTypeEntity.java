package MasterThesis.lineType;


//Plik LINE_TYPE – katalog kabli i przewodów

import MasterThesis.arc.ArcType;
import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;
import java.util.function.LongFunction;

@Getter
@Setter

public class LineTypeEntity extends BaseEntity {

    //region line arguments
    //    id //  – numer pozycji katalogowej
    //  –kabel=1, przewód linii napowietrznej = 2
    LineKind kind;

    //  – nazwa typu
    String type;

    //  – napięcie znamionowe
    Double voltage;

    //– przekrój roboczy
    Double mainStrandIntersection;

    //– rezystancja jednostkowa
    Double cohesiveUnitResistance;

    // – nieistotne, wpisujemy 0
    Double zeroUnitResistance;

    // – reaktancja jednostkowa
    Double cohesiveUnitReactance;

    // – nieistotne, wpisujemy 0
    Double zeroUnitReactance;

    //– pojemność doziemna
    Double unitCapacitanceToEarth;

    //– pojemność robocza
    Double unitWorkingCapacitance;

    //dla kabli, obciążalność dopuszczalna długotrwale, [A]
    Double longTermLoadCapacity;

    // dla przewodów linii napowietrznych,
    // obciążalność dopuszczalna długotrwale, lato, [A]
    Double longTermSummerLoadCapacity;

    // dla przewodów linii napowietrznych,
    // obciążalność dopuszczalna długotrwale, zima, [A]
    Double longTermWinterLoadCapacity;

    // – nieistotne, wpisujemy 0
    Double shortCircuit1sLoadCapacity;
    //endregion

    //region Constructor
    public LineTypeEntity(Long id) {
        super(id);
    }
    //endregion

    //region toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        fmt.format("%-31s %d\n", "ID arc: ", super.id);
        fmt.format("%-31s %s\n", "Kind: ", kind);
        fmt.format("%-31s %s\n", "Type: ", type);
        fmt.format("%-31s %.2f [kV]\n", "Voltage: ", voltage);
        fmt.format("%-31s %.2f\n", "MAIN_STRAND_INTERSECTION: ", mainStrandIntersection);
        fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_RESISTANCE: ", cohesiveUnitResistance);
        fmt.format("%-31s %.2f\n", "ZERO_UNIT_RESISTANCE: ", zeroUnitResistance);
        fmt.format("%-31s %.2f\n", "COHESIVE_UNIT_REACTANCE: ", cohesiveUnitReactance);
        fmt.format("%-31s %.2f\n", "ZERO_UNIT_REACTANCE: ", zeroUnitReactance);
        fmt.format("%-31s %.2f\n", "UNIT_CAPACITANCE_TO_EARTH: ", unitCapacitanceToEarth);
        fmt.format("%-31s %.2f\n", "UNIT_WORKING_CAPACITANCE: ", unitWorkingCapacitance);
        fmt.format("%-31s %.2f\n", "LONGTERM_LOAD_CAPACITY: ", longTermLoadCapacity);
        fmt.format("%-31s %.2f\n", "LONGTERM_SUMMER_LOAD_CAPACITY: ", longTermSummerLoadCapacity);
        fmt.format("%-31s %.2f\n", "LONGTERM_WINTER_LOAD_CAPACITY: ", longTermWinterLoadCapacity);
        fmt.format("%-31s %.2f\n", "SHORTCIRCUIT_1S_LOAD_CAPACITY: ", shortCircuit1sLoadCapacity);
        fmt.format("\n\n");

        return fmt.toString();
    }
    //endregion
}

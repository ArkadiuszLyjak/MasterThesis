package MasterThesis.lineType;


//Plik LINE_TYPE – katalog kabli i przewodów

import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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

    public LineTypeEntity(Long id) {
        super(id);
    }
}

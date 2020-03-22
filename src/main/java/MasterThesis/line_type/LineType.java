package MasterThesis.line_type;


//Plik LINE_TYPE – katalog kabli i przewodów

import MasterThesis.base.entity.BaseEntity;
import lombok.Data;
@Data
public class LineType extends BaseEntity {
//    id //  – numer pozycji katalogowej
    LineKind kind;  //  –kabel=1, przewód linii napowietrznej = 2
    String type;  //  – nazwa typu
    Double voltage;  //  – napięcie znamionowe
    Double mainStrandIntersection; //– przekrój roboczy
    Double cohesiveUnitResistance; //– rezystancja jednostkowa
    Double zeroUnitResistance;  // – nieistotne, wpisujemy 0
    Double cohesiveUnitReactance;  // – reaktancja jednostkowa
    Double zeroUnitReactance;  // – nieistotne, wpisujemy 0
    Double unitCapacitanceToEarth; //– pojemność doziemna
    Double unitWorkingCapacitance;  //– pojemność robocza
    Double longTermLoadCapacity;  //dla kabli, obciążalność dopuszczalna długotrwale, [A]
    Double longTermSummerLoadCapacity; // dla przewodów linii napowietrznych, obciążalność dopuszczalna długotrwale, lato, [A]
    Double longTermWinterLoadCapacity; // dla przewodów linii napowietrznych, obciążalność Dopuszczalna długotrwale, zima, [A]
    Double shortCircuit1sLoadCapacity; // – nieistotne, wpisujemy 0

    public LineType(Long id) {
        super(id);
    }
}

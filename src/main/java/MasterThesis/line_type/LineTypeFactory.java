package MasterThesis.line_type;

import MasterThesis.base.parameters.AppParameters;
import MasterThesis.base.parameters.AppParametersService;
//0  ID
//1  KIND_ID
//2  TYPE
//3  VOLTAGE
//4  MAIN_STRAND_INTERSECTION
//5  COHESIVE_UNIT_RESISTANCE
//6  ZERO_UNIT_RESISTANCE
//7  COHESIVE_UNIT_REACTANCE
//8  ZERO_UNIT_REACTANCE
//9  UNIT_CAPACITANCE_TO_EARTH
//10  UNIT_WORKING_CAPACITANCE
//11  LONGTERM_LOAD_CAPACITY
//12  LONGTERM_SUMMER_LOAD_CAPACITY
//13  LONGTERM_WINTER_LOAD_CAPACITY
//14  SHORTCIRCUIT_1S_LOAD_CAPACITY

public class LineTypeFactory {

    public static LineType prepareFromString(String lineTypeStr) {
        String[] entityArray = lineTypeStr.split(AppParametersService.getInstance().getRegex());
        LineType entity = new LineType(Long.decode(entityArray[0]));
        entity.setKind(LineKind.valueOf(Integer.valueOf(entityArray[1]))); //kabel=1, przewód linii napowietrznej = 2
        entity.setType(entityArray[2]); // nazwa typu
        entity.setVoltage(Double.valueOf(entityArray[3]));  //  – napięcie znamionowe
        entity.setMainStrandIntersection(Double.valueOf(entityArray[4])); //– przekrój roboczy
        entity.setCohesiveUnitResistance(Double.valueOf(entityArray[5])); //– rezystancja jednostkowa
        entity.setZeroUnitResistance(Double.valueOf(entityArray[6]));  // – nieistotne, wpisujemy 0
        entity.setCohesiveUnitReactance(Double.valueOf(entityArray[7]));  // – reaktancja jednostkowa
        entity.setZeroUnitReactance(Double.valueOf(entityArray[8]));  // – nieistotne, wpisujemy 0
        entity.setUnitCapacitanceToEarth(Double.valueOf(entityArray[9])); //– pojemność doziemna
        entity.setUnitWorkingCapacitance(Double.valueOf(entityArray[10]));  //– pojemność robocza
        entity.setLongTermLoadCapacity(Double.valueOf(entityArray[11]));  //dla kabli, obciążalność dopuszczalna długotrwale, [A]
        entity.setLongTermSummerLoadCapacity(Double.valueOf(entityArray[12])); // dla przewodów linii napowietrznych, obciążalność dopuszczalna długotrwale, lato, [A]
        entity.setLongTermWinterLoadCapacity(Double.valueOf(entityArray[13])); // dla przewodów linii napowietrznych, obciążalność Dopuszczalna długotrwale, zima, [A]
        entity.setShortCircuit1sLoadCapacity(Double.valueOf(entityArray[14])); // – nieistotne, wpisujemy 0
        return entity;
    }
}
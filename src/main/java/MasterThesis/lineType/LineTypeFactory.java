package MasterThesis.lineType;

import MasterThesis.arc_file_tools.FileDataReader;
import MasterThesis.base.parameters.AppParametersService;

import java.util.function.Consumer;

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
//  1|1|3xYHAKXS1x70|15|70|0.364|0|0.08|0|0.25|0.25|175|0|0|0
//  2|1|5xYKY1x400|0.4|400|0.153|0|0.110|0|0.210|0.37|353|0|0|0


public class LineTypeFactory {

    /**
     * <p>Metoda <i>{@code prepareFromString}</i> otrzymuje jako argument string zawierający jeden
     * rekord z pliku z informacjami o <tt>rodzaju linii el-energetycznych</tt>.</p>
     * Use {@link String#toLowerCase()} for conversion to lower case alphabets.
     *
     * @param lineTypeStr String z linią z pliku opisującą jedne rodzaj linii
     *                    <p>
     * @return {@link LineTypeEntity} object
     * @see String#toLowerCase() convertToLowerCase
     * @see "This method performs some function."
     * @see FileDataReader#netFileRead(String, Consumer) how to read from file
     * @see <a href="https://docs.oracle.com/en/java/">Java Dcoumentation</a>
     * @see <a href="http://www.baeldung.com">Baeldung</a>
     * @see String#hashCode() hashCode
     */

    //region prepareFromString
    public static LineTypeEntity prepareFromString(String lineTypeStr) {

        String[] entityArray = lineTypeStr.split(AppParametersService.getInstance().getRegex());

        LineTypeEntity entity = new LineTypeEntity(Long.decode(entityArray[0]));

        // kabel=1, przewód linii napowietrznej = 2
        entity.setKind(LineKind.valueOf(Integer.valueOf(entityArray[1])));

        // nazwa typu
        entity.setType(entityArray[2]);

        // – napięcie znamionowe
        entity.setVoltage(Double.valueOf(entityArray[3]));

        // – przekrój roboczy
        entity.setMainStrandIntersection(Double.valueOf(entityArray[4]));

        // – rezystancja jednostkowa
        entity.setCohesiveUnitResistance(Double.valueOf(entityArray[5]));

        // – nieistotne, wpisujemy 0
        entity.setZeroUnitResistance(Double.valueOf(entityArray[6]));

        // – reaktancja jednostkowa
        entity.setCohesiveUnitReactance(Double.valueOf(entityArray[7]));

        // – nieistotne, wpisujemy 0
        entity.setZeroUnitReactance(Double.valueOf(entityArray[8]));

        // – pojemność doziemna
        entity.setUnitCapacitanceToEarth(Double.valueOf(entityArray[9]));

        // – pojemność robocza
        entity.setUnitWorkingCapacitance(Double.valueOf(entityArray[10]));

        // dla kabli, obciążalność dopuszczalna długotrwale, [A]
        entity.setLongTermLoadCapacity(Double.valueOf(entityArray[11]));

        // dla przewodów linii napowietrznych, obciążalność dopuszczalna długotrwale, lato, [A]
        entity.setLongTermSummerLoadCapacity(Double.valueOf(entityArray[12]));

        // dla przewodów linii napowietrznych, obciążalność Dopuszczalna długotrwale, zima, [A]
        entity.setLongTermWinterLoadCapacity(Double.valueOf(entityArray[13]));

        // – nieistotne, wpisujemy 0
        entity.setShortCircuit1sLoadCapacity(Double.valueOf(entityArray[14]));

        return entity;
    }
    //endregion
}
package MasterThesis.transformer_type;

//pliktransformer_type–katalogtransformatorów

//pozostałe dane nie są potrzebne, wystarczy wpisać 0.
//ID|TYPE|NOMINAL_POWER|NOMINAL_UPPER_VOLTAGE|NOMINAL_LOWER_VOLTAGE|VOLTAGE_INCREASE_RANGE|VOLTAGE_DECREASE_RANGE|NO_OF_ADJUSTMENT_RANGE|CONNECTION_GROUP|LOAD_POWER_LOSS|ACTIVE_IDLE_LOSS|IDLE_CURRENT|SHORTING_VOLTAGE|ZERO_RESISTANCE|ZERO_REAKTANCE
//      10|TOd|630.00|15.75|0.42|0|0|0|0|6.5|0.6|0|6|0|0

import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

@Getter
@Setter

public class TransformerTypeEntity extends BaseEntity {

    //region transformer arguments
    // id  //id– numer pozycji katalogowej
    String type;  //type – nazwa typu

    //nominal_power – moc znamionowa [kva]
    Double nominalPower;

    //nominal_upper_voltage – napięcie znamionowe górne [kv]
    Double nominalUpperVoltage;

    //nominal_lower_voltage – napięcie znamionowe dolne kv]
    Double nominalLowerVoltage;

    //load_power_loss – straty obciążeniowe czynne [kw]
    Double loadPowerLoss;

    //active_idle_loss – straty jałowe czynne [kw]
    Double activeIdleLoss;

    //shorting_voltage – napięcie zwarcia [%]
    Double shortingVoltage;

    Double idleCurrent;
    Double voltageIncreaseRange;
    Double voltageDecreaseRange;
    Double noOfAdjustmentRange;
    Double connectionGroup;
    Double zeroResistance;
    Double zeroReactance;
    //endregion

    //region TransformerTypeEntity
    public TransformerTypeEntity(Long id) {
        super(id);
    }
    //endregion

    //region toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);

        fmt.format("%-31s %d\n", "ID: ", super.id);
        fmt.format("%-31s %s\n", "Type: ", type);
        fmt.format("%-31s %.2f [VA]\n", "nominal power: ", nominalPower);
        fmt.format("%-31s %.2f\n", "nominal Upper Voltage: ", nominalUpperVoltage);
        fmt.format("%-31s %.2f\n", "nominal Lower Voltage: ", nominalLowerVoltage);
        fmt.format("%-31s %.2f\n", "voltage Increase Range: ", voltageIncreaseRange);
        fmt.format("%-31s %.2f\n", "voltage Decrease Range: ", voltageDecreaseRange);
        fmt.format("%-31s %.2f\n", "no Of Adjustment Range: ", noOfAdjustmentRange);
        fmt.format("%-31s %.2f\n", "connection Group: ", connectionGroup);
        fmt.format("%-31s %.2f\n", "load Power Loss: ", loadPowerLoss);
        fmt.format("%-31s %.2f\n", "active Idle Loss: ", activeIdleLoss);
        fmt.format("%-31s %.2f\n", "idle Current: ", idleCurrent);
        fmt.format("%-31s %.2f\n", "shorting Voltage: ", shortingVoltage);
        fmt.format("%-31s %.2f\n", "zero Resistance: ", zeroResistance);
        fmt.format("%-31s %.2f\n", "zero Reactance", zeroReactance);
        fmt.format("\n\n");

        return fmt.toString();
    }
    //endregion

}

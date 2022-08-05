package MasterThesis.transformer_type;

//pliktransformer_type–katalogtransformatorów

//pozostałe dane nie są potrzebne, wystarczy wpisać 0.
//ID|TYPE|NOMINAL_POWER|NOMINAL_UPPER_VOLTAGE|NOMINAL_LOWER_VOLTAGE|VOLTAGE_INCREASE_RANGE|VOLTAGE_DECREASE_RANGE|NO_OF_ADJUSTMENT_RANGE|CONNECTION_GROUP|LOAD_POWER_LOSS|ACTIVE_IDLE_LOSS|IDLE_CURRENT|SHORTING_VOLTAGE|ZERO_RESISTANCE|ZERO_REAKTANCE
//      10|TOd|630.00|15.75|0.42|0|0|0|0|6.5|0.6|0|6|0|0

import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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

    public TransformerTypeEntity(Long id) {
        super(id);
    }

}

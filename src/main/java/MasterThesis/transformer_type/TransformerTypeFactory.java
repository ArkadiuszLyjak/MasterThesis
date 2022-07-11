package MasterThesis.transformer_type;

import MasterThesis.base.parameters.AppParametersService;

public class TransformerTypeFactory {

/*
0 ID
1 TYPE
2 NOMINAL_POWER
3 NOMINAL_UPPER_VOLTAGE
4 NOMINAL_LOWER_VOLTAGE
5 VOLTAGE_INCREASE_RANGE
6 VOLTAGE_DECREASE_RANGE
7 NO_OF_ADJUSTMENT_RANGE
8 CONNECTION_GROUP
9 LOAD_POWER_LOSS
10 ACTIVE_IDLE_LOSS
11 IDLE_CURRENT
12 SHORTING_VOLTAGE
13 ZERO_RESISTANCE
14 ZERO_REAKTANCE
*/

    public static TransformerTypeEntity prepareFromString(String transformerTypeStr) {
        String[] entityArray = transformerTypeStr.split(AppParametersService.getInstance().getRegex());
        TransformerTypeEntity entity = new TransformerTypeEntity(Long.decode(entityArray[0]));
        entity.setType(entityArray[1]);
        entity.setNominalPower(Double.valueOf(entityArray[2]));
        entity.setNominalUpperVoltage(Double.valueOf(entityArray[3]));
        entity.setNominalLowerVoltage(Double.valueOf(entityArray[4]));
        entity.setVoltageIncreaseRange(Double.valueOf(entityArray[5]));
        entity.setVoltageDecreaseRange(Double.valueOf(entityArray[6]));
        entity.setNoOfAdjustmentRange(Double.valueOf(entityArray[7]));
        entity.setConnectionGroup(Double.valueOf(entityArray[8]));
        entity.setLoadPowerLoss(Double.valueOf(entityArray[9]));
        entity.setActiveIdleLoss(Double.valueOf(entityArray[10]));
        entity.setIdleCurrent(Double.valueOf(entityArray[11]));
        entity.setShortingVoltage(Double.valueOf(entityArray[12]));
        entity.setZeroResistance(Double.valueOf(entityArray[13]));
        entity.setZeroReactance(Double.valueOf(entityArray[14]));
        return entity;
    }
}

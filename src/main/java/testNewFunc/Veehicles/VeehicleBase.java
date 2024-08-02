package testNewFunc.Veehicles;

import lombok.Getter;
import lombok.Setter;

public class VeehicleBase {
    @Getter
    @Setter
    private int passengers;
    @Getter
    @Setter
    private int fuelCup;
    @Getter
    @Setter
    private long fuelConsumption;
    @Getter
    @Setter
    private long range;

    public VeehicleBase() {
        passengers = 0;
        fuelCup = 0;
        fuelConsumption = 0;
        range = calculateRange();
    }

    public VeehicleBase(int passengers, int fuelCup, int fuelConsumption) {
        this.passengers = passengers;
        this.fuelCup = fuelCup;
        this.fuelConsumption = fuelConsumption;
    }

    public long calculateRange() {
        return fuelCup / fuelConsumption * 100;
    }

}

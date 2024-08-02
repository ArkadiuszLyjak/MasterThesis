package testNewFunc.Veehicles;

public class Truck extends VeehicleBase {
    int cargoCup;

    Truck(int passengers, int fuelCup, int fuelConsumption, int cargoCup) {
        super(passengers, fuelCup, fuelConsumption);
        this.cargoCup = cargoCup;
    }

    public int getCargoCup() {
        return cargoCup;
    }

    void printInfoTruck() {
        System.out.println("Passengers : " + getPassengers());
        System.out.println("fuelCup: " + getFuelCup());
        System.out.println("Fuel Consumption : " + getFuelConsumption());
        System.out.println("Cargo Cup : " + getCargoCup());
        System.out.println();
    }


}


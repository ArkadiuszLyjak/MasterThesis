package testNewFunc.Veehicles;

import java.util.Map;

public class Car extends VeehicleBase {

    String color;
    String engine;
    String brand;
    String model;
    Map<String, String> passengersNames;

    public Car(
            int passengers,
            int fuelCup,
            int fuelConsumption,
            String color,
            String engine,
            String brand,
            String model) {
        super(passengers, fuelCup, fuelConsumption);
        this.color = color;
        this.engine = engine;
        this.brand = brand;
        this.model = model;
    }


    public void displayCar() {
        System.out.println(color);
    }
}

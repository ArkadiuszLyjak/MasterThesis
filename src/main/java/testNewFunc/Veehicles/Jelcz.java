package testNewFunc.Veehicles;

public class Jelcz extends Truck {
    private String brand;

    public Jelcz(int passengers, int fuelCup, int fuelConsumption, int cargoCup, String brand) {
        super(passengers, fuelCup, fuelConsumption, cargoCup);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    void printInfoTruck() {
        super.printInfoTruck();
        System.out.println(brand);
    }

    /*public Jelcz(int passengers, int fuelCup, int fuelConsumption, int cargoCup, String brand) {
        super(passengers, fuelCup, fuelConsumption, cargoCup);
        this.brand = brand;
    }*/

    /*public Jelcz(int passengers, int fuelCup, int fuelConsumption, int cargoCup, String brand) {
        super(passengers, fuelCup, fuelConsumption, cargoCup);
        this.brand = brand;
    }*/

    /*public Jelcz(int passengers, int fuelCup, int fuelConsumption, int cargoCup, String brand) {
        super(passengers, fuelCup, fuelConsumption, cargoCup);
        this.brand = brand;
    }*/

    /*public Jelcz(int passengers, int fuelCup, int fuelConsumption, int cargoCup, String brand) {
        super(passengers, fuelCup, fuelConsumption, cargoCup);
        this.brand = brand;
    }*/
}

package testNewFunc.Veehicles;

import java.util.Arrays;
import java.util.Random;

class AccesDemo {

    private int alpha;
    private int beta;
    int gama;

    //region manyArgs
    static void manyArgs(int... args) {
        System.out.println("Lista argumentów: " + Arrays.toString(args) + args.length);
    }

    static void manyArgs(String str, int ... args) {
        System.out.println("Lista argumentów: " + Arrays.toString(args) + str);
        System.out.println(args.length);
    }
    //endregion

    //region Description
    void printArgs() {
        System.out.println("Wybierz co ma byc drukowane");
    }

    void printArgs(int alpha) {
        System.out.println(this.alpha);
        System.out.println();
    }

    void printArgs(int alpha, int beta) {
        System.out.println(this.alpha);
        System.out.println(this.beta);
        System.out.println();
    }

    void printArgs(int alpha, int beta, int gama) {
        System.out.println(this.alpha);
        System.out.println(this.beta);
        System.out.println(this.gama);
        System.out.println();
    }
    //endregion

    //region getters
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public void setGama(int gama) {
        this.gama = gama;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    public int getGama() {
        return gama;
    }
    //endregion


}

public class VeehicleApp {
    public static void main(String[] args) {
        Truck truck = new Truck(2, 200, 23, 40);
        truck.printInfoTruck();

        Jelcz jelcz = new Jelcz(12, 300, 23, 30, "Jelczunio");
        jelcz.printInfoTruck();








        //region Description
        Car car = new Car(
                4,
                45,
                10,
                "red",
                "diesel",
                "Toyota",
                "Corolla");

//        car.displayCar();
        //endregion


    }
}

package MasterThesis.data_calc;
// Change calculated data to per unit / jednostki wzgledne
public class BaseValues {
    public static Double power_base = 1000.0; //kVA
    public static Double voltage_base = 0.42; // kV
    public static Double current_base = power_base / ((Math.sqrt(3.0)) * voltage_base);
    public static Double impedance_base = Math.pow(voltage_base, 2.0) / power_base;

}

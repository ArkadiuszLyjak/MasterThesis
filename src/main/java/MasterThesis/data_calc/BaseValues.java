package MasterThesis.data_calc;

// Change calculated data to per unit / jednostki wzgledne
public class BaseValues {

    // Obliczanie rozpływów mocy w dużych systemach elektroenergetycznych PDF
    //    public static Double power_base = 100_000_000.0; // 100 MVA
    public static Double powerBase = 1_000_000.0;       // 1 MVA
    public static Double voltageBase = 420.0;           // V
    public static Double currentBase = powerBase / ((Math.sqrt(3.0)) * voltageBase);
    public static Double impedanceBase = Math.pow(voltageBase, 2.0) / powerBase;
    public static Double epsilon = 0.00000000001;

}

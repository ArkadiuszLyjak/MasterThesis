package MasterThesis.el_net.directMethod;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter

public class InterimEntity {

    long iterateMaxNumber;                     // emergency "valve"
    int a;                                     // error indicator
    double deltaCurrentThisIterPresentNode;    // bieżąca wart. poprawki prądu
    double currentPU;                          // prąd w węźle [PU]
    long nodeNumber;                           // nr węzła, dla którego prow. są obliczenia
    double u_i;                                // napięcie w tymże węźle w [PU]
    double g_ii;                               // konduktancja własna węzła [PU]
    double item_Ii;                            // prąd w węźle do obl. poprawki prądu delta I
    double item_Ui_Gii;                        // pierwszy człon licznika w obl. poprawki prądu

    Map<Long, NeighborEntity> neighborCalcMap;

    double item_sqrt;                          // pierwiastek kw. z 3
    double deltaVoltageThisIterPresentNode;    // zmiana napięcia w obl. węźle [PU]
    double voltageThisIterPresentNode;         // obl. nowe napięcie w węźle [PU]

    public InterimEntity(IterateMapBuilder builder) {
        iterateMaxNumber = builder.iterateMaxNumber;
        a = builder.a;
        deltaCurrentThisIterPresentNode = builder.deltaCurrentThisIterPresentNode;
        currentPU = builder.currentPU;
        nodeNumber = builder.nodeNumber;
        u_i = builder.u_i;
        g_ii = builder.g_ii;
        item_Ii = builder.item_Ii;
        item_Ui_Gii = builder.item_Ui_Gii;
        neighborCalcMap = new LinkedHashMap<>(builder.neighborCalcMap);
        deltaVoltageThisIterPresentNode = builder.deltaVoltageThisIterPresentNode;
        voltageThisIterPresentNode = builder.voltageThisIterPresentNode;
    }
}

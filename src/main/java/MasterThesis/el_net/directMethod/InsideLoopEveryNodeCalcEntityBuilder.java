package MasterThesis.el_net.directMethod;

import java.util.LinkedHashMap;
import java.util.Map;

public class InsideLoopEveryNodeCalcEntityBuilder {

    //region params
    long iterateMaxNumber;                     // emergency "valve"
    long nodeNumber;                           // nr węzła, dla którego prow. są obliczenia
    double currentPU;                          // prąd w węźle [PU]
    double u_i;                                // napięcie w tymże węźle w [PU]
    double g_ii;                               // konduktancja własna węzła [PU]
    double item_Ii;                            // prąd w węźle do obl. poprawki prądu delta I
    double item_Ui_Gii;                        // pierwszy człon licznika w obl. poprawki prądu
    Map<Long, NeighborEntity> neighborCalcMap;
    long nodeUniqueNeighborFWD;                // unikalny nr węzła-sąsiada (liczba)
    double u_j;                                // napięcie w węźle-sąsiedzie w [PU]
    double g_ij;                               // konduktancja między węzłami [PU]
    double item_sum;                           // drugi człon licznika w obl. poprawki prądu
    double deltaCurrentThisIterPresentNode;    // bieżąca wart. poprawki prądu
    double item_sqrt;                          // pierwiastek kw. z 3
    double deltaVoltageThisIterPresentNode;    // zmiana napięcia w obl. węźle [PU]
    double voltageThisIterPresentNode;         // obl. nowe napięcie w węźle [PU]
    int a;                                     // error indicator
    //endregion

    //region constructor
    public InsideLoopEveryNodeCalcEntityBuilder(long iterateMaxNumber) {
        this.iterateMaxNumber = iterateMaxNumber;
        neighborCalcMap = new LinkedHashMap<>();
    }
    //endregion

    //region setters/builders
    //region nodeNumber
    public InsideLoopEveryNodeCalcEntityBuilder nodeNumber(long nodeNumber) {
        this.nodeNumber = nodeNumber;
        return this;
    }
    //endregion

    //region errorIndicator
    public InsideLoopEveryNodeCalcEntityBuilder errorIndicator(int err) {
        a = err;
        return this;
    }
    //endregion

    //region deltaCurrent
    public InsideLoopEveryNodeCalcEntityBuilder deltaCurrentThisIterPresentNode(double deltaCurrentThisIterPresentNode) {
        this.deltaCurrentThisIterPresentNode = iterateMaxNumber;
        return this;
    }
    //endregion

    //region currentPU
    public InsideLoopEveryNodeCalcEntityBuilder currentPU(double currentPU) {
        this.currentPU = currentPU;
        return this;
    }
    //endregion

    //region nodeVoltage
    public InsideLoopEveryNodeCalcEntityBuilder u_i(double u_i) {
        this.u_i = u_i;
        return this;
    }
    //endregion

    //region selfConductance
    public InsideLoopEveryNodeCalcEntityBuilder g_ii(double g_ii) {
        this.g_ii = g_ii;
        return this;
    }
    //endregion

    //region item_Ii
    public InsideLoopEveryNodeCalcEntityBuilder item_Ii(double item_Ii) {
        this.item_Ii = item_Ii;
        return this;
    }
    //endregion

    //region item_Ui_Gii
    public InsideLoopEveryNodeCalcEntityBuilder item_Ui_Gii(double item_Ui_Gii) {
        this.item_Ui_Gii = item_Ui_Gii;
        return this;
    }
    //endregion

    //region item_sum
    public InsideLoopEveryNodeCalcEntityBuilder item_sum(double item_sum) {
        this.item_sum = item_sum;
        return this;
    }
    //endregion

    //region neighborMapCalc
    public InsideLoopEveryNodeCalcEntityBuilder neighborMapCalc(NeighborEntity neighborEntity) {
        this.neighborCalcMap.put(neighborEntity.nodeUniqueNeighborFWD, neighborEntity);
        return this;
    }
    //endregion

    //region nodeUniqueNeighborFWD
    public InsideLoopEveryNodeCalcEntityBuilder nodeUniqueNeighborFWD(long nodeUniqueNeighborFWD) {
        this.nodeUniqueNeighborFWD = nodeUniqueNeighborFWD;
        return this;
    }
    //endregion

    //region u_j
    public InsideLoopEveryNodeCalcEntityBuilder u_j(double u_j) {
        this.u_j = u_j;
        return this;
    }
    //endregion

    //region g_ij
    public InsideLoopEveryNodeCalcEntityBuilder g_ij(double g_ij) {
        this.g_ij = g_ij;
        return this;
    }
    //endregion

    //region deltaVoltage
    public InsideLoopEveryNodeCalcEntityBuilder deltaVoltageThisIterPresentNode(double deltaVoltage) {
        this.deltaVoltageThisIterPresentNode = deltaVoltage;
        return this;
    }
    //endregion

    //region voltage
    public InsideLoopEveryNodeCalcEntityBuilder voltageThisIterPresentNode(double voltageThisIterPresentNode) {
        this.voltageThisIterPresentNode = voltageThisIterPresentNode;
        return this;
    }
    //endregion
    //endregion

    //region build
    public InsideLoopEveryNodeCalcEntity build() {
        return new InsideLoopEveryNodeCalcEntity(this);
    }
    //endregion
}

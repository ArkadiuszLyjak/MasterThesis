package MasterThesis.el_net.InsideCalcContener;

public class NeighborEntityBuilder {

    long nodeUniqueNeighbor;                    // unikalny nr węzła-sąsiada (liczba) ✓
    double u_j;                                 // napięcie w węźle-sąsiedzie w [PU] ✓
    double g_ij;                                // konduktancja między węzłami [PU] ✓
    double item_sum;                            // drugi człon licznika w obl. poprawki prądu ✓

    public NeighborEntityBuilder(long nodeUniqueNeighbor) {
        this.nodeUniqueNeighbor = nodeUniqueNeighbor;
    }

    //region NeighborEntityBuilder
    public NeighborEntityBuilder u_j(double u_j) {
        this.u_j = u_j;
        return this;
    }
    //endregion

    //region NeighborEntityBuilder
    public NeighborEntityBuilder g_ij(double g_ij) {
        this.g_ij = g_ij;
        return this;
    }
    //endregion

    //region NeighborEntityBuilder
    public NeighborEntityBuilder item_sum(double item_sum) {
        this.item_sum = item_sum;
        return this;
    }
    //endregion

    //region NeighborEntity
    public NeighborEntity build() {
        return new NeighborEntity(this);
    }
    //endregion
}

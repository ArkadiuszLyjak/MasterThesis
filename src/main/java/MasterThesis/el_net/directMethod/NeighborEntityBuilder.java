package MasterThesis.el_net.directMethod;

public class NeighborEntityBuilder {

    long nodeUniqueNeighborFWD;                // unikalny nr węzła-sąsiada (liczba)
    double u_j;                                // napięcie w węźle-sąsiedzie w [PU]
    double g_ij;                               // konduktancja między węzłami [PU]
    double item_sum;                           // drugi człon licznika w obl. poprawki prądu

    public NeighborEntityBuilder(long nodeUniqueNeighborFWD) {
        this.nodeUniqueNeighborFWD = nodeUniqueNeighborFWD;
    }

    public NeighborEntityBuilder u_j(double u_j) {
        this.u_j = u_j;
        return this;
    }

    public NeighborEntityBuilder g_ij(double g_ij) {
        this.g_ij = g_ij;
        return this;
    }

    public NeighborEntityBuilder item_sum(double item_sum) {
        this.item_sum = item_sum;
        return this;
    }

    public NeighborEntity build() {
        return new NeighborEntity(this);
    }
}

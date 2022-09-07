package MasterThesis.el_net.directMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NeighborEntity {

    long nodeUniqueNeighborFWD;                // unikalny nr węzła-sąsiada (liczba)
    double u_j;                                // napięcie w węźle-sąsiedzie w [PU]
    double g_ij;                               // konduktancja między węzłami [PU]
    double item_sum;                           // drugi człon licznika w obl. poprawki prądu

    public NeighborEntity(NeighborEntityBuilder builder) {
        this.nodeUniqueNeighborFWD = builder.nodeUniqueNeighborFWD;
        this.u_j = builder.u_j;
        this.g_ij = builder.g_ij;
        this.item_sum = builder.item_sum;
    }

}
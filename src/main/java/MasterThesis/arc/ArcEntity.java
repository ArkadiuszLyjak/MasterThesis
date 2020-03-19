package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import lombok.Data;

@Data
public class ArcEntity  extends BaseEntity {

    Long startNode;   //Pocz
    Long endNode;   //Konc
    ArcType type;   //Typ
    Integer position;  //Pozycja
    Double arcLength;   //Dlugosc
    Integer lines;   //Tory
    Integer state;   //Stan
    Integer changes;   //Zmiana

    //2|2|4|LINE|8|9000|1|1|2
    //11|3|11|LINE|1|76.28|1|1|2
    //12|3|12|LINE|1|325.9|1|1|2


    public ArcEntity(Long id) {
        super(id);
    }

}

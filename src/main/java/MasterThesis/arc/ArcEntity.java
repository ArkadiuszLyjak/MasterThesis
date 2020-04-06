package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import lombok.Data;

@Data
public class ArcEntity  extends BaseEntity {

    Long startNodeId;   //Pocz
    Long endNodeId;   //Konc
    ArcType type;   //Typ
    Long position;  //Pozycja
    Double arcLength;   //Dlugosc
    Integer lines;   //Tory
    Integer state;   //Stan
    Integer changes;   //Zmiana

    public ArcEntity(Long id) {
        super(id);
    }

}

package MasterThesis.arc;


import MasterThesis.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArcEntity  extends BaseEntity {

    Long startNodeId;   //Pocz
    Long endNodeId;   //Konc
    ArcType type;   //Typ
    Long position;  //Pozycja
    Double arcLength;   //Dlugosc
    Integer lines;   //Tory
    Integer state;   //Stan
    Integer changes;   //Zmiana
    //-----------------------------------
    // Dane wyliczane
    Double reactance ;
    Double resistance;
    Double impedance;

    // Line Values
    Double reactancePU ;
    Double resistancePU;
    Double impedancePU;

    // Trafo Values
    Double powerPU;
    Double voltageHighPU;
    Double voltageLowPU;

  //  Double currentPU;

    public ArcEntity(Long id) {
        super(id);
    }

}

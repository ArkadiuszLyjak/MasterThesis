package MasterThesis.node;

//Typ – jeden węzeł o typie 4 (bilansujący), pozostałe – typ = 1

import java.util.Objects;

public enum NodeType {

    COMPENSATORY_NODE(4),  // węzeł bilansujący
    OTHER_NODE(1);         // węzły pozostałe

    Integer id;

    NodeType(Integer id) {
        this.id = id;
    }

    //region valueOf
    public static NodeType valueOf(Integer _id) {
        if (Objects.equals(_id, COMPENSATORY_NODE.id)) return COMPENSATORY_NODE;
        //TODO Rzucić wyjątek jeżeli inny niż 4 i 1
        return OTHER_NODE;
    }
    //endregion
}
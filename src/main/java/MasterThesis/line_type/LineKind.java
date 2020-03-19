package MasterThesis.line_type;

import MasterThesis.node.NodeType;

public enum LineKind {

    //–kabel=1, przewód linii napowietrznej = 2

    CABLE(1) ,
    OVERHEAD_LINE(3);
    Integer id;

    LineKind(Integer id){this.id = id;}
    public static LineKind valueOf(Integer _id){
        if (_id == CABLE.id) return CABLE;
        if (_id == OVERHEAD_LINE.id) return OVERHEAD_LINE;
        //TODO Rzucić wyjątek jeżlei inny niż 1,3
        return null;
    }

}

package MasterThesis.node;

//Typ – jeden węzeł o typie 4 (bilansujący), pozostałe – typ = 1

public enum  NodeType {
    COMPENSATORY_NODE(4),  // węzeł bilansujący
    OTHER_NODE(1);         // węzeły pozostałe
    Integer id;
    NodeType(Integer id){ this.id = id;}

    public static NodeType valueOf(Integer _id){
        if (_id == COMPENSATORY_NODE.id) return COMPENSATORY_NODE;
        //TODO Rzucić wyjątek jeżlei inny niż 4 i 1
        return OTHER_NODE;
    }
}
package MasterThesis.lineType;

public enum LineKind {

    // wire = 1,
    // electrical overhead wire = 2

    CABLE(1),
    OVERHEAD(2);

    final Integer id;

    LineKind(Integer id) {
        this.id = id;
    }

    //region valueOf
    public static LineKind valueOf(Integer _id) {
        if (_id == CABLE.id) return CABLE;
        if (_id == OVERHEAD.id) return OVERHEAD;
        //TODO Rzucić wyjątek jeżlei inny niż 1,2
        return null;
    }
    //endregion

    public Integer getId() {
        return id;
    }
}

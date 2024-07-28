package MasterThesis.base.entity;

import lombok.Data;

@Data
public abstract class BaseEntity {
    protected Long id;

    //region BaseEntity
    public BaseEntity(Long id) {
        this.id = id;
    }
    //endregion

    //region getId
    public Long getId() {
        return id;
    }
    //endregion

    //region setId
    public void setId(Long id) {
        this.id = id;
    }
    //endregion
}

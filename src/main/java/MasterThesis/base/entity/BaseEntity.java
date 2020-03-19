package MasterThesis.base.entity;

import lombok.Data;

@Data
public abstract class BaseEntity {
    protected Long id;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public BaseEntity(Long id) {
        this.id = id;
    }
}

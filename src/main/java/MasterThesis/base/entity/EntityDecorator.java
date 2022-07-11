package MasterThesis.base.entity;

public interface EntityDecorator <T extends BaseEntity>{
    public String getLabel(T t);
}

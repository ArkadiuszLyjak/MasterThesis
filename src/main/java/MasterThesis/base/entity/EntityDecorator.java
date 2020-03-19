package MasterThesis.base.entity;

import MasterThesis.base.entity.BaseEntity;

public interface EntityDecorator <T extends BaseEntity>{
    public String getLabel(T t);
}

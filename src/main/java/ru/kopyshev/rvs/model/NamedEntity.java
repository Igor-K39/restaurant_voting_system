package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class NamedEntity extends BaseEntity {

    protected String name;

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "AbstractNamedEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
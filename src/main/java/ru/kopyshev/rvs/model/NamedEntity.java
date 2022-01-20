package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class NamedEntity extends BaseEntity {

    @NotBlank
    @Size(min = 2, max = 80)
    @Column(name = "name", nullable = false)
    protected String name;

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
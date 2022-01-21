package ru.kopyshev.rvs.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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
}
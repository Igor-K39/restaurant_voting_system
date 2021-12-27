package ru.kopyshev.rvs.model;

import lombok.*;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.HasId;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements HasId {
    public static final int START_SEQ = 100_000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer id() {
        Assert.notNull(id, "id must not be null");
        return id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "id=" + id +
                '}';
    }
}
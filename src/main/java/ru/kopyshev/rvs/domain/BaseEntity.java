package ru.kopyshev.rvs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.HasId;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements HasId {
    public static final int START_SEQ = 100_000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public Integer id() {
        Assert.notNull(id, "id must not be null");
        return id;
    }

    public boolean isNew() {
        return id == null;
    }
}
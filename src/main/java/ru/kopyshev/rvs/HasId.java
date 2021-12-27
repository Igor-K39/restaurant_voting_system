package ru.kopyshev.rvs;

import org.springframework.util.Assert;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }

    default Integer id() {
        Integer id = getId();
        Assert.notNull(id, "id must not be null");
        return id;
    }
}


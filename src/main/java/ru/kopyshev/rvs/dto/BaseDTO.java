package ru.kopyshev.rvs.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kopyshev.rvs.HasId;

@Data
@NoArgsConstructor
public class BaseDTO implements HasId {

    protected Integer id;

    public BaseDTO(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }
}
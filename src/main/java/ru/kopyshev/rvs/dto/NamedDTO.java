package ru.kopyshev.rvs.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class NamedDTO extends BaseDTO {

    @NotBlank
    protected String name;

    public NamedDTO(Integer id, String name) {
        super(id);
        this.name = name;
    }
}

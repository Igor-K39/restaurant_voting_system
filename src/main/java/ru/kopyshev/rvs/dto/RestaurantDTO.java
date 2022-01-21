package ru.kopyshev.rvs.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class RestaurantDTO extends NamedDTO {

    @NotBlank
    @Size(min = 8, max = 120)
    protected String address;

    @NotBlank
    @Size(min = 8, max = 80)
    protected String website;

    public RestaurantDTO(RestaurantDTO restaurant) {
        this(restaurant.id, restaurant.name, restaurant.address, restaurant.website);
    }

    public RestaurantDTO(String name, String address, String website) {
        this(null, name, address, website);
    }

    public RestaurantDTO(Integer id, String name, String address, String website) {
        super(id, name);
        this.address = address;
        this.website = website;
    }
}

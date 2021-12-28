package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RestaurantDTO that = (RestaurantDTO) o;
        return Objects.equals(address, that.address) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, website);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "address='" + address + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

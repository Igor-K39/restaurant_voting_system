package ru.kopyshev.rvs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends NamedEntity {

    private String address;

    private String website;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.address, r.website);
    }

    public Restaurant(String name, String address, String website) {
        this(null, name, address, website);
    }

    public Restaurant(Integer id, String name, String address, String website) {
        super(id, name);
        this.address = address;
        this.website = website;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
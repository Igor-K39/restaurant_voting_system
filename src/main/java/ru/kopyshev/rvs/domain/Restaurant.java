package ru.kopyshev.rvs.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
@Access(AccessType.FIELD)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restaurant extends NamedEntity {

    @NotBlank
    @Size(min = 8, max = 120)
    @Column(name = "address")
    private String address;

    @NotBlank
    @Size(min = 8, max = 80)
    @Column(name = "website")
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
}
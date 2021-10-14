package ru.kopyshev.rvs.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User extends NamedEntity {

    private String email;

    private String password;

    private boolean enabled;

    private Date registered = new Date();

    private Set<Role> roles = Set.of();

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.enabled, u.registered, u.roles);
    }

    public User(String name, String email, String password, Set<Role> roles) {
        this(null, name, email, password, true, new Date(), roles);
    }

    public User(Integer id, String name, String email, String password, Set<Role> roles) {
        this(id, name, email, password, true, new Date(), roles);
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public Set<Role> getRoles() {
        return EnumSet.copyOf(roles);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = (roles != null) ? roles : Set.of();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }
}
package ru.kopyshev.rvs.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User extends NamedEntity {

    @Email
    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled;

    @NotNull
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private Date registered = new Date();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
        return Set.copyOf(roles);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = (roles != null) ? roles : Set.of();
    }
}
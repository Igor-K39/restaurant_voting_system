package ru.kopyshev.rvs.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends NamedDTO {

    @Email
    @NotBlank
    @Size(max = 100)
    protected String email;

    @NotBlank
    @Size(min = 5, max = 100)
    protected String password;

    protected boolean enabled = true;

    @NotNull
    protected Date registered;

    @NotNull
    protected Set<Role> roles = new HashSet<>();

    public UserDTO(UserDTO user) {
        this(user.id, user.name, user.email, user.password, user.enabled, user.registered, user.roles);
    }

    public UserDTO(Integer id, String name, String email, String password, Role... roles) {
        this(id, name, email, password, true, new Date(), Objects.isNull(roles) ? new HashSet<>() : Arrays.asList(roles));
    }

    public UserDTO(String name, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        this(null, name, email, password, enabled, registered, roles);
    }

    public UserDTO(Integer id, String name, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
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

    public void setRoles(Collection<Role> roles) {
        Assert.notNull(roles, "Roles must not be null");
        this.roles.clear();
        this.roles.addAll(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userTo = (UserDTO) o;
        return enabled == userTo.enabled && Objects.equals(email, userTo.email) && Objects.equals(password, userTo.password) && Objects.equals(registered, userTo.registered) && Objects.equals(roles, userTo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, enabled, registered, roles);
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }

}

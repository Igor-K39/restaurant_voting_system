package ru.kopyshev.rvs.dto.user;

import lombok.*;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.domain.Role;
import ru.kopyshev.rvs.dto.NamedDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDTO extends NamedDTO {

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    private boolean enabled = true;

    @NotNull
    private Date registered = new Date();

    @NotNull
    private Set<Role> roles = new HashSet<>();

    public UserDTO() {
        roles.add(Role.USER);
    }

    public UserDTO(UserDTO userDTO) {
        this(userDTO.id, userDTO.name, userDTO.email, userDTO.password, userDTO.enabled, userDTO.registered, userDTO.roles);
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
        Assert.notEmpty(roles, "Roles must not be empty");
        this.roles.clear();
        this.roles.addAll(roles);
    }
}

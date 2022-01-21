package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.dto.user.UserDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper userMapper;

    public UserMapper() {
        this.userMapper = new ModelMapper();
    }

    @PostConstruct
    public void setup() {
        userMapper.createTypeMap(User.class, UserDTO.class);
    }

    public UserDTO toDTO(User user) {
        return userMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return userMapper.map(userDTO, User.class);
    }

    public List<UserDTO> toDTO(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

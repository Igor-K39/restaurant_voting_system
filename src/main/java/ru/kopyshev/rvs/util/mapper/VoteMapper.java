package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.domain.Restaurant;
import ru.kopyshev.rvs.domain.User;
import ru.kopyshev.rvs.domain.Vote;
import ru.kopyshev.rvs.dto.VoteDTO;
import ru.kopyshev.rvs.repository.RestaurantRepository;
import ru.kopyshev.rvs.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteMapper {
    private final ModelMapper voteMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteMapper(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void setup() {
        voteMapper.createTypeMap(Vote.class, VoteDTO.class)
                .addMappings(m -> m.map(source -> source.getUser().getId(), VoteDTO::setUserId))
                .addMappings(m -> m.map(source -> source.getRestaurant().getId(), VoteDTO::setRestaurantId));

        Converter<VoteDTO, Vote> toEntityPostConverter = ctx -> {
            User user = userRepository.getById(ctx.getSource().getUserId());
            Restaurant restaurant = restaurantRepository.getById(ctx.getSource().getRestaurantId());
            Vote vote = ctx.getDestination();
            vote.setUser(user);
            vote.setRestaurant(restaurant);
            return vote;
        };
        voteMapper.createTypeMap(VoteDTO.class, Vote.class)
                .addMappings(m -> m.skip(Vote::setUser))
                .addMappings(m -> m.skip(Vote::setRestaurant))
                .setPostConverter(toEntityPostConverter);
    }

    public Vote toEntity(VoteDTO voteDTO) {
        return voteMapper.map(voteDTO, Vote.class);
    }

    public VoteDTO toDTO(Vote vote) {
        return voteMapper.map(vote, VoteDTO.class);
    }

    public List<VoteDTO> toDTO(List<Vote> voteDTOs) {
        return voteDTOs.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

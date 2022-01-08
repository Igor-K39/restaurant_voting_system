package ru.kopyshev.rvs.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kopyshev.rvs.model.Vote;
import ru.kopyshev.rvs.repository.CrudRestaurantRepository;
import ru.kopyshev.rvs.repository.CrudUserRepository;
import ru.kopyshev.rvs.to.VoteDTO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoteMapper {
    private final ModelMapper voteMapper;
    private final CrudUserRepository userRepository;
    private final CrudRestaurantRepository restaurantRepository;

    public VoteMapper(CrudUserRepository userRepository, CrudRestaurantRepository restaurantRepository) {
        this.voteMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostConstruct
    public void setup() {
        voteMapper.createTypeMap(Vote.class, VoteDTO.class)
                .addMappings(m -> m.map(source -> source.getUser().getId(), VoteDTO::setUserId))
                .addMappings(m -> m.map(source -> source.getRestaurant().getId(), VoteDTO::setRestaurantId));

        voteMapper.createTypeMap(VoteDTO.class, Vote.class)
                .addMappings(m -> m.skip(Vote::setUser))
                .addMappings(m -> m.skip(Vote::setRestaurant));
    }

    public Vote getEntity(VoteDTO voteDTO) {
        Vote vote = voteMapper.map(voteDTO, Vote.class);
        vote.setUser(userRepository.getById(voteDTO.getUserId()));
        vote.setRestaurant(restaurantRepository.getById(voteDTO.getRestaurantId()));
        return vote;
    }

    public VoteDTO getDTO(Vote vote) {
        return voteMapper.map(vote, VoteDTO.class);
    }

    public List<VoteDTO> getDTO(List<Vote> voteDTOs) {
        return voteDTOs.stream().map(this::getDTO).collect(Collectors.toList());
    }
}
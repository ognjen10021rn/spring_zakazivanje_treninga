package rs.ognjen_uros.spring_zakazivanje_treninga.mapper;

import org.springframework.stereotype.Component;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Sala;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.SalaDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserCreateDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.RoleRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.TrainingTypeRepository;

@Component
public class SalaMapper {

    private TrainingTypeRepository trainingTypeRepository;
    public SalaMapper(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    public SalaDto userToUserDto(Sala sala) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setEmail(user.getEmail());
//        userDto.setFirstName(user.getFirstName());
//        userDto.setLastName(user.getLastName());
//        userDto.setUsername(user.getUsername());
        return null;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
//        User user = new User();
//        user.setEmail(userCreateDto.getEmail());
//        user.setFirstName(userCreateDto.getFirstName());
//        user.setLastName(userCreateDto.getLastName());
//        user.setUsername(userCreateDto.getUsername());
//        user.setPassword(userCreateDto.getPassword());
//        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
//        user.setDateOfBirth(userCreateDto.getDateOfBirth());
//        user.generateMembership();
//        user.setUserKey(user.getMembershipId());
//        user.setActivated(false);
        return null;
    }
}

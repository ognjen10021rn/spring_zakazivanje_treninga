package rs.ognjen_uros.spring_zakazivanje_treninga.mapper;

import org.springframework.stereotype.Component;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Manager;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.ManagerCreateDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.ManagerDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserCreateDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.RoleRepository;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setNumberOfSessions(user.getNumberOfSessions());
        userDto.setDateOfBirth(user.getDateOfBirth());
        return userDto;
    }
    public ManagerDto manageerToManagerDto(Manager manager) {
        ManagerDto managerCreateDto = new ManagerDto();
        managerCreateDto.setEmailManager(manager.getEmailManager());
        managerCreateDto.setFirstName(manager.getFirstName());
        managerCreateDto.setLastName(manager.getLastName());
        managerCreateDto.setSalaName(manager.getSalaName());
        managerCreateDto.setUsernameManager(manager.getUsernameManager());

        return managerCreateDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
        user.setNumberOfSessions(0);
        user.setDateOfBirth(userCreateDto.getDateOfBirth());
        user.generateMembership();
        user.setUserKey(user.getMembershipId());
        user.setActivated(false);
        user.setDeleted(false);
        return user;
    }
}

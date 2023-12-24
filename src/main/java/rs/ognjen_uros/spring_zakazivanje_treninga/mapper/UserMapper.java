package rs.ognjen_uros.spring_zakazivanje_treninga.mapper;

import org.springframework.stereotype.Component;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
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
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
        //Set address
//        Address address = new Address();
//        address.setCountry(userCreateDto.getAddress().getCountry());
//        address.setCity(userCreateDto.getAddress().getCity());
//        address.setPostcode(userCreateDto.getAddress().getPostcode());
//        address.setStreet(userCreateDto.getAddress().getStreet());
//        address.setNumber(userCreateDto.getAddress().getNumber());
//        address.setApartmentNumber(userCreateDto.getAddress().getApartmentNumber());
//        user.setAddress(address);
        return user;
    }
}

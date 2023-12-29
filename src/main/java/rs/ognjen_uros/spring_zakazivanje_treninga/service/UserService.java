package rs.ognjen_uros.spring_zakazivanje_treninga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.*;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto add(UserCreateDto userCreateDto);
    UserDto activate(String userKey);
    UserDto update(UserChangeDto userDto, Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}

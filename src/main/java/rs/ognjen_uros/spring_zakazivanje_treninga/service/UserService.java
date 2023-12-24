package rs.ognjen_uros.spring_zakazivanje_treninga.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.TokenRequestDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.TokenResponseDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserCreateDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserDto;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    UserDto add(UserCreateDto userCreateDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}

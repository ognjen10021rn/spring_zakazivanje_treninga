package rs.ognjen_uros.spring_zakazivanje_treninga.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.TokenRequestDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.TokenResponseDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserCreateDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.UserDto;
import rs.ognjen_uros.spring_zakazivanje_treninga.exception.NotFoundException;
import rs.ognjen_uros.spring_zakazivanje_treninga.mapper.UserMapper;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.UserRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.secutiry.service.TokenService;
import rs.ognjen_uros.spring_zakazivanje_treninga.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private TokenService tokenService;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto add(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}

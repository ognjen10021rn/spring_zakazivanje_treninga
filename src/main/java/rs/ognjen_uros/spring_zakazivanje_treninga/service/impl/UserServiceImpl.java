package rs.ognjen_uros.spring_zakazivanje_treninga.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.*;
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
    public UserDto activate(String userKey) {
        User user = userRepository.findUserByUserKey(userKey)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with key: %s not found.", userKey)));
        user.setActivated(true);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto update(UserChangeDto userDto, Long id) {
        User user = userRepository
                .findUserById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with id: %s not found.", id)));
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return userMapper.userToUserDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        claims.put("email", user.getEmail());
        claims.put("isActive", user.getActivated());
        claims.put("isDeleted", user.getDeleted());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}

package rs.ognjen_uros.spring_zakazivanje_treninga.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Manager;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.User;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.VerificationToken;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.*;
import rs.ognjen_uros.spring_zakazivanje_treninga.exception.NotFoundException;
import rs.ognjen_uros.spring_zakazivanje_treninga.helper.MessageHelper;
import rs.ognjen_uros.spring_zakazivanje_treninga.mapper.UserMapper;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.ManagerRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.repository.UserRepository;
import rs.ognjen_uros.spring_zakazivanje_treninga.secutiry.service.TokenService;
import rs.ognjen_uros.spring_zakazivanje_treninga.service.UserService;

import java.sql.Timestamp;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private TokenService tokenService;
    private UserRepository userRepository;
    private ManagerRepository managerRepository;
    private UserMapper userMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String sendVerificationForUser;

    public UserServiceImpl(MessageHelper messageHelper, @Value("${destination.sendVerificationForUser}") String sendVerificationForUser,JmsTemplate jmsTemplate, ManagerRepository managerRepository, UserRepository userRepository, TokenService tokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.managerRepository = managerRepository;
        this.jmsTemplate = jmsTemplate;
        this.sendVerificationForUser = sendVerificationForUser;
        this.messageHelper = messageHelper;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with key: %s not found.", id)));
        return userMapper.userToUserDto(user);
    }

    @Override
    public ManagerDto getManagerById(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Manager with key: %s not found.", id)));
        return userMapper.manageerToManagerDto(manager);
    }

    @Override
    public UserDto add(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        tokenService.save(user, user.getUserKey());
        userRepository.save(user);
        System.out.println(user.toString());
        jmsTemplate.convertAndSend(sendVerificationForUser, messageHelper.createTextMessage(new SendVerificationLinkToUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), "http://localhost:8080/api/user/activate/"+user.getUserKey())));
        return userMapper.userToUserDto(user);
    }

    @Override
    public void updateUserNumberOfSessions(Long userid) {
        User user = userRepository.findUserById(userid)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with key: %s not found.", userid)));
        user.setNumberOfSessions(user.getNumberOfSessions() + 1);
        userRepository.save(user);
    }

    @Override
    public UserDto activate(String userKey) {
        User user = userRepository.findUserByUserKey(userKey)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with key: %s not found.", userKey)));
        VerificationToken verificationToken = tokenService.findByToken(userKey);
        System.out.println(verificationToken.getUser().getFirstName());
        if(verificationToken == null){
            return null;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(timestamp.before(verificationToken.getExpireDate())){
            user.setActivated(true);
            userRepository.save(user);
        }
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
    public ManagerDto saveManager(ManagerCreateDto managerCreateDto) {
        Manager manager = new Manager();
        manager.setEmail(managerCreateDto.getEmailManager());
        manager.setPassword(managerCreateDto.getPassword());
        manager.setUsernameManager(managerCreateDto.getUsernameManager());
        manager.setSalaName(managerCreateDto.getSalaName());
        manager.setFirstName(managerCreateDto.getFirstName());
        manager.setLastName(managerCreateDto.getLastName());
        managerRepository.save(manager);
        return userMapper.manageerToManagerDto(manager);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository.findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword());
        if(user != null) {
            Claims claims = Jwts.claims();
            claims.put("id", user.getId());
            claims.put("role", user.getRole().getName());
            claims.put("email", user.getEmail());
            claims.put("isActive", user.getActivated());
            claims.put("isDeleted", user.getDeleted());
            return new TokenResponseDto(tokenService.generate(claims));
        }
        Manager manager = managerRepository.findByEmailManagerAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword());
        if(manager != null) {
            Claims claims = Jwts.claims();
            claims.put("id", manager.getId());
            claims.put("email", manager.getEmail());
            claims.put("salaName", manager.getSalaName());
            return new TokenResponseDto(tokenService.generate(claims));
        }
        return new TokenResponseDto("none");
    }
}

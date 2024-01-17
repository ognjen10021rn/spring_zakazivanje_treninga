package rs.ognjen_uros.spring_zakazivanje_treninga.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ognjen_uros.spring_zakazivanje_treninga.domain.Manager;
import rs.ognjen_uros.spring_zakazivanje_treninga.dto.*;
import rs.ognjen_uros.spring_zakazivanje_treninga.secutiry.CheckSecurity;
import rs.ognjen_uros.spring_zakazivanje_treninga.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN\", \"ROLE_USER"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization,
                                                     Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Valid Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
    @GetMapping("/findByToken")
    public ResponseEntity<String> findByToken(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(userService.findByToken(authorization), HttpStatus.OK);
    }
    @GetMapping("/getManager/{managerId}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable @Valid Long managerId) {
        return new ResponseEntity<>(userService.getManagerById(managerId), HttpStatus.OK);
    }
    @GetMapping("/getManagerByEmail/{email}")
    public ResponseEntity<ManagerDto> getManagerByEmail(@PathVariable @Valid String email) {
        return new ResponseEntity<>(userService.getManagerByEmail(email), HttpStatus.OK);
    }
    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable @Valid String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @ApiOperation(value = "Register user")
    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.add(userCreateDto), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Register manager")
    @PostMapping("/registerManager")
    public ResponseEntity<ManagerDto> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(userService.saveManager(managerCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/loginUser")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @GetMapping("/activate/{userKey}")
    public ResponseEntity<UserDto> activateUser(@PathVariable String userKey) {
        return new ResponseEntity<>(userService.activate(userKey), HttpStatus.OK);
    }
    @ApiOperation(value = "Login")
    @PostMapping("/updateProfile/{id}")
    public ResponseEntity<UserDto> changeProfile(@RequestBody @Valid UserChangeDto userDto, @PathVariable Long id) {
        return new ResponseEntity<>(userService.update(userDto, id), HttpStatus.OK);
    }
}

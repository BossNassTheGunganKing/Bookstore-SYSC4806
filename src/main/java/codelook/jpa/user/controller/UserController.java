package codelook.jpa.user.controller;

import codelook.jpa.user.model.UserRole;
import codelook.jpa.user.request.UserRegistrationRequest;
import codelook.jpa.user.service.UserService;
import codelook.jpa.utils.ErrorMapper;
import codelook.jpa.user.validation.CurrentUserHasRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ErrorMapper errorMapper;

    @Autowired
    public UserController(UserService userService, ErrorMapper errorMapper) {
        this.userService = userService;
        this.errorMapper = errorMapper;
    }

    @PostMapping
    @CurrentUserHasRole(userRoles = {UserRole.ADMIN})
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationRequest userRequest) {
        try{
            return new ResponseEntity<>(this.userService.registerUser(userRequest), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(this.errorMapper.createErrorMap(e), HttpStatus.BAD_REQUEST);
        }
    }


}



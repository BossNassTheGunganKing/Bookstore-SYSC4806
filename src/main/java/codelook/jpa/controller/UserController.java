package codelook.jpa.controller;

import codelook.jpa.model.UserRole;
import codelook.jpa.request.UserRegistrationRequest;
import codelook.jpa.service.UserService;
import codelook.jpa.utils.ErrorMapper;
import codelook.jpa.validation.CurrentUserHasRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.json.EnumTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.valueOf;

@Validated
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



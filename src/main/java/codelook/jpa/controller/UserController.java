package codelook.jpa.controller;

import codelook.jpa.model.UserRole;
import codelook.jpa.request.ErrorResponse;
import codelook.jpa.request.UserRegistrationRequest;
import codelook.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.json.EnumTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.valueOf;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EnumTranslator enumTranslator;

    @Autowired
    public UserController(UserService userService, EnumTranslator enumTranslator) {
        this.userService = userService;
        this.enumTranslator = enumTranslator;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserRole loggedInRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> enumTranslator.fromText(UserRole.class, grantedAuthority.getAuthority())).orElse(UserRole.DEFAULT);
        ErrorResponse errorResponse = userService.validateUserRegistration(userRequest, loggedInRole);
        if (errorResponse != null) {
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }else{
            UserRole userRole = enumTranslator.fromText(UserRole.class,userRequest.role());
            userService.registerUser(userRequest.username(),userRequest.password(),userRequest.email(), userRole);
            return new ResponseEntity<>("Successfully registered new user",HttpStatus.CREATED);
        }
    }


}



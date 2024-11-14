package codelook.jpa.controller;

import codelook.jpa.model.UserInfo;
import codelook.jpa.model.UserRole;
import codelook.jpa.repository.UserInfoRepo;
import codelook.jpa.request.ErrorResponse;
import codelook.jpa.request.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationRequest userRequest) {
        ErrorResponse errorResponse = validateUserRegistration(userRequest);
        if (errorResponse != null) {
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }else{
            UserInfo newUser = new UserInfo(userRequest.username(),userRequest.password(),userRequest.email(), UserRole.DEFAULT);
            userInfoRepo.save(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }


    public ErrorResponse validateUserRegistration(UserRegistrationRequest registrationRequest) {
        Map<String, String> errors = new HashMap<>();
        if(registrationRequest.username() == null || registrationRequest.username().isBlank()){
            errors.put("username", "Username is required");
        }
        if(userInfoRepo.existsUserInfoByUsername(registrationRequest.username())){
            errors.put("username", "Username is already in use");
        }

        if(registrationRequest.password() == null || registrationRequest.password().isBlank()){
            errors.put("password", "Password is required");
        }
        if(registrationRequest.password() != null && registrationRequest.password().length() < 8){
            errors.put("password", "Password must be at least 8 characters");
        }

        if(registrationRequest.email() == null || registrationRequest.email().isBlank()){
            errors.put("email", "Email is required");
        }
        if(registrationRequest.email() != null && !registrationRequest.email().contains("@")){ // more trouble to fully validate emails than it's worth
            errors.put("email", "Email must be a valid email address");
        }
        if(errors.isEmpty()){
            return null;
        }
        return new ErrorResponse("Invalid user registration request",errors);

    }
}



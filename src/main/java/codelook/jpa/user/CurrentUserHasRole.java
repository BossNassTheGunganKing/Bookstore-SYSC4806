package codelook.jpa.user;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.annotation.*;
import java.util.Arrays;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentUserHasRole.Validator.class)
@Documented
@interface CurrentUserHasRole {
    String message() default "User does not have the required role";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    UserRole[] userRoles();

    class Validator implements ConstraintValidator<CurrentUserHasRole, Object> {

        private UserRole[] allowedRoles;

        @Override
        public void initialize(CurrentUserHasRole constraintAnnotation) {
            allowedRoles = constraintAnnotation.userRoles();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false; // No authenticated user
            }

            // Check if the user has the required role
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(authority -> Arrays.stream(allowedRoles)
                            .map(UserRole::name)
                            .anyMatch(authority::equals));
        }
    }
}


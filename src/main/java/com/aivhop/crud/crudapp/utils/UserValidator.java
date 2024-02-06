package com.aivhop.crud.crudapp.utils;

import com.aivhop.crud.crudapp.models.User;
import com.aivhop.crud.crudapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component //todo check without
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> userWithEqualEmail = userService.findByEmail(user.getEmail());
        if (userWithEqualEmail.isPresent() && user.getId() != userWithEqualEmail.get().getId()) { // for correct validation in updating
            errors.rejectValue("email", "", "This email is already taken");
        }
        Optional<User> userWithEqualUserName = userService.findByUserName(user.getUserName());
        if (userWithEqualUserName.isPresent() && user.getId() != userWithEqualUserName.get().getId()) { // for correct validation in updating
            errors.rejectValue("userName", "", "This username is already taken");
        }
    }


}

package com.tvoyagryvnia.util.validation.login;

import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LoginConstraintValidator implements ConstraintValidator<Login, String> {

    @Autowired
    IUserService userService;

    @Override
    public void initialize(Login String) { }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext cxt) {

        return userService.isUserLoginFree( login );
    }
}

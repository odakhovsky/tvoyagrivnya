package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.response.ResultBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/registration")
@Controller
public class RegistrationController {

    @Autowired
    IUserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultBean> registration(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email
    ) {
        try {
            if (userService.isUserLoginFree(email)) {
                userService.saveUser(new UserBean(name, email));
                return new ResponseEntity<ResultBean>(new ResultBean(true, "Реєстрація пройшла успішно!"), HttpStatus.OK);
            }else {
                return new ResponseEntity<ResultBean>(new ResultBean(false, "На цю пошту вже зарєстрований акаунт!"), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<ResultBean>(new ResultBean(false, ex.getMessage()), HttpStatus.OK);
        }
    }

}

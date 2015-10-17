package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired IUserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/isLoginFree", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Boolean> isLoginUnique(@RequestParam("login") String login){

        if (userService.isUserLoginFree( login )){
            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
        }else {
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
        }
    }

}

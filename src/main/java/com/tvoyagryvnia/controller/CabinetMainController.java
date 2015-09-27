package com.tvoyagryvnia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/cabinet")
@Controller
public class CabinetMainController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String redirectToMain() {
        return "redirect:/cabinet/main/";
    }

    @RequestMapping(value = "/main/", method = RequestMethod.GET)
    public String main(ModelMap map) {
        return "cabinet/main";
    }

}

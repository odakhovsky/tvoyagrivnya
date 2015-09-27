package com.tvoyagryvnia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/cabinet/control")
public class CabinetControlController {

    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {

        return "cabinet/control/main";
    }
}

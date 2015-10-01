package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/currency")
public class CurrencyAdminController {

    @Autowired
    private ICurrencyService currencyService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String currency(ModelMap map) {
        map.addAttribute("currencies", currencyService.getAll());
        return "admin/currencies";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(@RequestParam(value = "fullname") String full,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "shortName") String shortName,
                         @RequestParam(value = "rate") Float rate) {
        currencyService.create(full, name, shortName, rate);
        return "redirect:/admin/currency/";
    }

}

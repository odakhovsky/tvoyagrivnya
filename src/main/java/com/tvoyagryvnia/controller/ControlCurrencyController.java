package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.UserFieldBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cabinet/control/currencies")
@SessionAttributes("userBean")
public class ControlCurrencyController {

    @Autowired private IUserCurrencyService userCurrencyService;

    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public String index(ModelMap map, @ModelAttribute("userBean")UserBean userBean) {
        map.addAttribute("currs", userCurrencyService.getAllOfUser(userBean.getId()));
        return "cabinet/control/currencies";
    }

    @RequestMapping(value = "/default", method = RequestMethod.POST)
    public String setDeff(@RequestParam("curr")Integer curr,@ModelAttribute("userBean")UserBean user) {
        userCurrencyService.setAsDefault(user.getId(), curr);
        return "redirect:/cabinet/control/currencies/";
    }

    @RequestMapping(value = "/editrate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> editField(UserFieldBean field) {
        try {
            float rate = Float.valueOf(field.getValue());
            userCurrencyService.updateCrossRate(field.getPk(), rate);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>("Не вірні данні.", HttpStatus.OK);
        }
    }
}

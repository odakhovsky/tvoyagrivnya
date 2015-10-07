package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IAccountDao;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.service.IAccountService;
import com.tvoyagryvnia.service.ICurrencyService;
import com.tvoyagryvnia.service.IExchangeService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/cabinet/accounts/exchange")
@SessionAttributes("userBean")
public class AccountsExchangeController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserCurrencyService currencyService;
    @Autowired
    private IExchangeService exchangeService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String exchangepage(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        map.addAttribute("accounts", accountService.getAllOfUserEnabled(user.getId(), true, true));
        map.addAttribute("currencies", currencyService.getAllOfUser(user.getId()));
        return "cabinet/accounts/exchange/main";
    }

    @RequestMapping(value = "/new/", method = RequestMethod.POST)
    public String doExchange(@RequestParam("date") String date,
                             @RequestParam("description") String description,
                             @RequestParam("accFrom") Integer accFrom,
                             @RequestParam("currencyFrom") Integer currFrom,
                             @RequestParam("moneyFrom") Float moneyFrom,
                             @RequestParam("accTo") Integer accTo,
                             @RequestParam("currencyTo") Integer currTo,
                             @RequestParam("moneyTo") Float moneTo
                            ,@ModelAttribute("userBean")UserBean user) {
        exchangeService.create(date, description, moneyFrom, accFrom, currFrom, user.getId(), accTo, currTo, moneTo);
        return "redirect:/cabinet/accounts/exchange/";
    }

    @RequestMapping(value = "/currencies/", method = RequestMethod.GET)
    @ResponseBody
    public List<ExtendedCurrencyBean> currencyBeans(@ModelAttribute("userBean") UserBean user) {
        return currencyService.getAllOfUser(user.getId());
    }

}

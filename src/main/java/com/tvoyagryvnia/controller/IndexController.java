package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IAccountService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.util.BalanceUtil;
import com.tvoyagryvnia.util.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/")
@SessionAttributes("userBean")
public class IndexController {

    @Autowired
    IUserService userService;
    @Autowired
    IAccountService accountService;

    @Autowired
    IUserCurrencyService userCurrencyService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        System.out.println(Cipher.encrypt("11223354aa"));
//        List<AccountBean> accs = accountService.getAllOfUserActive(32, true);
//        ExtendedCurrencyBean cb = userCurrencyService.getByShortNameForUser(32, "USD");
//        System.out.println(BalanceUtil.getPurposeToGetMoney(accs, cb, 259));
        return "index";
    }

    @RequestMapping(value = "getPurpose", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<String> getPurpose(@RequestParam("sum") String value,
                                      @RequestParam("currency") Integer currency,
                                      @ModelAttribute("userBean")UserBean user) {

        ExtendedCurrencyBean cb = userCurrencyService.getById(currency);
        List<AccountBean> accs = accountService.getAllOfUserActive(user.getId(), true);
        String result = BalanceUtil.getPurposeToGetMoney(accs, cb, Long.valueOf(value));
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/isLoginFree", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Boolean> isLoginUnique(@RequestParam("login") String login) {

        if (userService.isUserLoginFree(login)) {
            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
        }
    }

}

package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.operation.NewOperation;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IAccountService;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cabinet/operations")
@SessionAttributes("userBean")
public class CabinetOperationController {

    @Autowired
    private IUserCurrencyService userCurrencyService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserCategoryService userCategoryService;

    @Autowired private IOperationService operationService;

    @RequestMapping(value = "/new/", method = RequestMethod.GET)
    public String newOperation(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        map.addAttribute("currencies", userCurrencyService.getAllOfUser(user.getId()));
        map.addAttribute("accounts", accountService.getAllOfUserEnabled(user.getId(), true, true));
        return "cabinet/operations/new";
    }

    @RequestMapping(value = "/new/", method = RequestMethod.POST)
    public String newOperation(@RequestParam("operationtype")String type,
                               @RequestParam("date")String date,
                               @RequestParam("description")String description,
                               @RequestParam("money")Float money,
                               @RequestParam("accounts")Integer account,
                               @RequestParam("currency")Integer currency,
                               @RequestParam("category")Integer category,
                               @ModelAttribute("userBean") UserBean user) {
        operationService.create( date, description, money, account, currency, category, user.getId());
        return "redirect:/cabinet/operations/new/";
    }

    @RequestMapping(value = "/categories/", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryBean> getUserCats(@ModelAttribute("userBean") UserBean user) {
        return userCategoryService.getAll(user.getId(), true);
    }

    @RequestMapping(value = "/accounts/", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountBean> getUserAccs(@ModelAttribute("userBean") UserBean user) {
        return accountService.getAllOfUserEnabled(user.getId(), true, true);
    }

    @RequestMapping(value = "/currencies/", method = RequestMethod.GET)
    @ResponseBody
    public List<ExtendedCurrencyBean> getUserCurrs(@ModelAttribute("userBean") UserBean user) {
        return userCurrencyService.getAllOfUser(user.getId());
    }

}

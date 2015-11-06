package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.bean.statistic.simple.SimpleStatisticBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/statistic")
@SessionAttributes("userBean")
public class StatisticController {

    @Autowired private IOperationService operationService;
    @Autowired private IUserCurrencyService userCurrencyService;


    @RequestMapping(value = "/simple/", method = RequestMethod.GET)
    @ResponseBody
    public SimpleStatisticBean simpleStatistic(@ModelAttribute("userBean") UserBean user) {
        List<OperationBean> operations = operationService.getAllAciveOfUserByCurrentMonth(user.getId());
        String currency = userCurrencyService.getDefaultCurrencyOfUser(user.getId()).getShortName();
        SimpleStatisticBean statistic = new SimpleStatisticBean(operations, currency);
        return statistic;
    }


}

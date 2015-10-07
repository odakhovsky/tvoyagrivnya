package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/cabinet")
@Controller
@SessionAttributes("userBean")
public class CabinetMainController {

    @Autowired
    private IOperationService operationService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String redirectToMain() {
        return "redirect:/cabinet/main/";
    }

    @RequestMapping(value = "/main/", method = RequestMethod.GET)
    public String main(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        List<OperationBean> operations = operationService.getAllOfUser(user.getId(), true)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .sorted((o1, o2) -> Integer.compare(o2.getId(), o1.getId())).collect(Collectors.toList());
        map.addAttribute("operations", operations);
        return "cabinet/main";
    }

}

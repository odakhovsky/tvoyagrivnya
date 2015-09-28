package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.user.EditUserPass;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cabinet/settings")
@SessionAttributes("userBean")
public class UserSettingsController {

    @Autowired
    private IUserService userService;


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String userSettings(@ModelAttribute("userBean") UserBean userBean, ModelMap map) {
        UserBean user = userService.getUserById(userBean.getId());
        if (null != user) {
            map.addAttribute("profile", user);
            map.addAttribute("editPass", new EditUserPass(user.getId()));
        }
        return "cabinet/settings";
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public String newPass(@ModelAttribute("editUserPass") EditUserPass userPass, HttpSession session) {

        UserBean userBean = (UserBean) session.getAttribute("userBean");

        userPass.setUserId(userBean.getId());

        userPass.setUserId(userBean.getId());
        userPass.setEmail(userBean.getEmail());
        userService.updateUser(userPass);
        return "redirect:/cabinet/settings/";
    }

    @RequestMapping(value = "/user-edit", method = RequestMethod.POST)
    public String newPass(@ModelAttribute("profile") UserBean userBean) {
        userService.updateUser(userBean);
        return "redirect:/cabinet/settings/";
    }
}

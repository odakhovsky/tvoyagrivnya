package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.UserFieldBean;
import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.category.CategoryNode;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.service.impl.UserServiceImpl;
import com.tvoyagryvnia.util.Messages;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet/control")
@SessionAttributes("userBean")
public class CabinetControlController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserCategoryService userCategoryService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        return "redirect:/cabinet/control/categories/";
    }

    @RequestMapping(value = {"/members/"}, method = RequestMethod.GET)
    public String members(ModelMap modelMap, @ModelAttribute("userBean") UserBean userBean) {
        fillMap(modelMap, userBean);
        return "cabinet/control/members";
    }

    private void fillMap(ModelMap modelMap, @ModelAttribute("userBean") UserBean userBean) {
        modelMap.addAttribute("newUser", new UserBean());
        modelMap.addAttribute("userMembers", userService.getUserMembers(userBean.getId()));
        modelMap.addAttribute("TOOL_TIP_ROLE", Messages.CONTROL_MEMBERS_ADDITION_ROLE);
    }

    @RequestMapping(value = {"/invite-new-user"}, method = RequestMethod.POST)
    public String inviteUser(@ModelAttribute("newUser") UserBean newUser, @ModelAttribute("userBean") UserBean userBean, ModelMap map) {
        if (userService.isUserLoginFree(newUser.getEmail())) {
            userService.inviteNewUser(userBean, newUser);
        } else {
            map.addAttribute("error", Messages.EMAIL_IS_USER_ALREADY);
            map.addAttribute("newUser", newUser);
            return "cabinet/control/members";
        }
        return "redirect:/cabinet/control/";
    }

    @RequestMapping(value = "/user/editField", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> editField(UserFieldBean field) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserBean>> violations =
                validator.validateValue(UserBean.class, field.getName(), field.getValue());

        if (violations.isEmpty()) {
            try {
                userService.updateSingleField(field.getPk(), field.getName(), field.getValue());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            StringBuilder stringBuffer = new StringBuilder();

            for (ConstraintViolation<UserBean> violation : violations) {
                stringBuffer.append(violation.getMessage());
                stringBuffer.append("\n");
            }

            return new ResponseEntity<>(stringBuffer.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/{userId}/superMember", method = RequestMethod.POST)
    public String editRole(@PathVariable("userId") Integer userId, @RequestParam("value") String value) {
        UserBean userBean = userService.getUserById(userId);
        if (null != userBean) {
            RoleEntity roleEntity = roleDao.getRoleByName(RoleEntity.Name.ROLE_SUPER_MEMBER);
            if (null != value) {
                switch (value) {
                    case "true":
                        if (!userBean.isSuperMember()) {
                            userService.addRole(userId, roleEntity.getName());
                        }
                        break;
                    case "false":
                        if (userBean.isSuperMember()) {
                            userService.removeRole(userId, roleEntity.getName());
                        }
                        break;
                }
            }
        }
        return "redirect:/cabinet/control/";
    }
}

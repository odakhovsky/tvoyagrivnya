package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.user.UserFieldBean;
import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.account.SideBarAccountBean;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.bean.response.ResultBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.IAccountService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cabinet/accounts")
@SessionAttributes("userBean")
public class AccountsController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = {"", "/"})
    public String index() {
        return "redirect:/cabinet/accounts/accmanage/";
    }

    @RequestMapping(value = "/accmanage/", method = RequestMethod.GET)
    public String accManage(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        map.addAttribute("accounts", accountService.getAllOfUserActive(user.getId(), true));
        return "cabinet/accounts/manage";
    }

    @RequestMapping(value = "/accmanage/{accId}/info/", method = RequestMethod.GET)
    public String accInfo(ModelMap map,
                          @PathVariable("accId") Integer accId,
                          @ModelAttribute("userBean") UserBean user) {
        AccountBean accountBean = accountService.getById(accId);
        boolean canDisplay = (accountBean.getOwner() == user.getId() && accountBean.isActive());
        if (canDisplay) {
            map.addAttribute("account", accountBean);
            return "cabinet/accounts/account";
        } else {
            return "redirect:/cabinet/accounts/accmanage/";
        }
    }

    @RequestMapping(value = "/createAccount/", method = RequestMethod.GET)
    public String createAccPage() {
        return "cabinet/accounts/create";
    }

    @RequestMapping(value = "/createAccount/", method = RequestMethod.POST)
    public String createAcc(@RequestParam("name") String name,
                            @RequestParam(value = "description") String description,
                            ModelMap map, @ModelAttribute("userBean") UserBean userBean) {
        boolean isFree = accountService.isAccountNameIsFree(userBean.getId(), name);
        if (isFree) {
            accountService.create(userBean.getId(), name, description);
        } else {
            map.addAttribute("error", "Задайте іншу назву рахунку.");
            return "cabinet/accounts/create";
        }
        return "redirect:/cabinet/accounts/accmanage/";
    }

    @RequestMapping(value = "/editField", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> editField(UserFieldBean field) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CurrencyBean>> violations =
                validator.validateValue(CurrencyBean.class, field.getName(), field.getValue());

        if (violations.isEmpty()) {
            try {
                accountService.updateSingleField(field.getPk(), field.getName(), field.getValue());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (ConstraintViolationException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            StringBuilder stringBuffer = new StringBuilder();

            for (ConstraintViolation<CurrencyBean> violation : violations) {
                stringBuffer.append(violation.getMessage());
                stringBuffer.append("\n");
            }

            return new ResponseEntity<>(stringBuffer.toString(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "/sidebar/list", method = RequestMethod.GET)
    @ResponseBody
    public List<SideBarAccountBean> userAccounts(@ModelAttribute("userBean")UserBean user) {
        return accountService.getAllOfUserEnabled(user.getId(), true, true)
                .stream().map(SideBarAccountBean::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{accountId}/deactivate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultBean> deactivate(@PathVariable("accountId")Integer accId) {
        AccountBean accountBean = accountService.getById(accId);
        if (Objects.nonNull(accountBean)) {
            accountBean.setEnabled(false);
            accountService.update(accountBean);
            return new ResponseEntity<>(new ResultBean(true),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResultBean(false),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{accountId}/activate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultBean> activate(@PathVariable("accountId")Integer accId) {
        AccountBean accountBean = accountService.getById(accId);
        if (Objects.nonNull(accountBean)) {
            accountBean.setEnabled(true);
            accountService.update(accountBean);
            return new ResponseEntity<>(new ResultBean(true),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResultBean(false),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{accountId}/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultBean> remove(@PathVariable("accountId")Integer accId) {
        AccountBean accountBean = accountService.getById(accId);
        if (Objects.nonNull(accountBean)) {
            accountBean.setEnabled(false);
            accountBean.setActive(false);
            accountService.update(accountBean);
            //unactive all operations for this account
            return new ResponseEntity<>(new ResultBean(true),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResultBean(false),HttpStatus.OK);
        }
    }
}

package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.UserFieldBean;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

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


    @RequestMapping(value = "/editField", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> editField(UserFieldBean field) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CurrencyBean>> violations =
                validator.validateValue(CurrencyBean.class, field.getName(), field.getValue());

        if (violations.isEmpty()) {
            try {
                currencyService.updateSingleField(field.getPk(), field.getName(), field.getValue());
                return new ResponseEntity<>(HttpStatus.OK);
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

}

package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.AnalysisBean;
import com.tvoyagryvnia.bean.Line;
import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.bean.reports.ReportBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.service.impl.AnalysisService;
import com.tvoyagryvnia.util.DateUtil;
import com.tvoyagryvnia.util.NumberFormatter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet/analysis")
@SessionAttributes("userBean")
public class AnalysisController {

    @Autowired
    private IUserCategoryService userCategoryService;

    @Autowired private AnalysisService analysisService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        return "cabinet/analysis";
    }

    @RequestMapping(value = "/categories/", method = RequestMethod.GET)
    @ResponseBody
    List<CategoryBean> userCates(@ModelAttribute("userBean") UserBean user) {
        return userCategoryService.getAll(user.getId(), true).stream().
                filter(c -> c.getParent() == 0 && c.getOperation().equals(OperationType.minus.name()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String analysis(@RequestBody AnalysisBean req, ModelMap map, @ModelAttribute("userBean") UserBean user) {
        AnalysisBean bean = analysisService.analysis(req.getRange(), user.getId());
        map.addAttribute("res", bean);
        return "cabinet/analysis/result";
    }
}

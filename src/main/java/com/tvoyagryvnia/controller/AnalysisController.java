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
    private IUserDao userDao;
    @Autowired
    private IUserCategoryService userCategoryService;
    @Autowired
    private IOperationService operationService;
    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private IOperationDao operationDao;
    @Autowired
    private IUserCurrencyService userCurrencyService;

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

    private List<OperationEntity> getOperations(UserCategoryEntity category, Date from, Date to) {
        return operationDao.getAllOfUserByCategory(category.getOwner().getId(), category.getId(), true, from, to);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String analysis(@RequestBody AnalysisBean bean, ModelMap map, @ModelAttribute("userBean") UserBean user) {
        Date from = DateUtil.parseDate(bean.getRange().split(" - ")[0], DateUtil.DF_POINT.toPattern());
        Date to = DateUtil.parseDate(bean.getRange().split(" - ")[1], DateUtil.DF_POINT.toPattern());
        AnalysisBean result = new AnalysisBean();
        result.setRange(bean.getRange());
        List<Line> lines = new ArrayList<>();
        try {
            List<OperationBean> operations = new ArrayList<>();
            for (CategoryBean cat : userCategoryService.getAll(user.getId(), true).stream().filter(c -> c.getOperation().equals(OperationType.minus.name())).collect(Collectors.toList())) {
                List<OperationBean> oper = operationService.getAllOfUserByCategory(user.getId(), cat.getId(), true, from, to);
                operations.addAll(oper);
            }

            float total = calcSum(operations);
            for (Line line : bean.getCategories()) {
                float sum = userCategoryService.getSumOfCategoryAndSubCategoriesIfPresent(line.getId(), from, to);
                float percent = NumberFormatter.cutFloat(((sum / total) * 100), 2);
                line.setPercent(percent);
                line.setMoney(sum);
                line.setCurr(userCurrencyService.getDefaultCurrencyOfUser(user.getId()).getShortName());
                if (sum > 0.0) {
                    lines.add(line);
                }
            }
            result.setCategories(lines);
            Collections.sort(bean.getCategories(), this::compare);
            map.put("res", result);
        }catch (Exception ex){

        }
        return "cabinet/analysis/result";
    }
    public int compare(Line bean, Line bean2){

        return new CompareToBuilder()
                .append(bean2.getPercent(), bean.getPercent())
                .append(bean2.getValue(), bean.getValue())
                .toComparison();
    }

    private float calcSum(List<OperationBean> operations) {
        float sum = 0.0f;
        for (OperationBean op : operations) {
            sum += op.getMoney() * op.getCurrency().getCrossRate();
        }
        return sum;
    }

}

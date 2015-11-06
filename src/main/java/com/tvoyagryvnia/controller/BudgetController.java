package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.bean.budget.SimpleBudgetBean;
import com.tvoyagryvnia.bean.reports.budget.xls.XlsBudgetReport;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IBudgetService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet/budget")
@SessionAttributes("userBean")
public class BudgetController {

    @Autowired private IBudgetService budgetService;
    @Autowired private IUserCategoryService userCategoryService;
    @Autowired private IUserCurrencyService userCurrencyService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(ModelMap map, @RequestParam(value = "budgetId", required = false) Integer budgetId,
                        @ModelAttribute("userBean") UserBean user) {
        List<SimpleBudgetBean> budgets = budgetService.getAllOfUser(user.getId(), true);
        map.addAttribute("budgets", budgets);
        if (null != budgetId) {
            try {
                FullBudgetBean budget = budgetService.getFullBudget(budgetId);
                if (budget.getOwner() == user.getId() && budget.isActive()) {
                    map.addAttribute("budget", budget);
                } else {
                    return "redirect:/cabinet/budget/";
                }
            } catch (NullPointerException ex) {
                return "redirect:/cabinet/budget/";
            }
        }
        return "cabinet/budget/list";
    }


    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public String create(@RequestParam("date-range") String date, ModelMap map, @ModelAttribute("userBean") UserBean user) {

        int budget = budgetService.create(date, user.getId());
        if (0 == budget) {
            map.addAttribute("error", "Бюджет з такою датою вже існує");
        } else {
            return "redirect:/cabinet/budget/" + budget + "/edit/";
        }
        return "cabinet/budget/list";
    }

    @RequestMapping(value = "/{budgetId}/edit/", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable("budgetId") Integer buId, @ModelAttribute("userBean") UserBean user) {
        SimpleBudgetBean budgetBean = budgetService.getById(buId);
        if (budgetBean.getOwner() != user.getId()) {
            return "redirect:/cabinet/budget/";
        }
        map.addAttribute("currency", userCurrencyService.getDefaultCurrencyOfUser(user.getId()).getShortName());
        map.addAttribute("budget", budgetBean);
        map.addAttribute("plus", userCategoryService.getAll(budgetBean.getOwner(), true)
                .stream().filter(c -> c.getOperation().equals(OperationType.plus.name())).collect(Collectors.toList()));
        map.addAttribute("minus", userCategoryService.getAll(budgetBean.getOwner(), true)
                .stream().filter(c -> c.getOperation().equals(OperationType.minus.name())).collect(Collectors.toList()));
        return "cabinet/budget/edit";
    }

    @RequestMapping(value = "/{budgetId}/addline/", method = RequestMethod.POST)
    public String addLine(@PathVariable("budgetId") Integer budgetId,
                          @RequestParam("categoryId") Integer category,
                          @RequestParam("money") Float money) {
        budgetService.addLineToBudget(budgetId, category, money);
        return "redirect:/cabinet/budget/" + budgetId + "/edit/";
    }

    @RequestMapping(value = "/line/{lineId}/remove/", method = RequestMethod.POST)
    public ResponseEntity removeLine(@PathVariable("lineId") Integer lineId) {
        budgetService.removeLine(lineId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/{budgetId}/remove/", method = RequestMethod.POST)
    public String addLine(@PathVariable("budgetId") Integer budgetId) {
        budgetService.deactivate(budgetId);
        return "redirect:/cabinet/budget/" + budgetId + "/edit/";
    }

    @RequestMapping(value = "/getBudgetReportAsXls/{reportId}/", method = RequestMethod.POST)
    public String getReport(@PathVariable("reportId") Integer reportId, ModelMap map) throws IOException {
        FullBudgetBean report = budgetService.getFullBudget(reportId);
        map.addAttribute("budget", report);
        XlsBudgetReport x = new XlsBudgetReport();
        map.addAttribute("report", x);
        return "xlsBudgetReport";
    }
}

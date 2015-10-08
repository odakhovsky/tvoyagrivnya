package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.budget.BudgetLineBean;
import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.bean.budget.SimpleBudgetBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IBudgetService;
import com.tvoyagryvnia.service.IUserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cabinet/budget")
@SessionAttributes("userBean")
public class BudgetController {

    @Autowired
    private IBudgetService budgetService;
    @Autowired
    private IUserCategoryService userCategoryService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(ModelMap map, @RequestParam(value = "budgetId", required = false) Integer budgetId,
                        @ModelAttribute("userBean") UserBean user) {
        addBudgetToView(map, budgetId, user.getId());
        List<SimpleBudgetBean> budgets = budgetService.getAllOfUser(user.getId(), true);
        map.addAttribute("budgets", budgets);
        if (null != budgetId) {
            FullBudgetBean budget = budgetService.getFullBudget(budgetId);
            if (budget.getOwner() == user.getId()) {
                map.addAttribute("budget", budget);
            }else {
                return "redirect:/cabinet/budget/";
            }
        }
        return "cabinet/budget/list";
    }

    private void addBudgetToView(ModelMap map, Integer budgetId, int userId) {
        if (!Objects.isNull(budgetId)) {
            FullBudgetBean budgetBean = budgetService.getFullBudget(budgetId);
            if (budgetBean.getOwner() == userId) {
                map.addAttribute("budget", budgetService.getById(budgetId));
            }
        }
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
    public String edit(ModelMap map, @PathVariable("budgetId") Integer buId,@ModelAttribute("userBean")UserBean user) {
        SimpleBudgetBean budgetBean = budgetService.getById(buId);
        if(budgetBean.getOwner() != user.getId()) {
            return "redirect:/cabinet/budget/";
        }
        map.addAttribute("budget", budgetBean);
        map.addAttribute("plus", userCategoryService.getAll(budgetBean.getOwner(), true)
                .stream().filter(c -> c.getOperation().equals(OperationType.plus.name())).collect(Collectors.toList()));
        map.addAttribute("minus", userCategoryService.getAll(budgetBean.getOwner(), true)
                .stream().filter(c -> c.getOperation().equals(OperationType.minus.name())).collect(Collectors.toList()));
        return "cabinet/budget/edit";
    }

    @RequestMapping(value = "/{budgetId}/addline/", method = RequestMethod.POST)
    public String addLine(@PathVariable("budgetId")Integer budgetId,
                          @RequestParam("categoryId")Integer category,
                          @RequestParam("money")Float money) {
        budgetService.addLineToBudget(budgetId, category, money);
        return "redirect:/cabinet/budget/" + budgetId + "/edit/";
    }
    
    @RequestMapping(value = "/line/{lineId}/remove/", method = RequestMethod.POST)
    public ResponseEntity removeLine(@PathVariable("lineId")Integer lineId) {
        budgetService.removeLine(lineId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.AdminMainStatisticBean;
import com.tvoyagryvnia.bean.FeedbackBean;
import com.tvoyagryvnia.bean.FeedbackFilter;
import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.category.CategoryNode;
import com.tvoyagryvnia.model.CategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.ICategoryService;
import com.tvoyagryvnia.service.IFeedbackService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.service.impl.AdminStatisticService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IUserService userService;
    @Autowired private IFeedbackService feedbackService;

    @Autowired
    private AdminStatisticService adminStatisticService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index(ModelMap map) {
        AdminMainStatisticBean bean = adminStatisticService.getMainStatistic();
        map.addAttribute("stat", bean);
        return "admin/index";
    }

    @RequestMapping(value = {"/categories/"}, method = RequestMethod.GET)
    public String categories(ModelMap map) {
        List<CategoryBean> plus = categoryService.getAllByType(OperationType.plus);
        List<CategoryBean> minus = categoryService.getAllByType(OperationType.minus);
        map.addAttribute("categoriesPlus", plus);
        map.addAttribute("categoriesMinus", minus);
        map.addAttribute("plus", OperationType.plus.name());
        map.addAttribute("minus", OperationType.minus.name());
        return "admin/categories";
    }

    @RequestMapping(value = {"/categories/{type}/list"}, method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryNode> categoriesList(@PathVariable("type") String type) {
        if (EnumUtils.isValidEnum(OperationType.class, type)) {
            OperationType t = OperationType.valueOf(type);
            List<CategoryBean> list = categoryService.getAllByType(t).stream()
                    .filter(c -> c.getParent() == 0).collect(Collectors.toList());

            return list.stream().map(CategoryNode::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = {"/categories/"}, method = RequestMethod.POST)
    public String categoryCreate(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "parent") Integer parent,
                                 @RequestParam(value = "operation") String operation,
                                 @RequestParam(value = "id", required = false) Integer id) {

        if (isNewCategory(id)) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setName(name);
            categoryBean.setOperation(OperationType.valueOf(operation).name());
            categoryBean.setActive(true);
            setParentIfExist(parent, categoryBean);
            categoryService.create(categoryBean);
        } else {
            CategoryBean bean = categoryService.getById(id);
            bean.setName(name);
            setParentIfExist(parent, bean);
            categoryService.update(bean);
        }
        return "redirect:/admin/categories/";
    }

    private boolean isNewCategory(@RequestParam(value = "id", required = false) Integer id) {
        return null == id || id == 0;
    }

    private void setParentIfExist(Integer parent, CategoryBean categoryBean) {
        if (hasParent(parent)) {
            CategoryBean par = categoryService.getById(parent);
            categoryBean.setParent(par.getId());
            categoryBean.setParentName(par.getName());
        } else {
            categoryBean.setParent(0);
        }
    }

    private boolean hasParent(Integer parent) {
        return null != parent && parent != 0;
    }

    @RequestMapping(value = "/update-user-categories", method = RequestMethod.POST)
    public String update() {
        userService.updateUsersCategories();
        return "redirect:/admin/categories/";

    }

    @RequestMapping(value = "/feedback/", method = RequestMethod.GET)
    public String feedback(ModelMap map) {
        List<FeedbackBean> feeds = feedbackService.getAll();
        feeds.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        map.addAttribute("feeds", feeds);
        return "admin/feedback";
    }

    @RequestMapping(value = "/feedback/", method = RequestMethod.POST)
    @ResponseBody
    public List<FeedbackBean> feedbackSearch(@RequestParam(value = "email", required = false)String email,
                                             @RequestParam("date")String date) {
        return feedbackService.getByFilter(new FeedbackFilter(email, date));
    }
}


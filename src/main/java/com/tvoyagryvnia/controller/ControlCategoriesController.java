package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.category.CategoryNode;
import com.tvoyagryvnia.bean.response.ResultBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserService;
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
@RequestMapping(value = "/cabinet/control/categories")
@SessionAttributes("userBean")
public class ControlCategoriesController {

    @Autowired
    private IUserCategoryService userCategoryService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String categories(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        List<CategoryBean> plus = userCategoryService.getAllByType(user.getId(), OperationType.plus);
        List<CategoryBean> minus = userCategoryService.getAllByType(user.getId(), OperationType.minus);
        map.addAttribute("categoriesPlus", plus);
        map.addAttribute("categoriesMinus", minus);
        map.addAttribute("plus", OperationType.plus.name());
        map.addAttribute("minus", OperationType.minus.name());
        return "cabinet/control/categories";
    }


    @RequestMapping(value = "/user/{userId}/type/{type}/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryNode> userCategories(@PathVariable("userId") Integer userId, @PathVariable("type") String type) {
        if (EnumUtils.isValidEnum(OperationType.class, type)) {
            OperationType t = OperationType.valueOf(type);
            List<CategoryBean> list = userCategoryService.getAllByType(userId, t).stream()
                    .filter(c -> c.getParent() == 0 && c.getActive()).collect(Collectors.toList());

            return list.stream().map(CategoryNode::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/{categoryId}/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultBean> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        CategoryBean bean = userCategoryService.getById(categoryId);
        if (null != bean) {
            //deactive all categories
            //deactive all operations in this category
            deactiveCategories(bean);
            return new ResponseEntity<>(new ResultBean(true, "Категорія видалена"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultBean(false, "Проблеми з видаленням"), HttpStatus.OK);
    }

    private void deactiveCategories(CategoryBean categoryBean) {
        userCategoryService.delete(categoryBean.getId());
        categoryBean.getChildrens().forEach(this::deactiveCategories);
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    public String categoryCreate(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "parent") Integer parent,
                                 @RequestParam(value = "operation") String operation,
                                 @RequestParam(value = "categoryId", required = false) Integer id,
                                 @ModelAttribute("userBean") UserBean userBean) {

        OperationType operationType = OperationType.valueOf(operation);
        if (isNewCategory(id)) {
            int present = userCategoryService.isCategoryPresent(userBean.getId(), name, parent, operationType);
            if (present == 0) {
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setName(name);
                categoryBean.setOperation(operationType.name());
                categoryBean.setActive(true);
                setParentIfExist(parent, categoryBean);
                userCategoryService.create(userBean, categoryBean);
            } else {
                CategoryBean cat = userCategoryService.getById(present);
                cat.setActive(true);
                userCategoryService.update(cat);
            }
        } else {
            CategoryBean bean = userCategoryService.getById(id);
            bean.setName(name);
            setParentIfExist(parent, bean);
            userCategoryService.update(bean);
        }
        return "redirect:/cabinet/control/categories/";

    }

    private void setParentIfExist(Integer parent, CategoryBean categoryBean) {
        if (hasParent(parent)) {
            CategoryBean par = userCategoryService.getById(parent);
            categoryBean.setParent(par.getId());
            categoryBean.setParentName(par.getName());
        } else {
            categoryBean.setParent(0);
        }
    }

    private boolean hasParent(Integer parent) {
        return null != parent && parent != 0;
    }

    private boolean isNewCategory(Integer id) {
        return null == id || id == 0;
    }

}

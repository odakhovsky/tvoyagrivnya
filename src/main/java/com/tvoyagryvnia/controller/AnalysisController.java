package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.analysis.AnalysisBean;
import com.tvoyagryvnia.bean.analysis.ExtendedAnalysisBean;
import com.tvoyagryvnia.bean.analysis.PairAnalysisRequest;
import com.tvoyagryvnia.bean.category.CategoryBean;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.impl.AnalysisService;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/extended", method = RequestMethod.GET)
    public String indexExtended(ModelMap map, @ModelAttribute("userBean") UserBean user) {
        return "cabinet/analysis-ext";
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

    @RequestMapping(value = "/extend/", method = RequestMethod.POST)
    public String analysisExtend(@RequestBody PairAnalysisRequest req, ModelMap map, @ModelAttribute("userBean") UserBean user) {
        String from = DateUtil.getDateRange(req.getFrom());
        String to = DateUtil.getDateRange(req.getTo());
        ExtendedAnalysisBean bean = analysisService.analysis(from, to, user.getId());
        map.addAttribute("res", bean);
        return "cabinet/analysis/extend/result";
    }
}

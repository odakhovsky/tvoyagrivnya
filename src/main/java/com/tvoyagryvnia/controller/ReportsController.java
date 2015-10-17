package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.reports.ReportBean;
import com.tvoyagryvnia.bean.reports.ReportFilter;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.service.impl.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cabinet/reports")
@SessionAttributes("userBean")
public class ReportsController {


    @Autowired private ReportService reportService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String reposts(ModelMap map) {
        return "cabinet/reports";
    }

    @RequestMapping(value = "/make/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReportBean> makeReport(@RequestBody ReportFilter filter) {
        return new ResponseEntity<>(reportService.getReport(filter), HttpStatus.OK);
    }

}

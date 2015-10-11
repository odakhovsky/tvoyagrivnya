package com.tvoyagryvnia.controller;

import com.tvoyagryvnia.bean.response.ResultBean;
import com.tvoyagryvnia.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired private IFeedbackService feedbackService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultBean> createFeedback(@RequestParam("name")String name,
                                                     @RequestParam("email")String email,
                                                     @RequestParam("text")String text) {

        feedbackService.create(name, email, text);
        return new ResponseEntity<>(new ResultBean(true, ""), HttpStatus.OK);
    }

}

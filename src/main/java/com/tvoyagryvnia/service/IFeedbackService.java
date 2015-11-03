package com.tvoyagryvnia.service;

import com.tvoyagryvnia.bean.FeedbackBean;
import com.tvoyagryvnia.bean.FeedbackFilter;

import java.util.List;


public interface IFeedbackService {
    FeedbackBean getById(int id);
    List<FeedbackBean> getByEmail(String email);
    void create(String name, String email, String text);
    List<FeedbackBean> getAll();
    List<FeedbackBean> getByFilter(FeedbackFilter filter);
}

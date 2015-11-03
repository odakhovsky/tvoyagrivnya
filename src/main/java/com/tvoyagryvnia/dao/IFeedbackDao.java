package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.FeedBackEntity;

import java.util.Date;
import java.util.List;

public interface IFeedbackDao {
    FeedBackEntity getById(int id);
    List<FeedBackEntity> getByEmail(String email);
    void save(FeedBackEntity feedBackEntity);
    List<FeedBackEntity> getAll();
    List<FeedBackEntity> getByFilter(Date from, Date to, String email);
}

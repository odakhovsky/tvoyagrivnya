package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.FeedbackBean;
import com.tvoyagryvnia.dao.IFeedbackDao;
import com.tvoyagryvnia.model.FeedBackEntity;
import com.tvoyagryvnia.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements IFeedbackService{

    @Autowired
    private IFeedbackDao feedbackDao;

    @Override
    public FeedbackBean getById(int id) {
        return toBean(feedbackDao.getById(id));
    }

    @Override
    public List<FeedbackBean> getByEmail(String email) {
        return feedbackDao.getByEmail(email).stream().map(FeedbackBean::new).collect(Collectors.toList());
    }

    @Override
    public void create(String name, String email, String text) {
        FeedBackEntity entity = new FeedBackEntity();
        entity.setEmail(email);
        entity.setDate(new Date());
        entity.setName(name);
        entity.setText(text);
        feedbackDao.save(entity);
        //todo send notification to user about receiving feedback
    }

    private FeedbackBean toBean(FeedBackEntity entity) {
        return new FeedbackBean(entity);
    }
}

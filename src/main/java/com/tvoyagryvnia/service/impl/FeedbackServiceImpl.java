package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.FeedbackBean;
import com.tvoyagryvnia.bean.FeedbackFilter;
import com.tvoyagryvnia.dao.IFeedbackDao;
import com.tvoyagryvnia.model.FeedBackEntity;
import com.tvoyagryvnia.service.IFeedbackService;
import com.tvoyagryvnia.util.DateUtil;
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

    @Override
    public List<FeedbackBean> getAll() {
        return feedbackDao.getAll().stream().map(FeedbackBean::new).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackBean> getByFilter(FeedbackFilter filter) {
        Date from = DateUtil.parseDate(filter.getDate().split(" - ")[0], DateUtil.DF_POINT.toPattern());
        Date to = DateUtil.parseDate(filter.getDate().split(" - ")[1], DateUtil.DF_POINT.toPattern());
        String email = filter.getEmail();
        return feedbackDao.getByFilter(from, to, email)
                .stream().map(FeedbackBean::new)
                .collect(Collectors.toList());
    }

    private FeedbackBean toBean(FeedBackEntity entity) {
        return new FeedbackBean(entity);
    }
}

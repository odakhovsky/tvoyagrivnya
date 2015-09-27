package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IFeedbackDao;
import com.tvoyagryvnia.model.FeedBackEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDaoImpl implements IFeedbackDao {

    @Autowired private SessionFactory sessionFactory;

    private Session session(){return  sessionFactory.getCurrentSession();}

    @Override
    public FeedBackEntity getById(int id) {
        return (FeedBackEntity) session().get(FeedBackEntity.class, id);
    }

    @Override
    public List<FeedBackEntity> getByEmail(String email) {
        return session().createCriteria(FeedBackEntity.class)
                .add(Restrictions.eq("email", email))
                .list();
    }

    @Override
    public void save(FeedBackEntity feedBackEntity) {
        session().saveOrUpdate(feedBackEntity);
    }
}

package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IFeedbackDao;
import com.tvoyagryvnia.model.FeedBackEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    @Override
    public List<FeedBackEntity> getAll() {
        return session().createCriteria(FeedBackEntity.class).list();
    }

    @Override
    public List<FeedBackEntity> getByFilter(Date from, Date to, String email) {
        Criteria criteria = session().createCriteria(FeedBackEntity.class);
        criteria.add(
                Restrictions.between("date", from, to)
        );
        if (StringUtils.isNotBlank(email)) {
            criteria.add(Restrictions.eq("email", email));
        }
        return criteria.list();
    }
}

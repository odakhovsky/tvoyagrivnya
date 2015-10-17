package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.INoteDao;
import com.tvoyagryvnia.model.NoteEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteDaoImpl implements INoteDao {

    private @Autowired SessionFactory sessionFactory;

    private Session session(){return sessionFactory.getCurrentSession();}

    @Override
    public NoteEntity getById(int id) {
        return (NoteEntity) session().get(NoteEntity.class, id);
    }

    @Override
    public void delete(NoteEntity note) {
        NoteEntity e = getById(note.getId());
        if (null != e) {
            e.setActive(false);
            update(e);
        }
    }

    @Override
    public void save(NoteEntity note) {
        session().save(note);
    }

    @Override
    public void update(NoteEntity note) {
        session().update(note);
    }

    @Override
    public List<NoteEntity> getAllOfUser(int user) {
        return session().createCriteria(NoteEntity.class, "n")
                .createAlias("n.owner", "o")
                .add(Restrictions.eq("o.id", user))
                .add(Restrictions.eq("active", true))
                .list();
    }

    @Override
    public List<NoteEntity> getAllOfUserByCategory(int user, int usercategory) {
        return session().createCriteria(NoteEntity.class, "n")
                .createAlias("n.owner", "o")
                .createAlias("n.category","c")
                .add(Restrictions.and(
                        Restrictions.eq("o.id", user),
                        Restrictions.eq("c.id", usercategory),
                        Restrictions.eq("active", true)

                )).list();
    }
}

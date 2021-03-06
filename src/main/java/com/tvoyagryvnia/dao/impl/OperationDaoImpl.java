package com.tvoyagryvnia.dao.impl;

import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository
public class OperationDaoImpl implements IOperationDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public OperationEntity getById(int id) {
        return (OperationEntity) session().get(OperationEntity.class, id);
    }

    @Override
    public List<OperationEntity> getAllOfUser(int user, boolean active) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .add(Restrictions.and(
                                Restrictions.eq("o.id", user),
                                Restrictions.eq("op.active", active))
                ).list();
    }

    @Override
    public List<OperationEntity> getAllOfUserByType(int user, OperationType type, boolean active) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .createAlias("op.category", "cat")
                .add(Restrictions.and(
                                Restrictions.eq("o.id", user),
                                Restrictions.eq("op.active", active),
                                Restrictions.eq("cat.operation", type)//check if works without .name()
                        )
                ).list();
    }

    @Override
    public List<OperationEntity> getAllOfUserByCategory(int user, int userCategory, boolean active) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .createAlias("op.category", "cat")
                .add(Restrictions.and(
                                Restrictions.eq("o.id", user),
                                Restrictions.eq("op.active", active),
                                Restrictions.eq("cat.id", userCategory)
                        )
                ).list();
    }

    @Override
    public List<OperationEntity> getAllOfUserByCategoryWithSubCats(int user, int mainUserCategory, boolean active) {
        return null;//todo
    }

    @Override
    public List<OperationEntity> getAllByAccount(int account) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.account", "acc")
                .add(Restrictions.and(
                                Restrictions.eq("acc.id", account),
                                Restrictions.eq("op.active", true)
                        )
                ).list();
    }

    @Override
    public List<OperationEntity> getAllByAccountAndCurrency(int account, int currency) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.account", "acc")
                .createAlias("op.currency", "curr")
                .add(Restrictions.and(
                                Restrictions.eq("acc", account),
                                Restrictions.eq("op.active", true),
                                Restrictions.eq("curr.id", currency)
                        )
                ).list();
    }

    @Override
    public List<OperationEntity> getAllOfUserByCurrency(int user, int currency) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .createAlias("op.currency", "curr")
                .add(Restrictions.and(
                                Restrictions.eq("o.id", user),
                                Restrictions.eq("op.active", true),
                                Restrictions.eq("curr.id", currency)
                        )
                ).list();
    }

    @Override
    public List<OperationEntity> getAllLast30OperationsOfAcc(int account) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.account", "acc")
                .add(Restrictions.and(
                                Restrictions.eq("acc.id", account),
                                Restrictions.eq("op.active", true)
                        )
                )
                .addOrder(Order.desc("op.date"))
                .addOrder(Order.desc("op.id"))
                .setMaxResults(30)
                .list();
    }

    @Override
    public void save(OperationEntity operation) {
        session().save(operation);
    }

    @Override
    public void update(OperationEntity operation) {
        session().update(operation);
    }

    @Override
    public void delete(OperationEntity operation) {

    }

    @Override
    public List<OperationEntity> getAllOfUserByCategory(int user, int userCategory, boolean active, Date from, Date to) {
        return session().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .createAlias("op.category", "cat")
                .add(Restrictions.and(
                                Restrictions.eq("o.id", user),
                                Restrictions.eq("op.active", active),
                                Restrictions.eq("cat.id", userCategory),
                                Restrictions.between("date", from, to)
                        )
                ).list();
    }

    public static Date getCurrentMonthFirstDate() {
        LocalDate ld = LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).withDayOfMonth(1);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date fd = Date.from(instant);
        return fd;
    }

    public static Date getCurrentMonthLastDate() {
        LocalDate ld =LocalDate.ofEpochDay(System.currentTimeMillis() / (24 * 60 * 60 * 1000)).plusMonths(1).withDayOfMonth(1).minusDays(1);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date fd = Date.from(instant);
        return fd;
    }
    @Override
    public List<OperationEntity> getAllAciveOfUserByCurrentMonth(int user) {
        Date from = getCurrentMonthFirstDate();
        Date to = getCurrentMonthLastDate();

        return sessionFactory.getCurrentSession().createCriteria(OperationEntity.class, "op")
                .createAlias("op.owner", "o")
                .add(Restrictions.and(
                        Restrictions.eq("o.id", user),
                        Restrictions.eq("op.active", true),
                        Restrictions.between("date", from, to)
                )).list();
    }

    @Override
    public List<OperationEntity> getAllAciveByCurrentMonth() {
        Date from = getCurrentMonthFirstDate();
        Date to = getCurrentMonthLastDate();

        return sessionFactory.getCurrentSession().createCriteria(OperationEntity.class, "op")
                .add(Restrictions.and(
                        Restrictions.eq("op.active", true),
                        Restrictions.between("date", from, to)
                )).list();
    }

    @Override
    public List<OperationEntity> getAll() {
        return session().createCriteria(OperationEntity.class).list();
    }

    @Override
    public void deactivate(int id) {
        session().createQuery("update OperationEntity set active = 0 where id  = :id")
                .setParameter("id", id).executeUpdate();
    }
}

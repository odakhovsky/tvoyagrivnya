package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.dao.ICurrencyDao;
import com.tvoyagryvnia.dao.IRateDao;
import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.CurrencyEntity;
import com.tvoyagryvnia.model.RateEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.DateUtil;
import com.tvoyagryvnia.util.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserCurrencyServiceImpl implements IUserCurrencyService {

    @Autowired
    private IRateDao rateDao;
    @Autowired
    private IUserCurrencyDao userCurrencyDao;
    @Autowired
    private ICurrencyDao currencyDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public void addAllForUser(int user) {
        UserEntity userr = userDao.getUserById(user);
        if (null != userr) {
            for (CurrencyEntity c : currencyDao.getAll()) {
                UserCurrencyEntity entity = new UserCurrencyEntity();
                if (c.getShortName().equals("UAH")) {
                    entity.setDef(true);
                    entity.setCrossRate(NumberFormatter.cutFloat(1f));
                } else {
                    entity.setCrossRate(NumberFormatter.cutFloat(c.getCrossRate()));
                }
                entity.setCurrency(c);
                entity.setOwner(userr);
                userCurrencyDao.save(entity);

                RateEntity rate = new RateEntity();
                rate.setDate(DateUtil.getDateWithoutTime(new Date()));
                rate.setRate(NumberFormatter.cutFloat(c.getCrossRate()));
                rate.setCurrency(entity);
                rateDao.save(rate);
            }
        }
    }

    @Override
    public ExtendedCurrencyBean getById(int id) {
        return new ExtendedCurrencyBean(userCurrencyDao.getById(id));
    }

    @Override
    public ExtendedCurrencyBean getByShortNameForUser(int user, String name) {
        return new ExtendedCurrencyBean(userCurrencyDao.getByShortNameForUser(user, name));
    }

    @Override
    public List<ExtendedCurrencyBean> getAllOfUser(int user) {
        return userCurrencyDao.getAllOfUser(user).stream().map(ExtendedCurrencyBean::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(ExtendedCurrencyBean bean) {
        UserCurrencyEntity prev = userCurrencyDao.getById(bean.getId());
        if (prev.getCrossRate() != bean.getCrossRate()) {
            RateEntity rate = new RateEntity();
            rate.setCurrency(prev);
            rate.setDate(DateUtil.getDateWithoutTime(new Date()));
            rateDao.save(rate);
        }
        prev.setCrossRate(bean.getCrossRate());
        if (!prev.isDef() && bean.isDef()) {
            recalculateRates(bean.getOwner(), prev.getCurrency().getId(), prev.getId(), prev.getCrossRate());
        }
    }

    private void recalculateRates(int owner, int currency, int prev, float prevCourse) {
        UserCurrencyEntity curr = userCurrencyDao.getById(currency);
        List<UserCurrencyEntity> currs = userCurrencyDao.getAllOfUser(owner);
        List<RateEntity> rates = rateDao.getAllOfUser(owner);
        for (RateEntity r : rates) {
            if (r.getCurrency().getId() == currency) {
                r.setRate(1f);
            } else if (r.getCurrency().getId() == prev) {
                r.setRate(NumberFormatter.cutFloat(1 / prevCourse));
                UserCurrencyEntity userCurrencyEntity = userCurrencyDao.getById(r.getCurrency().getId());
                userCurrencyEntity.setCrossRate(r.getRate());
            } else {
                r.setRate(NumberFormatter.cutFloat((1 / prevCourse) * r.getRate()));
                UserCurrencyEntity userCurrencyEntity = userCurrencyDao.getById(r.getCurrency().getId());
                userCurrencyEntity.setCrossRate(r.getRate());
            }
            rateDao.save(r);
        }

        for (UserCurrencyEntity c : currs) {
            RateEntity r = new RateEntity();
            r.setRate(c.getCrossRate());
            r.setDate(DateUtil.getDateWithoutTime(new Date()));
            r.setCurrency(c);
            rateDao.save(r);
        }
    }

    @Override
    public void setAsDefault(int user, int currency) {
        UserCurrencyEntity entity = userCurrencyDao.getById(currency);
        UserCurrencyEntity defEntity = userCurrencyDao.getDefCurrency(user);
        float prevCourse = entity.getCrossRate();
        if (null != entity) {
            if (!entity.isDef()) {
                entity.setDef(true);
                defEntity.setCrossRate(NumberFormatter.cutFloat(1.0f / entity.getCrossRate()));
                entity.setCrossRate(NumberFormatter.cutFloat(1.0f));
                defEntity.setDef(false);
                userCurrencyDao.update(entity);
                userCurrencyDao.update(defEntity);
            }
            recalculateRates(user, currency, defEntity.getId(), prevCourse);
        }
    }

    @Override
    public void updateCrossRate(int pk, float rate) {
        UserCurrencyEntity curr = userCurrencyDao.getById(pk);
        if (null != curr && !curr.isDef()) {
            curr.setCrossRate(rate);
            RateEntity r = new RateEntity();
            r.setRate(NumberFormatter.cutFloat(rate));
            r.setCurrency(curr);
            r.setDate(DateUtil.getDateWithoutTime(new Date()));
            userCurrencyDao.update(curr);
            rateDao.save(r);
        }
    }

    @Override
    public ExtendedCurrencyBean getDefaultCurrencyOfUser(int user) {
        return new ExtendedCurrencyBean(userCurrencyDao.getDefaultCurrencyOfUser(user));
    }
}

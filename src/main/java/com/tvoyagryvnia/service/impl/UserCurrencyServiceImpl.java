package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.dao.ICurrencyDao;
import com.tvoyagryvnia.dao.IRateDao;
import com.tvoyagryvnia.dao.IUserCurrencyDao;
import com.tvoyagryvnia.model.CurrencyEntity;
import com.tvoyagryvnia.model.RateEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserCurrencyServiceImpl implements IUserCurrencyService {

    @Autowired private IRateDao rateDao;
    @Autowired private IUserCurrencyDao userCurrencyDao;
    @Autowired private ICurrencyDao currencyDao;

    @Override
    public void addAllForUser(int user) {

    }

    @Override
    public ExtendedCurrencyBean getById(int id) {
        return null;
    }

    @Override
    public ExtendedCurrencyBean getByShortNameForUser(int user, String name) {
        return null;
    }

    @Override
    public List<ExtendedCurrencyBean> getAllOfUser(int user) {
        return null;
    }

    @Override
    public void update(ExtendedCurrencyBean bean) {
        UserCurrencyEntity prev = userCurrencyDao.getById(bean.getId());
        if (prev.getCrossRate() != bean.getCrossRate()) {
            RateEntity rate = new RateEntity();
            rate.setCurrency(prev);
            rate.setDate(new Date());
            rateDao.save(rate);
        }
        prev.setCrossRate(bean.getCrossRate());
        if (!prev.isDef() && bean.isDef()){
            recalculateRates(bean.getOwner());
        }
    }

    private void recalculateRates(int owner) {

    }

    @Override
    public void setAsDefault(int user, int currency) {
        UserCurrencyEntity entity = userCurrencyDao.getById(currency);
        if (null != entity){
            if (!entity.isDef()) {
                entity.setDef(true);
                entity.setCrossRate(1.0f);
            }
            recalculateRates(user);
        }
    }
}

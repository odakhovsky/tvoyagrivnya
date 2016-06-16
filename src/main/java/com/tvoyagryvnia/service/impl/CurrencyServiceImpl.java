package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.dao.ICurrencyDao;
import com.tvoyagryvnia.model.CurrencyEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.service.ICurrencyService;
import com.tvoyagryvnia.util.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CurrencyServiceImpl implements ICurrencyService {

    @Autowired private ICurrencyDao currencyDao;

    @Override
    public void create(CurrencyBean bean) {
        CurrencyEntity entity = toEntity(bean);
        entity.setCrossRate(NumberFormatter.cutFloat(entity.getCrossRate()));
        currencyDao.save(entity);
    }

    @Override
    public void create(String name, String currency, String shortName, float rate) {
        CurrencyEntity entity = new CurrencyEntity();
        entity.setShortName(shortName);
        entity.setName(name);
        entity.setCurrency(currency);
        entity.setCrossRate(NumberFormatter.cutFloat(rate));
        currencyDao.save(entity);
    }

    @Override
    public CurrencyBean getById(int id) {
        return new CurrencyBean(currencyDao.getById(id));
    }

    @Override
    public CurrencyBean getByShortName(String name) {
        return new CurrencyBean(currencyDao.getByShortName(name));
    }

    @Override
    public List<CurrencyBean> getAll() {
        return currencyDao.getAll().stream().map(CurrencyBean::new).collect(Collectors.toList());
    }

    @Override
    public void update(CurrencyBean currencyEntity) {
        currencyDao.update(toEntity(currencyEntity));
    }

    @Override
    public void updateSingleField(int curr, String fieldName, String fielValue) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        CurrencyEntity currencyEntity = currencyDao.getById(curr);
        if (!fieldName.equals("crossRate")){
            Method setter = new PropertyDescriptor(fieldName, currencyEntity.getClass()).getWriteMethod();
            setter.invoke(currencyEntity, fielValue);
        }else{
            currencyEntity.setCrossRate(Float.valueOf(fielValue));
        }
        currencyDao.update(currencyEntity);
    }

    private CurrencyEntity toEntity(CurrencyBean currencyBean) {
        CurrencyEntity entity = new CurrencyEntity();
        if (currencyBean.getId() != 0) {
            entity = currencyDao.getById(currencyBean.getId());
        }
        entity.setName(currencyBean.getName());
        entity.setCrossRate(NumberFormatter.cutFloat(currencyBean.getCrossRate()));
        entity.setCurrency(currencyBean.getCurrency());
        entity.setShortName(currencyBean.getShortName());
        return entity;
    }
}

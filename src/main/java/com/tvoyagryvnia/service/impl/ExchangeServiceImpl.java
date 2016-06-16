package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.operation.ExchangeBean;
import com.tvoyagryvnia.dao.*;
import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.model.ExchangeEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IExchangeService;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.Constants;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExchangeServiceImpl implements IExchangeService {

    @Autowired private IOperationDao operationDao;
    @Autowired private IUserCategoryDao userCategoryDao;
    @Autowired private IUserCurrencyDao userCurrencyDao;
    @Autowired private IUserDao userDao;
    @Autowired private IRateDao rateDao;
    @Autowired private IAccountDao accountDao;
    @Autowired private IExchangeDao exchangeDao;
    @Autowired private IBalanceDao balanceDao;

    @Override
    public ExchangeBean getById(int id) {
        return new ExchangeBean(exchangeDao.getById(id));
    }

    @Override
    public List<ExchangeBean> getAllByUser(int user) {
        return exchangeDao.getAllByUser(user)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .sorted((o11, o21) -> Integer.compare(o21.getId(), o11.getId()))
                .map(ExchangeBean::new).collect(Collectors.toList());
    }

    @Override
    public List<ExchangeBean> getAllFromAccount(int account) {
        return exchangeDao.getAllFromAccount(account)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .sorted((o11, o21) -> Integer.compare(o21.getId(),o11.getId()))
                .map(ExchangeBean::new).collect(Collectors.toList());
    }

    @Override
    public List<ExchangeBean> getAllToAccount(int account) {
        return exchangeDao.getAllToAccount(account)
                .stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .sorted((o11, o21) -> Integer.compare(o21.getId(),o11.getId()))
                .map(ExchangeBean::new).collect(Collectors.toList());
    }

    @Override
    public void create(String date, String description, float money, int account, int currency, int user, int accountTo, int currencyTo, float moneyTo) {

        boolean isTransfer = (!Objects.equals(account, accountTo) && Objects.equals(currency, currencyTo));
        UserCurrencyEntity currFrom = userCurrencyDao.getById(currency);
        UserCurrencyEntity currTo = userCurrencyDao.getById(currencyTo);

        UserCategoryEntity category;
        category = determineCategory(user, isTransfer);
        ExchangeEntity exchange = new ExchangeEntity();
        exchange.setOwner(userDao.getUserById(user));
        exchange.setDate(DateUtil.parseDate(date, DateUtil.DF_POINT.toPattern()));
        exchange.setCategory(category);
        exchange.setActive(true);
        exchange.setAccount(accountDao.getById(account));
        exchange.setAccountTo(accountDao.getById(accountTo));
        exchange.setCurrency(currFrom);
        exchange.setCurrencyTo(currTo);
        exchange.setCrossRate(currFrom.getLastRate());
        exchange.setCrossRateTo(currTo.getLastRate());
        exchange.setMoney(money);
        exchange.setMoneyTo(moneyTo);
        exchange.setNote(description);


        BalanceEntity balanceFrom = balanceDao.getByAccAndCurrency(account, currency);
        BalanceEntity balanceTo = balanceDao.getByAccAndCurrency(accountTo, currencyTo);

        balanceFrom.setBalance(balanceFrom.getBalance() - money);
        balanceTo.setBalance(balanceTo.getBalance() + moneyTo);

        balanceDao.save(balanceFrom);
        balanceDao.save(balanceTo);
        exchangeDao.save(exchange);
    }


    private UserCategoryEntity determineCategory(int user, boolean isTransfer) {
        UserCategoryEntity category;
        if(isTransfer){
            category = userCategoryDao.getAllByType(user, OperationType.transfer)
            .stream().filter(userCategoryEntity -> userCategoryEntity.getName().equals(Constants.CATEGORY_TRANSFER))
            .findFirst().get();
        }else {
            category = userCategoryDao.getAllByType(user, OperationType.transfer)
                    .stream().filter(userCategoryEntity -> userCategoryEntity.getName().equals(Constants.CATEGORY_EXCHANGE))
                    .findFirst().get();
        }
        return category;
    }
}
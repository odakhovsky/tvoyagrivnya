package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.dao.*;
import com.tvoyagryvnia.model.*;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IOperationService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OperationServiceImpl implements IOperationService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserCurrencyDao userCurrencyDao;
    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private IRateDao rateDao;
    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private IBalanceDao balanceDao;
    @Autowired
    private IOperationDao operationDao;


    @Override
    public OperationBean getById(int id) {
        return new OperationBean(operationDao.getById(id));
    }

    @Override
    public List<OperationBean> getAllOfUser(int user, boolean active) {
        return operationDao.getAllOfUser(user, active)
                .stream().map(OperationBean::new).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllOfUserByType(int user, OperationType type, boolean active) {

        return operationDao.getAllOfUserByType(user, type, active)
                .stream().map(OperationBean::new).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllOfUserByCategory(int user, int userCategory, boolean active) {

        return operationDao.getAllOfUserByCategory(user, userCategory, active)
                .stream().map(OperationBean::new).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllOfUserByCategoryWithSubCats(int user, int mainUserCategory, boolean active) {
        return operationDao.getAllOfUserByCategoryWithSubCats(user, mainUserCategory, active)
                .stream().map(OperationBean::new).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllByAccount(int account) {
        return operationDao.getAllByAccount(account)
                .stream().map(OperationBean::new).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllByAccountWithLimit(int account, int limit) {
        if (limit <= 0){ limit = 1;}
        return operationDao.getAllByAccount(account)
                .stream().map(OperationBean::new).limit(limit).collect(Collectors.toList());
    }

    @Override
    public List<OperationBean> getAllByAccountAndCurrency(int account, int currency) {
        return null;
    }

    @Override
    public List<OperationBean> getAllOfUserByCurrency(int user, int currency) {
        return null;
    }

    @Override
    public void create(String date, String description, float money, int account, int currency, int category, int user) {
        OperationEntity operation = new OperationEntity();
        Date d = DateUtil.parseDate(date, DateUtil.DF_POINT.toPattern());
        AccountEntity acc = accountDao.getById(account);
        UserCurrencyEntity curr = userCurrencyDao.getById(currency);
        UserCategoryEntity cate = userCategoryDao.getById(category);
        UserEntity owner = userDao.getUserById(user);
        RateEntity rate = curr.getLastRate();

        setOperationEntityFields(description, money, operation, d, acc, curr, cate, owner, rate);
        operationDao.save(operation);

        updateBalance(money, account, currency, cate);
    }

    @Override
    public List<OperationBean> getLast30Operation(int account) {
        return operationDao.getAllLast30OperationsOfAcc(account)
                .stream().map(OperationBean::new)
                .collect(Collectors.toList());
    }

    private void updateBalance(float money, int account, int currency, UserCategoryEntity cate) {
        BalanceEntity balance = balanceDao.getByAccAndCurrency(account, currency);
        OperationType type = cate.getOperation();
        float oldBalance = balance.getBalance();
        float newBalance = 0;

        switch (type) {
            case minus:
                newBalance = oldBalance - money;
                break;
            case plus:
                newBalance = oldBalance + money;
                break;
        }
        balance.setBalance(newBalance);
        balanceDao.update(balance);
    }

    private void setOperationEntityFields(String description, float money, OperationEntity operation, Date d, AccountEntity acc, UserCurrencyEntity curr, UserCategoryEntity cate, UserEntity owner, RateEntity rate) {
        operation.setCategory(cate);
        operation.setDate(d);
        operation.setOwner(owner);
        operation.setAccount(acc);
        operation.setActive(true);
        operation.setCrossRate(rate);
        operation.setCurrency(curr);
        operation.setMoney(money);
        operation.setNote(description);
    }

    @Override
    public void update(OperationBean operation) {

    }
}

package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;
import com.tvoyagryvnia.bean.currency.RateBean;
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
        if (limit <= 0) {
            limit = 1;
        }
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
    public void update(int id, String date, String description, float money, int account, int currency, int category, int user) {
        OperationBean operationBean = new OperationBean();
        operationBean.setId(id);
        operationBean.setDate(DateUtil.parseDate(date, DateUtil.DF_POINT_REVERSE.toPattern()));
        operationBean.setNote(description);
        operationBean.setMoney(money);
        operationBean.setActive(true);
        operationBean.setAccount(new AccountBean(accountDao.getById(account)));
        operationBean.setCurrency(new ExtendedCurrencyBean(userCurrencyDao.getById(currency)));
        operationBean.setCategoryId(category);
        operationBean.setOwner(user);
        operationBean.setRate(new RateBean(rateDao.getById(operationBean.getCurrency().getRateId())));
        update(operationBean);
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
        OperationEntity old = operationDao.getById(operation.getId());
        old.setCategory(userCategoryDao.getById(operation.getCategoryId()));
        old.setDate(operation.getDate());
        old.setOwner(userDao.getUserById(operation.getOwner()));
        old.setActive(operation.isActive());
        old.setNote(operation.getNote());

        float diff = Math.abs(old.getMoney() - operation.getMoney());

        //is sames accounts
        if (old.getAccount().getId() == operation.getAccount().getId()) {
            //if currencies of operation same that plus or  minus balance values
            if (old.getCurrency().getId() == operation.getCurrency().getId()) {
                BalanceEntity balance = balanceDao.getByAccAndCurrency(operation.getAccount().getId(), operation.getCurrency().getId());
                //is was more money that stay , minus
                if (old.getMoney() > operation.getMoney()) {
                    balance.setBalance(balance.getBalance() - diff);
                } else {
                    //is was lower money, that plus
                    balance.setBalance(balance.getBalance() + diff);
                }
                balanceDao.update(balance);
            } else {
                //if currencies different,  write off all sum from one currency and put all into another
                BalanceEntity balanceOld = balanceDao.getByAccAndCurrency(old.getAccount().getId(), old.getCurrency().getId());
                BalanceEntity balanceCurr = balanceDao.getByAccAndCurrency(old.getAccount().getId(), operation.getCurrency().getId());
                balanceOld.setBalance(balanceOld.getBalance() - old.getMoney());
                balanceCurr.setBalance(balanceCurr.getBalance() + operation.getMoney());
                balanceDao.update(balanceCurr);
                balanceDao.update(balanceOld);
            }
        } else {

            //if currencies of operation same that plus or  minus balance values
            if (old.getCurrency().getId() == operation.getCurrency().getId()) {
                BalanceEntity balance = balanceDao.getByAccAndCurrency(old.getAccount().getId(), operation.getCurrency().getId());
                BalanceEntity balanceNew = balanceDao.getByAccAndCurrency(operation.getAccount().getId(), operation.getCurrency().getId());
                //is was more money that stay , minus
                balance.setBalance(balance.getBalance() - old.getMoney());
                balanceNew.setBalance(balanceNew.getBalance() + operation.getMoney());
                balanceDao.update(balanceNew);
                balanceDao.update(balance);
            } else {
                //if currencies different,  write off all sum from one currency and put all into another
                BalanceEntity balanceOld = balanceDao.getByAccAndCurrency(old.getAccount().getId(), old.getCurrency().getId());
                BalanceEntity balanceCurr = balanceDao.getByAccAndCurrency(operation.getAccount().getId(), operation.getCurrency().getId());
                balanceOld.setBalance(balanceOld.getBalance() - old.getMoney());
                balanceCurr.setBalance(balanceCurr.getBalance() + operation.getMoney());
                balanceDao.update(balanceCurr);
                balanceDao.update(balanceOld);
            }
        }

        old.setAccount(accountDao.getById(operation.getAccount().getId()));
        old.setMoney(operation.getMoney());
        old.setCrossRate(rateDao.getById(operation.getRate().getId()));
        old.setCurrency(userCurrencyDao.getById(operation.getCurrency().getId()));
        operationDao.update(old);
    }
}

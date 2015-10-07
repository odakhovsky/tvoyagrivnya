package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.dao.*;
import com.tvoyagryvnia.model.AccountEntity;
import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private IBalanceDao balanceDao;
    @Autowired
    private IUserCurrencyDao userCurrencyDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IOperationDao operationDao;
    @Autowired private IExchangeDao exchangeDao;

    @Override
    public void create(int owner, String name, String description) {

        AccountEntity account = new AccountEntity();
        account.setOwner(userDao.getUserById(owner));
        account.setActive(true);
        account.setEnabled(true);
        account.setName(name);
        account.setDescription(description);
        accountDao.save(account);
        initBalancesForAccount(owner, account);
    }

    private void initBalancesForAccount(int owner, AccountEntity account) {
        for (UserCurrencyEntity curr : userCurrencyDao.getAllOfUser(owner)) {
            BalanceEntity balance = new BalanceEntity();
            balance.setCurrency(curr);
            balance.setAccount(account);
            balance.setBalance(0.0f);
            balanceDao.save(balance);
        }
    }

    @Override
    public void update(AccountBean account) {
        AccountEntity acc = accountDao.getById(account.getId());
        if (null != acc) {
            acc.setName(account.getName());
            acc.setActive(account.getActive());
            acc.setEnabled(account.getEnabled());
            acc.setDescription(account.getDescription());
            accountDao.update(acc);
            if (!acc.isActive()) {
                deactivateOperationForAccount(acc.getId());
            }
        }
    }

    private void deactivateOperationForAccount(int id) {
        AccountEntity accountEntity = accountDao.getById(id);
        if (null != accountEntity && !accountEntity.isActive()){
            for (OperationEntity operation : accountEntity.getOperations()) {
                operation.setActive(false);
                operationDao.update(operation);
            }
        }
    }

    @Override
    public void takeFromAccount(int account, int currency, float value) {
        BalanceEntity balance = balanceDao.getByAccAndCurrency(account, currency);
        float currentBalance = balance.getBalance();
        balance.setBalance(currentBalance - Math.abs(value));
        balanceDao.update(balance);
    }

    @Override
    public void putIntoAccount(int account, int currency, float value) {
        BalanceEntity balance = balanceDao.getByAccAndCurrency(account, currency);
        float currentBalance = balance.getBalance();
        balance.setBalance(currentBalance + Math.abs(value));
        balanceDao.update(balance);
    }

    @Override
    public AccountBean getById(int id) {
        return new AccountBean(accountDao.getById(id));
    }

    @Override
    public List<AccountBean> getAllOfUserActive(int user, boolean active) {
        return accountDao.getAllOfUserActive(user, active).stream().map(AccountBean::new).collect(Collectors.toList());
    }

    @Override
    public List<AccountBean> getAllOfUserEnabled(int user, boolean active, boolean enabled) {
        return accountDao.getAllOfUserEnabled(user, active, enabled).stream().map(AccountBean::new).collect(Collectors.toList());

    }

    @Override
    public boolean isAccountNameIsFree(int user, String accName) {
        List<AccountEntity> accounts = accountDao.getAllOfUserActive(user, true);
        boolean pres = accounts.stream().map(accountEntity -> accountEntity.getName().toLowerCase())
                .filter(name -> name.equals(accName.toLowerCase())).findFirst().isPresent();
        return !pres;
    }

    @Override
    public void updateSingleField(int accId, String fieldName, String fielValue) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        AccountEntity entity = accountDao.getById(accId);
        Method setter = new PropertyDescriptor(fieldName, entity.getClass()).getWriteMethod();
        setter.invoke(entity, fielValue);
        accountDao.update(entity);
    }

    @Override
    public void deactivate(int account) {
        AccountEntity accountEntity = accountDao.getById(account);
        if (null != accountEntity) {
            accountEntity.setActive(false);
            accountDao.update(accountEntity);
            deactivateOperationForAccount(account);
        }
    }
}

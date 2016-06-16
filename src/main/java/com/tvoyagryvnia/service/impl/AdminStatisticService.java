package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.AdminMainStatisticBean;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.dao.IAccountDao;
import com.tvoyagryvnia.dao.IBalanceDao;
import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.BalanceEntity;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.util.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminStatisticService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IBalanceDao balanceDao;
    @Autowired
    private IOperationDao operationDao;
    @Autowired
    private IAccountDao accountDao;

    public AdminMainStatisticBean getMainStatistic() {
        int totalUsers = userDao.getAllUsers(true).size();
        int accounts = accountDao.getTotalCount().intValue();
        Map<String, Float> middleMoney = getMiddleMoney();
        float middleMoneyToMonth = NumberFormatter.cutFloat(getMiddleMonthMoney(),2);
        float totalSpendMoney = NumberFormatter.cutFloat(getTotalSpend(OperationType.minus),2);
        float totalIncomingMoney = NumberFormatter.cutFloat(getTotalSpend(OperationType.plus),2);
        AdminMainStatisticBean bean = new AdminMainStatisticBean(totalUsers,accounts,middleMoney,middleMoneyToMonth,totalSpendMoney,totalIncomingMoney);
        return bean;
    }

    public Map<String, Float> getMiddleMoney() {
        Map<String, Float> map = new HashMap<>();
        List<BalanceEntity> balances = balanceDao.getAllActive().stream()
                .filter(bal -> bal.getBalance() > 0.0f).collect(Collectors.toList());
        for (BalanceEntity balanceEntity : balances) {
            String shortName = balanceEntity.getCurrency().getCurrency().getShortName();
            float balance = map.getOrDefault(shortName, 0.0f);
            balance += balanceEntity.getBalance();
            map.put(shortName, balance);
        }
        return map;
    }

    public float getMiddleMonthMoney() {
        final float[] spend = {0};
        Map<Integer, Float> moneys = new HashMap<>();
        List<UserEntity> userEntities = userDao.getAllUsers();
        for (UserEntity u : userEntities) {
            float money = getTotalMoney(u.getId())[0];
            if (money > 0.0f) {
                moneys.put(u.getId(), money);
            }
        }
        for (Float m : moneys.values()) {
            spend[0] += m;
        }
        if (spend[0] > 0.0f) {
            spend[0] = spend[0] / moneys.size();
        }
        return spend[0];
    }

    private float[] getTotalMoney(int id) {
        final float[] money = {0};
        List<OperationEntity> ops = operationDao.getAllOfUser(id, true)
                .stream().filter(op -> op.getCategory().getOperation().equals(OperationType.minus))
                .collect(Collectors.toList());
        ops.stream().forEach(o -> money[0] += o.getMoney());
        return money;
    }

    private float getTotalSpend(OperationType opt) {
        final float[] money = {0};
        List<OperationEntity> ops = operationDao.getAll()
                .stream().filter(op -> op.getCategory().getOperation().equals(opt))
                .collect(Collectors.toList());
        for (OperationEntity op : ops) {
            money[0] += op.getMoney();
        }
        return money[0];
    }

}

package com.tvoyagryvnia.util;


import com.tvoyagryvnia.bean.account.AccountBean;
import com.tvoyagryvnia.bean.account.BalanceBean;
import com.tvoyagryvnia.bean.account.TotalAccountBalance;
import com.tvoyagryvnia.bean.currency.CurrencyBean;
import com.tvoyagryvnia.bean.currency.ExtendedCurrencyBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class BalanceUtil {

    public static float getTotalBalanceForAllAccounts(List<AccountBean> accounts) {
        float total = 0.0f;
        for (AccountBean acc : accounts) {
            total += acc.getTotalBalance();
        }
        return NumberFormatter.cutFloat(total);
    }

    public static String getPurposeToGetMoney(List<AccountBean> accounts, ExtendedCurrencyBean currencyBean, float sum) {
        StringBuilder sb = new StringBuilder();

        if (accounts.size() == 0) {
            sb.append("Відсутня інформація по рахункам");
            return sb.toString();
        }

        CurrencyBean cb = getDefCurrency(accounts.get(0).getBalances());
        float moneyWithBaseRate = currencyBean.isDef()? sum : sum * currencyBean.getCrossRate();
        moneyWithBaseRate = NumberFormatter.cutFloat(moneyWithBaseRate, 2);

        float totalBalance = getTotalBalanceForAllAccounts(accounts);
        float diff = NumberFormatter.cutFloat(totalBalance - moneyWithBaseRate,2);
        if( totalBalance < moneyWithBaseRate){
            sb.append("На рахунках не достатньо коштів.");
            return sb.toString();
        } else  {

            System.out.println(sum + " " + currencyBean.getCurrency() + " is " + moneyWithBaseRate + " " + cb.getName());

            List<TotalAccountBalance> accountBalances = getAccountsBalances(accounts, cb);
            Collections.sort(accountBalances, (o1, o2) -> Float.compare(o2.getTotal(), o1.getTotal()));

            List<TotalAccountBalance> balances = accountBalances.stream().filter(b->b.getTotal() > 0.0).collect(Collectors.toList());
            List<Integer> accsInList = new ArrayList<>();
            float taken = 0.0f;
            for (int i = 0; i < balances.size(); i++){
                accsInList.add(balances.get(i).getAccountId());
                taken += balances.get(i).getTotal();
                sb.append(balances.get(i).getAccount()).append(",");
                if(taken >= moneyWithBaseRate){
                    break;
                }
            }
            sb.setLength(sb.length() - 1);

            if (accsInList.size() == 1){
                sb.insert(0, "Візміть гроші з рахунку ");
            }else {
                sb.insert(0, "Візміть гроші з рахунків ");
            }

            sb.append(".</br>").append("Залишок по рахункам після придбання складнатиме <b>" + diff + "</b> " + cb.getShortName());
            return sb.toString();
        }
    }

    private static List<TotalAccountBalance> getAccountsBalances(List<AccountBean> accounts, CurrencyBean defCurrency) {
        List<TotalAccountBalance> result = new ArrayList<>();

        for (AccountBean accountBean : accounts){
            TotalAccountBalance accountBalance = new TotalAccountBalance();

            accountBalance.setAccount(accountBean.getName());
            accountBalance.setAccountId(accountBean.getId());

            accountBalance.setCurrency(defCurrency.getName());
            accountBalance.setCurrencyId(defCurrency.getId());

            accountBalance.setTotal(accountBean.getTotalBalance());

            result.add(accountBalance);
        }


        return result;
    }

    private static CurrencyBean getDefCurrency(List<BalanceBean> balanceBeen) {
        CurrencyBean cb = new CurrencyBean();

        for (BalanceBean balanceBean : balanceBeen) {
            if (balanceBean.isDefCurrency()) {
                cb.setCrossRate(balanceBean.getRate());
                cb.setId(balanceBean.getId());
                cb.setName(balanceBean.getCurrName());
                cb.setShortName(balanceBean.getCurrShort());
                break;
            }
        }

        return cb;
    }

}

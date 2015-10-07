package com.tvoyagryvnia.util;


import com.tvoyagryvnia.bean.account.AccountBean;

import java.util.List;

public class BalanceUtil {

    public static float getTotalBalanceForAllAccounts(List<AccountBean> accounts){
        float total = 0.0f;
        for (AccountBean acc : accounts){
            total += acc.getTotalBalance();
        }
        return NumberFormatter.cutFloat(total);
    }

}

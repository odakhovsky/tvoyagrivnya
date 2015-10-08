package com.tvoyagryvnia.service;

import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.model.enums.OperationType;

import java.util.Date;
import java.util.List;

public interface IOperationService {



    OperationBean getById(int id);
    List<OperationBean> getAllOfUser(int user, boolean active);
    List<OperationBean> getAllOfUserByType(int user, OperationType type, boolean  active);
    List<OperationBean> getAllOfUserByCategory(int user, int userCategory, boolean active);
    List<OperationBean> getAllOfUserByCategory(int user, int userCategory, boolean active, Date from, Date to);

    List<OperationBean> getAllOfUserByCategoryWithSubCats(int user, int mainUserCategory, boolean active);

    List<OperationBean> getAllByAccount(int account);
    List<OperationBean> getAllByAccountWithLimit(int account,int limit);
    List<OperationBean> getAllByAccountAndCurrency(int account, int currency);
    List<OperationBean> getAllOfUserByCurrency(int user, int currency);

    void create(String date, String description, float money, int account, int currency, int category, int user);
    void update(int id,String date, String description, float money, int account, int currency, int category, int user);

    List<OperationBean> getLast30Operation(int account);

    void update(OperationBean operation);


}

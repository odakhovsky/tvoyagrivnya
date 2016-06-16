package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.operation.OperationBean;
import com.tvoyagryvnia.bean.reports.ReportBean;
import com.tvoyagryvnia.bean.reports.ReportFilter;
import com.tvoyagryvnia.bean.reports.ReportLineBean;
import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {

    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IOperationDao userOperation;
    @Autowired private IUserCurrencyService userCurrencyService;


    private final static int allCategories = -1;
    private final static String allTypes = "1";

    public ReportBean getReport(ReportFilter filter) {
        List<UserCategoryEntity> cats = new ArrayList<>();
        ReportBean reportBean = new ReportBean();
        reportBean.setCurrency(userCurrencyService.getDefaultCurrencyOfUser(filter.getUser()).getShortName());
        Date from = DateUtil.parseDate(filter.getPeriod().split(" - ")[0], DateUtil.DF_POINT.toPattern());
        Date to = DateUtil.parseDate(filter.getPeriod().split(" - ")[1], DateUtil.DF_POINT.toPattern());

        if (filter.getCategory() == allCategories) {
            cats.addAll(userCategoryDao.getAll(filter.getUser(), true));
        } else {
            cats.add(userCategoryDao.getById(filter.getCategory()));
        }
        if (!filter.getType().equals(allTypes)) {
            cats = cats.stream().filter(c -> c.getOperation().equals(OperationType.valueOf(filter.getType())))
                    .collect(Collectors.toList());
        }

        if (cats.size() == 1) {
            //if only one category
            ReportLineBean line = new ReportLineBean(cats.get(0).getName(), calcSum(getOperations(cats.get(0), from, to)));
            if (cats.get(0).getOperation().equals(OperationType.plus)) {
                reportBean.getIncomings().add(line);
            } else if (cats.get(0).getOperation().equals(OperationType.minus)) {
                reportBean.getSpendings().add(line);
            }
            //if category has child's than gat all, that has value > 0
            if (cats.get(0).getChildrens().size() > 0) {
                reportBean = addLineForChildren(cats.get(0).getChildrens(), reportBean, from, to);
            }
            reportBean.getOperations().addAll(getOperations(cats.get(0), from, to).stream().map(OperationBean::new)
                    .filter(op -> op.getType().equals(OperationType.plus.name()) || op.getType().equals(OperationType.minus.name()))
                    .collect(Collectors.toList()));
        } else {
            for (UserCategoryEntity cat : cats) {
                ReportLineBean line = new ReportLineBean(cat.getName(), calcSum(getOperations(cat, from, to)));
                if (line.getValue() > 0) {
                    if (cat.getOperation().equals(OperationType.plus)) {
                        reportBean.getIncomings().add(line);
                    } else if (cat.getOperation().equals(OperationType.minus)) {
                        reportBean.getSpendings().add(line);
                    }
                    reportBean.getOperations().addAll(getOperations(cat, from, to).stream().map(OperationBean::new)
                            .filter(op -> op.getType().equals(OperationType.plus.name()) || op.getType().equals(OperationType.minus.name()))
                            .collect(Collectors.toList()));
                }
            }
        }

        return reportBean;
    }

    private ReportBean addLineForChildren(Set<UserCategoryEntity> childrens, ReportBean report, Date from, Date to) {
        for (UserCategoryEntity cat : childrens) {
            ReportLineBean line = new ReportLineBean(cat.getName(), calcSum(getOperations(cat, from, to)));
            if (line.getValue() > 0) {
                if (cat.getOperation().equals(OperationType.plus)) {
                    report.getIncomings().add(line);
                } else if (cat.getOperation().equals(OperationType.minus)) {
                    report.getSpendings().add(line);
                }
                if (cat.getChildrens().size() > 0) {
                    addLineForChildren(cat.getChildrens(), report, from, to);
                }
                report.getOperations().addAll(getOperations(cat, from, to).stream().map(OperationBean::new)
                        .filter(op -> op.getType().equals(OperationType.plus.name()) || op.getType().equals(OperationType.minus.name()))
                        .collect(Collectors.toList()));
            }
        }
        return report;
    }

    private float calcSum(List<OperationEntity> operations) {
        float sum = 0.0f;
        for (OperationEntity op : operations) {
            sum += op.getMoney() * op.getCurrency().getCrossRate();
        }
        return sum;
    }

    private List<OperationEntity> getOperations(UserCategoryEntity category, Date from, Date to) {
        return userOperation.getAllOfUserByCategory(category.getOwner().getId(), category.getId(), true, from, to);
    }

}

package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.bean.Pair;
import com.tvoyagryvnia.bean.analysis.AnalysisBean;
import com.tvoyagryvnia.bean.analysis.ExtendedAnalysisBean;
import com.tvoyagryvnia.bean.analysis.Line;
import com.tvoyagryvnia.dao.IOperationDao;
import com.tvoyagryvnia.dao.IUserCategoryDao;
import com.tvoyagryvnia.model.OperationEntity;
import com.tvoyagryvnia.model.UserCategoryEntity;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.IUserCategoryService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.util.DateUtil;
import com.tvoyagryvnia.util.NumberFormatter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnalysisService {


    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private IOperationDao operationDao;
    @Autowired
    private IUserCategoryService userCategoryService;
    @Autowired
    private IUserCurrencyService userCurrencyService;

    public AnalysisBean analysis(String range, int userId) {

        Date from = DateUtil.parseDate(range.split(" - ")[0], DateUtil.DF_POINT.toPattern());
        Date to = DateUtil.parseDate(range.split(" - ")[1], DateUtil.DF_POINT.toPattern());
        AnalysisBean result = new AnalysisBean();
        result.setRange(range);

        List<Line> lines = new ArrayList<>();
        try {

            List<OperationEntity> operations = new ArrayList<>();
            List<UserCategoryEntity> categories = userCategoryDao.getAllByType(userId, OperationType.minus);

            for (UserCategoryEntity category : categories) {
                List<OperationEntity> oper = operationDao.getAllOfUserByCategory(userId, category.getId(), true, from, to);
                operations.addAll(oper);
            }

            float total = calcSum(operations);
            for (UserCategoryEntity cat : categories.stream().filter(c -> Objects.isNull(c.getParent())).collect(Collectors.toList())) {
                float sum = NumberFormatter.cutFloat(
                        userCategoryService.getSumOfCategoryAndSubCategoriesIfPresent(cat.getId(), from, to)
                        , 2);
                float percent = NumberFormatter.cutFloat(((sum / total) * 100), 2);
                if (sum > 0.0) {
                    Line line = new Line();
                    line.setId(cat.getId());
                    line.setName(cat.getName());
                    line.setPercent(percent);
                    line.setMoney(sum);
                    line.setCurr(userCurrencyService.getDefaultCurrencyOfUser(userId).getShortName());
                    if (cat.getChildrens().size() > 0) {
                        fillChildrens(cat.getChildrens(), line, total, from, to);
                    }
                    lines.add(line);
                }
            }
            result.setCategories(lines);
            result.setTotal(NumberFormatter.cutFloat(total, 2));
            result.setCurr(result.getCategories().get(0).getCurr());
            Collections.sort(result.getCategories(), this::compare);
            return result;
        } catch (Exception ex) {
            return new AnalysisBean();
        }
    }

    private void fillChildrens(Set<UserCategoryEntity> cat, Line line, float total, Date from, Date to) {

        for (UserCategoryEntity category : cat) {
            float sum = NumberFormatter.cutFloat(
                    userCategoryService.getSumOfCategoryAndSubCategoriesIfPresent(category.getId(), from, to)
                    , 2);
            float percent = NumberFormatter.cutFloat(((sum / total) * 100), 2);
            if (sum > 0.0) {
                Line l = new Line();
                l.setId(category.getId());
                l.setName(category.getName());
                l.setPercent(percent);
                l.setMoney(sum);

                l.setCurr(userCurrencyService.getDefaultCurrencyOfUser(category.getOwner().getId()).getShortName());

             /*   if (line.getMoney() != l.getMoney()) {
                    line.getSublines().add(l);
                }*/
                //todo check

                if (category.getChildrens().size() > 0) {
                    fillChildrens(category.getChildrens(), l, total, from, to);
                }
            }
        }
    }

    private float diffMoneyLineAndSubLines(Line line) {
        float diff = line.getMoney();
        float totalSub = 0.0f;
        for (Line l : line.getSublines()) {
            totalSub += l.getMoney();
        }
        return diff - totalSub;
    }

    private float calcPerncents(List<Line> sublines) {
        float sum = 0.0f;
        for (Line line : sublines) {
            sum += line.getPercent();
        }
        return sum;
    }


    public int compare(Line bean, Line bean2) {

        return new CompareToBuilder()
                .append(bean2.getPercent(), bean.getPercent())
                .append(bean2.getValue(), bean.getValue())
                .toComparison();
    }

    private float calcSum(List<OperationEntity> operations) {
        float sum = 0.0f;
        for (OperationEntity op : operations) {
            sum += op.getMoney() * op.getCurrency().getCrossRate();
        }
        return sum;
    }


    private List<OperationEntity> getOperations(UserCategoryEntity category, Date from, Date to) {
        return operationDao.getAllOfUserByCategory(category.getOwner().getId(), category.getId(), true, from, to);
    }


    public ExtendedAnalysisBean analysis(String from, String to, int userId) {
        Map<String, Pair<Integer, Float>> map = new LinkedHashMap<>();
        AnalysisBean first = analysis(from, userId);
        AnalysisBean second = analysis(to, userId);

        for (Line line : first.getCategories()) {
            Pair<Integer, Float> pair = new Pair<>(line.getValue(), line.getPercent());
            map.put(line.getName(), pair);
            if (isLineHasSubLines(line)) {
                fillMapLine(line, map);
            }
        }

        for (Line line : second.getCategories()) {
            Pair<Integer, Float> pair = map.getOrDefault(line.getName(), null);
            if (!Objects.isNull(pair)) {
                line.setDiff(NumberFormatter.cutFloat(pair.second - line.getPercent(), 2));
                if (isLineHasSubLines(line)) {
                    fillDiffs(line, map);
                }
            }
        }

        ExtendedAnalysisBean result = new ExtendedAnalysisBean();
        result.setCurr(userCurrencyService.getDefaultCurrencyOfUser(userId).getShortName());
        result.setFirst(first);
        result.setSecond(second);
        result.setRange(from + " " + to);
        return result;
    }


    private void fillDiffs(Line mainLine, Map<String, Pair<Integer, Float>> map) {
        for (Line line : mainLine.getSublines()) {
            Pair<Integer, Float> pair = map.getOrDefault(line.getName(), null);
            if (!Objects.isNull(pair)) {
                line.setDiff(NumberFormatter.cutFloat(pair.second - line.getPercent(), 2));
                if (isLineHasSubLines(line)) {
                    fillMapLine(line, map);
                }
            }
        }
    }

    private boolean isLineHasSubLines(Line line) {
        return line.getSublines().size() > 0;
    }

    private void fillMapLine(Line mainLine, Map<String, Pair<Integer, Float>> map) {
        for (Line line : mainLine.getSublines()) {
            Pair<Integer, Float> pair = new Pair<>(line.getValue(), line.getPercent());
            map.put(line.getName(), pair);
            if (isLineHasSubLines(line)) {
                fillMapLine(line, map);
            }
        }
    }
}

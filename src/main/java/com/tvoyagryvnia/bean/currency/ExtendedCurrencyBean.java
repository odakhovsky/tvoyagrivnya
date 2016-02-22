package com.tvoyagryvnia.bean.currency;

import com.tvoyagryvnia.model.RateEntity;
import com.tvoyagryvnia.model.UserCurrencyEntity;
import com.tvoyagryvnia.util.DateUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ExtendedCurrencyBean extends CurrencyBean {

    protected int id;
    protected float crossRate;
    private int owner;
    private boolean def;
    private Date date;
    private String stringDate;
    private int rateId;

    public ExtendedCurrencyBean(UserCurrencyEntity currencyEntity) {
        super(currencyEntity.getCurrency());
        this.id = currencyEntity.getId();
        this.crossRate = currencyEntity.getCrossRate();
        this.owner = currencyEntity.getOwner().getId();
        this.def = currencyEntity.isDef();
        this.date = DateUtil.getDateWithoutTime(getLastDate(currencyEntity.getCrossRates().stream().map(RateEntity::getDate).collect(Collectors.toList())));
        this.stringDate = DateUtil.DF_POINT.format(this.date);
        this.rateId = currencyEntity.getCrossRates().stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .map(RateEntity::getId).collect(Collectors.toList()).get(0);
    }

    private Date getLastDate(List<Date> dates){
        return dates.stream().sorted((o1, o2) -> o2.compareTo(o1)).collect(Collectors.toList()).get(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(float crossRate) {
        this.crossRate = crossRate;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }
}

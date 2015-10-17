package com.tvoyagryvnia.model;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCY")
public class CurrencyEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "CURR_CROSS_RATE")
    private float crossRate;

    public CurrencyEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public float getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(float crossRate) {
        this.crossRate = crossRate;
    }
}

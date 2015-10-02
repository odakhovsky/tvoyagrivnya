package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_CURRENCY_RATE")
public class RateEntity {

    @Id @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private UserCurrencyEntity currency;

    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "RATE")
    private Float rate;

    public RateEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrencyEntity currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}

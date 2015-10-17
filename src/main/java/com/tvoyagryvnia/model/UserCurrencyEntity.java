package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "USER_CURRENCY")
public class UserCurrencyEntity {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "CURRENCY")
    private CurrencyEntity currency;

    @Column(name = "CROSS_RATE")
    private Float crossRate;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @Column(name = "DEF")
    private boolean def;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.PERSIST)
    @OrderBy("date DESC")
    private List<RateEntity> crossRates;

    public UserCurrencyEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public Float getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(Float crossRate) {
        this.crossRate = crossRate;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public List<RateEntity> getCrossRates() {
        return crossRates;
    }

    public void setCrossRates(List<RateEntity> crossRates) {
        this.crossRates = crossRates;
    }

    public RateEntity getLastRate() {
        return crossRates.stream().sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList()).get(0);
    }
}

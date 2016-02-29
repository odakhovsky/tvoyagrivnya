package com.tvoyagryvnia.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTE")
public class NoteEntity {

    @Id @GeneratedValue
    private int id;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "CATEGORY")
    private UserCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private UserCurrencyEntity currency;

    @Column(name = "SUM")
    private float sum;

    public NoteEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public UserCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(UserCategoryEntity category) {
        this.category = category;
    }

    public UserCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrencyEntity currency) {
        this.currency = currency;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}

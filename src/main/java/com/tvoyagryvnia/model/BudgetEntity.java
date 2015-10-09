package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BUDGET")
public class BudgetEntity {

    @Id @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM")
    private Date from;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO")
    private Date to;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @OneToMany(mappedBy = "budget")
    List<BudgetLineEntity> categories;

    public BudgetEntity(){}

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

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
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

    public List<BudgetLineEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<BudgetLineEntity> categories) {
        this.categories = categories;
    }
}

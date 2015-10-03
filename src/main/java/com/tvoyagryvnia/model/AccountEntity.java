package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

    @Id @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @OneToMany(mappedBy = "account")
    private List<BalanceEntity> balances;

    public AccountEntity(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<BalanceEntity> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceEntity> balances) {
        this.balances = balances;
    }
}

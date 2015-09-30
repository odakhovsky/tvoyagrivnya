package com.tvoyagryvnia.model;

import com.tvoyagryvnia.model.enums.OperationType;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "USER_CATEGORY")
public class UserCategoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @Column(name = "OPERATION_TYPE")
    @Enumerated(EnumType.STRING)
    private OperationType operation;

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY")
    private UserCategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<UserCategoryEntity> childrens;

    @ManyToOne
    @JoinColumn(name = "MAIN_CATEGORY")
    private CategoryEntity main;

    public UserCategoryEntity(){}

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

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public UserCategoryEntity getParent() {
        return parent;
    }

    public void setParent(UserCategoryEntity parent) {
        this.parent = parent;
    }

    public Set<UserCategoryEntity> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<UserCategoryEntity> childrens) {
        this.childrens = childrens;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CategoryEntity getMain() {
        return main;
    }

    public void setMain(CategoryEntity main) {
        this.main = main;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}

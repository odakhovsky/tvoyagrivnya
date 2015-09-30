package com.tvoyagryvnia.model;

import com.tvoyagryvnia.model.enums.OperationType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {

    @Id @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;


    @Column(name = "ACTIVE")
    private Boolean active = true;

    @Column(name = "OPERATION_TYPE")
    @Enumerated(EnumType.STRING)
    private OperationType operation;

    @ManyToOne
    @JoinColumn(name = "PARENT_CATEGORY")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<CategoryEntity> childrens;

    public CategoryEntity(){}

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

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public Set<CategoryEntity> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<CategoryEntity> childrens) {
        this.childrens = childrens;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

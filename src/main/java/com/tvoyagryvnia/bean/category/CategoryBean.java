package com.tvoyagryvnia.bean.category;


import com.tvoyagryvnia.model.CategoryEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryBean {

    private int id;
    private String name;
    private int parent;
    private String parentName;
    private Set<CategoryBean> childrens;
    private String operation;
    private Boolean active;

    public CategoryBean(CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.parent = (null == entity.getParent()) ? 0 : entity.getParent().getId();
        if (parent != 0) {
            parentName = entity.getParent().getName();
        }
        this.childrens = (null == entity.getChildrens()) ? new HashSet<>() : entity.getChildrens()
                .stream().map(CategoryBean::new).collect(Collectors.toSet());
        this.operation = entity.getOperation().name();
        this.active = entity.getActive();
    }

    public CategoryBean(CategoryBean bean) {
        this.id = bean.getId();
        this.name = bean.getName();
        this.parent = bean.getParent();
        this.childrens = bean.getChildrens();
        this.operation = bean.getOperation();
        this.active = bean.getActive();
    }

    public CategoryBean(){}

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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Set<CategoryBean> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<CategoryBean> childrens) {
        this.childrens = childrens;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}

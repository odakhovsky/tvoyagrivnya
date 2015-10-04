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
}

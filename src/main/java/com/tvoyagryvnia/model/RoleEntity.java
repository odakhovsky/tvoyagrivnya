package com.tvoyagryvnia.model;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
public class RoleEntity {

    public enum Name {
        ROLE_ADMIN, ROLE_OWNER, ROLE_MEMBER,ROLE_SUPER_MEMBER
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false)
    @Enumerated(EnumType.STRING)
    private Name name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}

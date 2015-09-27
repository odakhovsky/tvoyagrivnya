package com.tvoyagryvnia.model;

import javax.persistence.*;

@Entity
@Table(name = "USER_SETTINGS")
public class UserSettingsEntity {

    public enum Switcher {
        OFF(false), ON(true);

        private Boolean value;

        Switcher(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public UserSettingsEntity(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
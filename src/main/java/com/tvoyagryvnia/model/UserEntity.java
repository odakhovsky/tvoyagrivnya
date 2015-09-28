package com.tvoyagryvnia.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String name;


    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
    )
    private Set<RoleEntity> roles;

    @OneToMany
    @JoinTable(
            name = "USER_MEMBERS",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MEMBER_ID")}
    )
    private Set<UserEntity> members;

    @ManyToOne
    @JoinTable(
            name = "USER_MEMBERS",
            joinColumns = {@JoinColumn(name = "MEMBER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
    )
    private UserEntity inviter;

    @OneToOne
    @JoinColumn(name = "SETTINGS_ID", updatable=true)
    private UserSettingsEntity settings;

    public UserEntity(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public UserSettingsEntity getSettings() {
        return settings;
    }

    public void setSettings(UserSettingsEntity settings) {
        this.settings = settings;
    }

    public boolean hasRole(RoleEntity.Name role) {
        return roles.stream().anyMatch(roleEntity -> roleEntity.getName().equals(role));
    }

    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public UserEntity getInviter() {
        return inviter;
    }

    public void setInviter(UserEntity inviter) {
        this.inviter = inviter;
    }
}

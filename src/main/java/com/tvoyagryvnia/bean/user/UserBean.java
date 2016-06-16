package com.tvoyagryvnia.bean.user;


import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.util.validation.login.Login;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserBean {

    private Integer id;

    private String name;

    @NotEmpty
    @Login(message = "Користувач з такою поштою вже існує")
    private String email;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Past
    private Date dateOfBirth;

    private String password;

    private Integer inviter;

    private Set<Integer> roles = new HashSet<>();

    private boolean active;

    private boolean owner;

    private boolean superMember;

    public UserBean() {
        roles = new HashSet<>();
    }

    public UserBean(UserEntity userEntity) {

        roles = userEntity.getRoles().stream().map(RoleEntity::getId).collect(Collectors.toSet());
        password = userEntity.getPassword();
        id = userEntity.getId();
        name = userEntity.getName();
        email = userEntity.getEmail();
        this.dateOfBirth = (null == userEntity.getDateOfBirth()) ? null : userEntity.getDateOfBirth();
        active = userEntity.getActive();
        this.owner = userEntity.hasRole(RoleEntity.Name.ROLE_OWNER);
        this.superMember = userEntity.hasRole(RoleEntity.Name.ROLE_SUPER_MEMBER);
    }

    public UserBean(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserEntity toEntity(UserEntity userEntity) {
        if (id != null) {
            userEntity.setId(id);
        }
        userEntity.setName(name);
        userEntity.setEmail(email);

        return userEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isSuperMember() {
        return superMember;
    }

    public void setSuperMember(boolean superMember) {
        this.superMember = superMember;
    }

    public Integer getInviter() {
        return inviter;
    }

    public void setInviter(Integer inviter) {
        this.inviter = inviter;
    }
}

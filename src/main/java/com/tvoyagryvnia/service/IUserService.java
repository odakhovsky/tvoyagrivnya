package com.tvoyagryvnia.service;


import com.tvoyagryvnia.bean.user.EditUserPass;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public interface IUserService {

    public int saveUser(UserBean user);

    public String deleteUser(int userId);

    public List<UserBean> getAllUsers(boolean activeStatus);

    public List<UserBean> getAllUsers();

    public UserBean getUserById(int id);

    public UserBean getUserByEmail(String email);

    public boolean isUserLoginFree(String userLogin);

    public UserBean findUserByLogin(String login);

    public void updateUser(UserBean userBean);

    public Set<UserBean> getAllUsersAsSet();

    public boolean hasRole(int userId, RoleEntity.Name roleName);

    public UserEntity toUserEntity(UserBean user);

    UserBean inviteNewUser(UserBean inviter, UserBean invited);

    List<UserBean> getUserMembers(int userId);

    public void updateSingleField(int userId, String fieldName, String fielValue)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException;

    public void addRole(int userId, RoleEntity.Name role);

    public void removeRole(int userId, RoleEntity.Name role);

    public void updateUser(EditUserPass user);


}

package com.tvoyagryvnia.dao;

import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 02.09.2015.
 */
public interface IUserDao {

   UserEntity findUserByEmail(String email);

   List<UserEntity> getUserMembers(int user);

   public int saveUser(UserEntity user);

   public void updateUser(UserEntity user);

   public void deleteUser(UserEntity user);

   public List<UserEntity> getAllUsers(boolean activeStatus);

   public List<UserEntity> getAllUsers();

   public UserEntity getUserById(int id);

   boolean isUserLoginFree(String userLogin);

   public UserEntity findUserByToken(String token);

   public UserEntity findUserByLogin(String login);

   public void addRole(UserEntity user, RoleEntity.Name roleName);

   public void removeRole(UserEntity user, RoleEntity.Name roleName);

   public List<UserEntity> getUsersByIds(Set<Integer> usersId);
}

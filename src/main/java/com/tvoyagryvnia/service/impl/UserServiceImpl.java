package com.tvoyagryvnia.service.impl;


import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.dao.IUserSettingsDao;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.model.UserSettingsEntity;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.util.Cipher;
import com.tvoyagryvnia.util.password.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;
    @Autowired
    IUserSettingsDao userSettingsDao;
    @Autowired
    IRoleDao roleDao;
    @Autowired
    PasswordGenerator passwordGenerator;

    @Override
    public int saveUser(UserBean user) {

        if(isUserLoginFree(user.getEmail())) {

            String password = passwordGenerator.generate();
            user.setPassword(password);
            System.out.println(password);
//todo remove sout
            UserEntity userEntity = toUserEntity(user);
            userEntity.setActive(true);
            userEntity.setPassword(Cipher.encrypt(user.getPassword()));


            UserSettingsEntity settings = new UserSettingsEntity();
            userSettingsDao.saveOrUpdate(settings);

            userEntity.setSettings(settings);
            int userID = userDao.saveUser(userEntity);
            userEntity = userDao.getUserById(userID);
            settings.setUser(userEntity);
            userSettingsDao.saveOrUpdate(settings);


            userDao.addRole(userEntity, RoleEntity.Name.ROLE_MEMBER);
            userDao.addRole(userEntity, RoleEntity.Name.ROLE_SUPER_MEMBER);
            userDao.addRole(userEntity, RoleEntity.Name.ROLE_OWNER);

            //todo send notification to user
            return userID;

        }
        return 0;
    }


    @Override
    public String deleteUser(int userId) {
        UserEntity entity = userDao.getUserById(userId);
        entity.setActive(false);
        userDao.updateUser(entity);
        return entity.getName();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<UserBean> getAllUsers(boolean activeStatus) {
        return userDao.getAllUsers(activeStatus).stream().map(UserBean::new).collect(Collectors.toList());
    }

    @Override
    public List<UserBean> getAllUsers() {
        return getAllUsers(true);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserBean getUserById(int id) {
        return new UserBean(userDao.getUserById(id));
    }

    @Override
    public UserBean getUserByEmail(String email) {
        UserEntity userEntity = userDao.findUserByEmail(email);
        if (userEntity != null) {
            return new UserBean(userEntity);
        }
        return null;
    }

    @Override
    public boolean isUserLoginFree(String userLogin) {
        return userDao.isUserLoginFree(userLogin);
    }

    @Override
    public UserBean findUserByLogin(String login) {
        return new UserBean(userDao.findUserByLogin(login));
    }

    @Override
    public void updateUser(UserBean userBean) {
        userDao.updateUser(toUserEntity(userBean));
    }


    @Override
    public Set<UserBean> getAllUsersAsSet() {
        return new HashSet<>(getAllUsers());
    }


    @Override
    public boolean hasRole(int userId, RoleEntity.Name roleName) {
        return false;
    }


    private Set<RoleEntity> toRoleEntitySet(Set<Integer> userBeanRolesSet) {
        return userBeanRolesSet.isEmpty() ? new HashSet<>() : new HashSet<>(roleDao.getRolesByIds(userBeanRolesSet));
    }

    @Override
    public UserEntity toUserEntity(UserBean userBean) {
        UserEntity userEntity = userBean.toEntity(new UserEntity());

        userEntity.setEmail(userBean.getEmail());
        userEntity.setDateOfBirth(userBean.getDateOfBirth());
        userEntity.setPassword(userBean.getPassword());
        userEntity.setRoles(toRoleEntitySet(userBean.getRoles()));
        userEntity.setActive(userBean.isActive());
        return userEntity;
    }


}

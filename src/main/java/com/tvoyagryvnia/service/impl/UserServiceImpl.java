package com.tvoyagryvnia.service.impl;


import com.tvoyagryvnia.bean.user.EditUserPass;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.dao.IUserSettingsDao;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;
import com.tvoyagryvnia.model.UserSettingsEntity;
import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.util.Cipher;
import com.tvoyagryvnia.util.password.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
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

    @Autowired
    private ISendMailService sendMailService;

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

            sendMailService.sendRegistrationInformation(user.getName(), user.getEmail(), password);
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

        UserEntity entity = toUserEntity(userBean);

        userDao.updateUser(entity);
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

    private Set<UserEntity> toMemberEntitySet(Set<Integer> userBeanMemberSet) {
        return userBeanMemberSet.isEmpty() ? new HashSet<>() : new HashSet<>(userDao.getUsersByIds(userBeanMemberSet));
    }

    @Override
    public UserEntity toUserEntity(UserBean userBean) {
        UserEntity userEntity = userBean.toEntity(userDao.getUserById(userBean.getId()));

        userEntity.setEmail(userBean.getEmail());
        userEntity.setDateOfBirth(userBean.getDateOfBirth());
        userEntity.setPassword(userBean.getPassword());
        userEntity.setRoles(toRoleEntitySet(userBean.getRoles()));
        userEntity.setMembers(toMemberEntitySet(userBean.getMembers().stream().map(UserBean::getId).collect(Collectors.toSet())));
        userEntity.setActive(userBean.isActive());

        if (null != userBean.getInviter()) {
            userEntity.setInviter(userDao.getUserById(userBean.getInviter()));
        }

        userEntity.setSettings(userSettingsDao.getByUserID(userBean.getId()));

        return userEntity;
    }

    @Override
    public UserBean inviteNewUser(UserBean inviter, UserBean invited) {
        System.out.println(invited);
        String password = passwordGenerator.generate();
        invited.setPassword(password);
        System.out.println(password);

//todo remove sout
        UserEntity userEntity = toUserEntity(invited);
        userEntity.setActive(true);
        userEntity.setPassword(Cipher.encrypt(invited.getPassword()));
        userEntity.setInviter(toUserEntity(inviter));


        UserSettingsEntity settings = new UserSettingsEntity();
        userSettingsDao.saveOrUpdate(settings);

        userEntity.setSettings(settings);
        int userID = userDao.saveUser(userEntity);
        userEntity = userDao.getUserById(userID);
        settings.setUser(userEntity);
        userSettingsDao.saveOrUpdate(settings);


        userDao.addRole(userEntity, RoleEntity.Name.ROLE_MEMBER);
        if (invited.isSuperMember()) {
            userDao.addRole(userEntity, RoleEntity.Name.ROLE_SUPER_MEMBER);
        }
        sendMailService.sendInviteInformation(inviter.getName(), invited.getName(), invited.getEmail(), password);
        return invited;
    }

    @Override
    public List<UserBean> getUserMembers(int userId) {
        return userDao.getUserMembers(userId).stream().map(UserBean::new).collect(Collectors.toList());
    }

    @Override
    public void updateSingleField(int userId, String fieldName, String fielValue) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        UserEntity user = userDao.getUserById(userId);

        Method setter = new PropertyDescriptor(fieldName, user.getClass()).getWriteMethod();
        setter.invoke(user, fielValue);
        userDao.updateUser(user);
    }

    @Override
    public void addRole(int userId, RoleEntity.Name role) {
        UserEntity user = userDao.getUserById(userId);
        if (null != user) {
            RoleEntity roleEntity = roleDao.getRoleByName(role);
            if (null != roleEntity) {
                userDao.addRole(user, role);
            }
        }
    }

    @Override
    public void removeRole(int userId, RoleEntity.Name role) {
        UserEntity user = userDao.getUserById(userId);
        if (null != user) {
            RoleEntity roleEntity = roleDao.getRoleByName(role);
            if (null != roleEntity) {
                userDao.removeRole(user, role);
            }
        }
    }


    private UserEntity toUserEntity(EditUserPass editUserPass) {
        UserEntity userEntity = userDao.getUserById(editUserPass.getUserId());
        if (userEntity == null) {
            userEntity = userDao.findUserByLogin(editUserPass.getEmail());
        }
        userEntity.setActive(true);
        userEntity.setPassword(Cipher.encrypt(editUserPass.getPassword()));
        return userEntity;
    }

    @Override
    public void updateUser(EditUserPass user) {
        userDao.updateUser(toUserEntity(user));
        sendMailService.sendPasswordUpdateNotification(user.getEmail(), user.getPassword());
    }


}

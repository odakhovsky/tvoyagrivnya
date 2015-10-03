package com.tvoyagryvnia.service.impl;


import com.tvoyagryvnia.bean.user.EditUserPass;
import com.tvoyagryvnia.bean.user.UserBean;
import com.tvoyagryvnia.dao.*;
import com.tvoyagryvnia.model.*;
import com.tvoyagryvnia.model.enums.OperationType;
import com.tvoyagryvnia.service.ISendMailService;
import com.tvoyagryvnia.service.IUserCurrencyService;
import com.tvoyagryvnia.service.IUserService;
import com.tvoyagryvnia.util.Cipher;
import com.tvoyagryvnia.util.password.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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
    private ICategoryDao baseCategoriesDao;
    @Autowired
    private IUserCategoryDao userCategoryDao;
    @Autowired
    private ISendMailService sendMailService;
    @Autowired
    private IUserCurrencyService currencyService;

    @Override
    public int saveUser(UserBean user) {

        if (isUserLoginFree(user.getEmail())) {

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

            addCategoriesForUser(userEntity);
            currencyService.addAllForUser(userID);
            return userID;

        }
        return 0;
    }

    private void addCategoriesForUser(UserEntity userEntity) {
        fillCategories(userEntity, OperationType.plus);
        fillCategories(userEntity, OperationType.minus);
    }

    private void fillCategories(UserEntity userEntity, OperationType type) {
        List<CategoryEntity> categoryEntities = baseCategoriesDao.getAllByType(type)
                .stream().filter(cat -> Objects.isNull(cat.getParent()))
                .collect(Collectors.toList());

        for (CategoryEntity cat : categoryEntities) {
            UserCategoryEntity entity = fillUserCategory(userEntity, cat);
            entity.setOwner(userEntity);
            userCategoryDao.save(entity);
            if (cat.getChildrens().size() > 0) {
                fillCategory(cat.getChildrens(), entity);
            }
        }
    }

    private void fillCategory(Set<CategoryEntity> childrens, UserCategoryEntity parent) {
        for (CategoryEntity c : childrens) {
            UserCategoryEntity entity = fillUserCategory(parent.getOwner(), c);
            entity.setParent(parent);
            entity.setOwner(parent.getOwner());
            userCategoryDao.save(entity);
            if (c.getChildrens().size() > 0) {
                fillCategory(c.getChildrens(), entity);
            }
        }

    }

    private UserCategoryEntity fillUserCategory(UserEntity userEntity, CategoryEntity categoryEntity) {
        UserCategoryEntity entity = new UserCategoryEntity();
        entity.setActive(true);
        entity.setName(categoryEntity.getName());
        entity.setMain(categoryEntity);
        entity.setOperation(categoryEntity.getOperation());
        entity.setOwner(userEntity);
        return entity;
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
        UserEntity userEntity = userBean.toEntity(new UserEntity());
        if (isRegisteredUser(userBean)) {
            userEntity = userBean.toEntity(userDao.getUserById(userBean.getId()));
        }

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

    private boolean isRegisteredUser(UserBean userBean) {
        return null != userBean.getId() && userBean.getId() > 0;
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

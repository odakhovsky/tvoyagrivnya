package com.tvoyagryvnia.service.impl;

import com.tvoyagryvnia.dao.IRoleDao;
import com.tvoyagryvnia.dao.IUserDao;
import com.tvoyagryvnia.model.RoleEntity;
import com.tvoyagryvnia.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired private IUserDao userDao;
    @Autowired private IRoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userDao.findUserByEmail(email);

        Set<RoleEntity> roles = user.getRoles();
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }

}

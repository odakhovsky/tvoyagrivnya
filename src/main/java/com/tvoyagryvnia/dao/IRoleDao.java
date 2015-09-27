package com.tvoyagryvnia.dao;


import com.tvoyagryvnia.model.RoleEntity;

import java.util.List;
import java.util.Set;

public interface IRoleDao {
    public RoleEntity getRoleById(int id);

    public RoleEntity getRoleByName(RoleEntity.Name name);

    List<RoleEntity> getRolesByIds(Set<Integer> roleIds);

    public int saveRole(RoleEntity roleEntity);

    public List<RoleEntity> getAllRoles();

    public void updateRole(RoleEntity roleEntity);

    public void deleteRole(RoleEntity roleEntity);
}

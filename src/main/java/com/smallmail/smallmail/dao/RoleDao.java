package com.smallmail.smallmail.dao;

import java.util.List;
import java.util.Set;
import com.smallmail.smallmail.model.entity.Role;

public interface RoleDao {

    void create(Role role);

    Role getByRoleName(Role.RoleName roleName);

    Role getById(Long id);

    List<Role> getAll();

    Set<Role> getAllByUserId(Long id);

    public void deleteRoleByUserId(Long userId);
}

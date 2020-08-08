package com.smallmail.smallmail.dao;

import java.util.List;
import com.smallmail.smallmail.model.entity.Role;

public interface RoleDao {

    Role create(Role role);

    Role getByRoleName(String roleName);

    Role getById(Long id);

    List<Role> getAll();

    List<Role> getAllByUserId(Long id);

    public void deleteRoleByUserId(Long userId);
}

package com.smallmail.smallmail.Service;

import java.util.List;
import com.smallmail.smallmail.model.entity.Role;

public interface RoleService {
    Role create(Role role);

    Role getByRoleName(String role);

    Role getById(Long id);

    List<Role> getAll();
}

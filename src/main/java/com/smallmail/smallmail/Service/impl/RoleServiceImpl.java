package com.smallmail.smallmail.Service.impl;

import java.util.List;
import com.smallmail.smallmail.Service.RoleService;
import com.smallmail.smallmail.dao.RoleDao;
import com.smallmail.smallmail.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }

    @Override
    public Role getByRoleName(String role) {
        return roleDao.getByRoleName(role);
    }

    @Override
    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }
}

package com.smallmail.smallmail.Service.impl;

import java.util.List;
import com.smallmail.smallmail.Service.RoleService;
import com.smallmail.smallmail.model.entity.Role;
import com.smallmail.smallmail.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByRoleName(String role) {
        return roleRepository.findByRoleName(Role.RoleName.valueOf(role));
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}

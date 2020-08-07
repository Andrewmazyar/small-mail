package com.smallmail.smallmail.dao.impl;

import java.util.List;
import com.smallmail.smallmail.dao.RoleDao;
import com.smallmail.smallmail.dao.daoMapper.MapperRole;
import com.smallmail.smallmail.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Role role) {
        String sql = "INSERT INTO roles (name) VALUES(?)";
        jdbcTemplate.update(sql, role.getRoleName());
        LOGGER.info("role was created" + role.getRoleName().toString() + " successfully");
    }

    @Override
    public Role getByRoleName(Role.RoleName roleName) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        Role role = jdbcTemplate.queryForObject(sql, new Object[]{roleName}, new MapperRole());
        return role;
    }

    @Override
    public Role getById(Long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        Role role = jdbcTemplate.queryForObject(sql, new Object[]{id}, new MapperRole());
        return role;
    }

    @Override
    public List<Role> getAll() {
        String sql = "SELECT * FROM roles";
        List roles = jdbcTemplate.query(sql, new MapperRole());
        return roles;
    }

    @Override
    public List<Role> getAllByUserId(Long id) {
        String sql = "SELECT * FROM roles"
                + " JOIN user_roles ON roles.id = user_roles.role_id"
                + " WHERE user_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{id},
                new BeanPropertyRowMapper(Role.class));
    }

    @Override
    public void deleteRoleByUserId(Long userId) {
        String sql = "DELETE user_roles WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}

package com.smallmail.smallmail.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.smallmail.smallmail.dao.RoleDao;
import com.smallmail.smallmail.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role create(Role role) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                String sql = "INSERT INTO roles(role_name) VALUES(?)";
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, role.getRoleName().toString());
                return statement;
            }
        }, holder);
        role.setId(holder.getKey().longValue());
        LOGGER.info("role was created" + role.getRoleName() + " successfully");
        return role;
    }

    @Override
    public Role getByRoleName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        Role role = jdbcTemplate.queryForObject(sql, new Object[]{roleName},
                (rs, rowNum) ->
                new Role(
                        rs.getLong("id"),
                        Role.RoleName.valueOf(rs.getString("role_name"))
                ));
        return role;
    }

    @Override
    public Role getById(Long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        Role role = jdbcTemplate.queryForObject(sql, new Object[]{id},
                (rs, rowNum) ->
                        new Role(
                                rs.getLong("id"),
                                Role.RoleName.valueOf(rs.getString("role_name"))
                        ));
        return role;
    }

    @Override
    public List<Role> getAll() {
        String sql = "SELECT * FROM roles";
        List<Role> roles = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Role(
                        rs.getLong("id"),
                        Role.RoleName.valueOf(rs.getString("role_name"))
                ));
        return roles;
    }

    @Override
    public List<Role> getAllByUserId(Long id) {
        String sql = "SELECT * FROM roles"
                + " JOIN user_roles ON roles.id = user_roles.role_id"
                + " WHERE user_id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{id},
                (rs, rowNum) ->
                        new Role(
                                rs.getLong("id"),
                                Role.RoleName.valueOf(rs.getString("role_name"))
                        ));
    }

    @Override
    public void deleteRoleByUserId(Long userId) {
        String sql = "DELETE user_roles WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}

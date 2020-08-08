package com.smallmail.smallmail.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.smallmail.smallmail.dao.RoleDao;
import com.smallmail.smallmail.dao.daoMapper.MapperUser;
import com.smallmail.smallmail.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.smallmail.smallmail.dao.UserDao;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final RoleDao roleDao;

    public UserDaoImpl(JdbcTemplate jdbcTemplate,
                       RoleDao roleDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleDao = roleDao;
    }

    @Override
    public User create(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                String sql = "INSERT INTO users (email, password) VALUES(?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                LOGGER.info("user was created " + user.getEmail() + " successfully");
                return statement;
            }
        }, holder);
        Long primaryKey = holder.getKey().longValue();
        user.setId(primaryKey);
        addRoleForUser(user.getRoles(), user.getId());
        return user;
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = (User) jdbcTemplate.queryForObject(sql, new Object[]{email},
                new BeanPropertyRowMapper(User.class));
        user.setRoles(completeRole(roleDao.getAllByUserId(user.getId())));
        return user;
    }

    @Override
    public User getById(Long id) {
        if (!(id == 0)) {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = (User) jdbcTemplate.queryForObject(sql, new Object[]{id},
                    new BeanPropertyRowMapper(User.class));
            user.setRoles(completeRole(roleDao.getAllByUserId(user.getId())));
            return user;
        }
        return new User();
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users set email = ? password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getId());
        roleDao.deleteRoleByUserId(user.getId());
        addRoleForUser(user.getRoles(), user.getId());
        return user;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        for (User user : users) {
            user.setRoles(completeRole(roleDao.getAllByUserId(user.getId())));
        }
        return users;
    }

    private Set<Role> completeRole(List<Role> roles) {
        Set<Role> userRoles = new HashSet<>();
        for (Role role : roles) {
            Role some = roleDao.getById(role.getId());
            userRoles.add(some);
        }
        return userRoles;
    }

    private void addRoleForUser(Set<Role> roles, Long userId) {
        for (Role role: roles) {
            String sqlRole = "INSERT INTO user_roles (user_id, role_id) VALUES(?, ?)";
            jdbcTemplate.update(sqlRole, userId, role.getId());
            LOGGER.info("user " + userId
                    + " was added role  "
                    + role.getRoleName()
                    + " successfully");
        }
    }
}

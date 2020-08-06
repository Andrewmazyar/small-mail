package com.smallmail.smallmail.dao.impl;

import java.util.List;
import java.util.Set;
import com.smallmail.smallmail.dao.RoleDao;
import com.smallmail.smallmail.dao.daoMapper.MapperUser;
import com.smallmail.smallmail.model.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.smallmail.smallmail.dao.UserDao;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = "INSERT INTO users (email, password) VALUES(?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());
        LOGGER.info("user was created " + user.getEmail() + " successfully");
        User current = getByEmail(user.getEmail());
        addRoleForUser(user.getRoles(), current.getId());
        return getByEmail(user.getEmail());
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new MapperUser());
        user.setRoles(roleDao.getAllByUserId(user.getId()));
        return user;
    }

    @Override
    public User getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new MapperUser());
        user.setRoles(roleDao.getAllByUserId(user.getId()));
        return user;
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
        List<User> users = jdbcTemplate.query(sql, new MapperUser());
        for (User user : users) {
            user.setRoles(roleDao.getAllByUserId(user.getId()));
        }
        return users;
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

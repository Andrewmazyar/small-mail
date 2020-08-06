package com.smallmail.smallmail.Service.impl;

import java.util.List;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.dao.UserDao;
import com.smallmail.smallmail.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) {
        LOGGER.info("user was added successfully");
        return userDao.create(user);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }
}

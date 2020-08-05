package com.smallmail.smallmail.Service.impl;

import java.util.List;
import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.entity.User;
import com.smallmail.smallmail.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        LOGGER.info("user was added successfully");
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteByUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("user was deleted successfully");
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}

package com.smallmail.smallmail.dao;

import java.util.List;
import com.smallmail.smallmail.model.entity.User;

public interface UserDao {
    User create(User user);

    User getByEmail(String email);

    User getById(Long id);

    User update(User user);

    List<User> getAll();
}

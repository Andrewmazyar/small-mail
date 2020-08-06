package com.smallmail.smallmail.Service;

import java.util.List;
import com.smallmail.smallmail.model.entity.User;

public interface UserService {
    User create(User user);

    User getByEmail(String email);

    List<User> getAll();

    User getById(Long id);

    User update(User user);

}

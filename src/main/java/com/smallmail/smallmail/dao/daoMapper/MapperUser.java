package com.smallmail.smallmail.dao.daoMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

public class MapperUser implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return null;
    }
}

package com.smallmail.smallmail.dao.daoMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.smallmail.smallmail.model.entity.Role;
import org.springframework.jdbc.core.RowMapper;

public class MapperRole implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet set, int i) throws SQLException {
        Role role = new Role();
        role.setRoleName(("APICALL".equals(set.getString("name")) ? Role.RoleName.APICALL : Role.RoleName.USER));
        role.setId(set.getLong("id"));
        return role;
    }
}

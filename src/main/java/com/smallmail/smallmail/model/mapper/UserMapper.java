package com.smallmail.smallmail.model.mapper;

import com.smallmail.smallmail.model.dto.UserDetailDto;
import com.smallmail.smallmail.model.dto.UserResponseDto;
import com.smallmail.smallmail.model.entity.Role;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UserDetailDto convertToUserDetailDto(User user) {
        UserDetailDto dto = new UserDetailDto();
        dto.setEmail(user.getEmail());
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equals(Role.RoleName.APICALL)) {
                dto.setDescription("switch off api");
                return dto;
            }
        }
        dto.setDescription("switch on api");
        return dto;
    }
}

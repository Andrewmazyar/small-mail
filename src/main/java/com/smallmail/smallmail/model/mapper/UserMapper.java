package com.smallmail.smallmail.model.mapper;

import com.smallmail.smallmail.model.dto.UserResponseDto;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(user.getEmail());
        return dto;
    }
}

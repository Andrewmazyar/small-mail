package com.smallmail.smallmail.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.smallmail.smallmail.service.UserService;
import com.smallmail.smallmail.model.dto.UserResponseDto;
import com.smallmail.smallmail.model.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
